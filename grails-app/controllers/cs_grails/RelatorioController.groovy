package cs_grails

class RelatorioController {

    static defaultAction = "principal"

    def excluirData() {
        def data = params.id
//        println "data: " + data
        Date dataDelete= Date.parse('yyyy-MM-dd',data)
//        println "dataDelete: " + dataDelete
        def jogadoresList = Jogador.findAll()
        println "Antes de deletar: " + jogadoresList.size()
        Facadas.executeUpdate("delete from Facadas where date(dataFacada)=$dataDelete")
        Tiros.executeUpdate("delete from Tiros where date(dataTiro)=$dataDelete")
        Vitima.executeUpdate("delete from Vitima where date(dataFacada)=$dataDelete")
//        Jogador.executeUpdate("delete from Jogador where vitima.dataFacada=$dataDelete")
//        jogadoresList = Jogador.findAll()
//        println "Depois de deletar: " + jogadoresList.size()
        redirect(controller: "relatorio", action: "principal")
    }

    def excluir() {
        def jogadoresList = Jogador.findAll()
        println "Antes de deletar: " + jogadoresList.size()
        Facadas.executeUpdate("delete from Facadas")
        Tiros.executeUpdate("delete from Tiros")
        Vitima.executeUpdate("delete from Vitima")
        Jogador.executeUpdate("delete from Jogador")
        jogadoresList = Jogador.findAll()
        println "Depois de deletar: " + jogadoresList.size()
        redirect(controller: "relatorio", action: "principal")
    }

    def principal() {
//        def datas = Facadas.where {}.projections { distinct 'dataFacada' }
        def datas = Vitima.executeQuery("select distinct date(dataFacada) from Vitima order by dataFacada desc")
//        println "Dataaaaaa: " + datas
//        Date datasF = datas.format('dd-')
//        println "---------------------------- " + datas.list()
//        render(view: "principal")
        render(view: "principal", model:[datasList:datas])
    }

    def index() {
        println "params.id: " + params.id
        println "params.tipo: " + params.tipo
        String data = params.id
        Date dataRelatorio = Date.parse('yyyy-MM-dd',data)
        String dataView = dataRelatorio.format("dd-MM-yyyy")
        println "dataView: " + dataView

        println "dataRelatorio: " + dataRelatorio

        def lista = Facadas.withCriteria {
            vitima {
                matador{
                    order('nome','asc')
                }
            }
            ge 'dataFacada', dataRelatorio
        }
//        lista += Vitima.findAllByDataFacada(dataRelatorio)

        println "lista: " + lista

        def listaAlgozes = Facadas.withCriteria {
            vitima {
                vitima{
                    order('nome','asc')
                }
            }
            ge 'dataFacada', dataRelatorio.clearTime()

        }

        def players = []

        for(facada in lista){
            println "facada: " + facada
            players.push(facada.vitima.matador.nome)
            players.push(facada.vitima.vitima.nome)
//            println "facada: " + facada.vitima.matador.nome + " - " + facada.vitima.vitima.nome
        }


        def boaTarde = Vitima.executeQuery("from Vitima where date(dataFacada)=$dataRelatorio and ehBot=0 order by dataFacada asc").get(0)
        println "boaTarde: " + boaTarde


        List<Relatorio> relatorioList = new ArrayList<Relatorio>()
        List<RelFacas> relFacas = new ArrayList<RelFacas>()
        def listaDetalhada
        def nRel
        def relFac
        def nFacadasDaVitima
        def nFacadasDoMatador
        def nTirosDaVitima
        def nTirosDoMatador
        def jogadores = []
        float kd
        for(jogador in players.unique()){
            Jogador jog = new Jogador()
            jog.nome = jogador
            jogadores.push(jog)

            ////////////// Facas
            nRel = new Relatorio()
            nFacadasDaVitima = Facadas.executeQuery("select sum(qtdeFacadas)from Facadas where vitima.vitima.nome=$jogador and date(dataFacada)=$dataRelatorio").get(0)
            println "nFacadasDaVitima: " + nFacadasDaVitima
            nRel.vitima = jogador
            if(nFacadasDaVitima==null) nRel.nFacadasVitima = 0
            else nRel.nFacadasVitima = nFacadasDaVitima
            nFacadasDoMatador = Facadas.executeQuery("select sum(qtdeFacadas)from Facadas where vitima.matador.nome=$jogador and date(dataFacada)=$dataRelatorio").get(0)
            nRel.matador = jogador
            if(nFacadasDoMatador==null) nRel.nFacadasMatador = 0
            else nRel.nFacadasMatador = nFacadasDoMatador

            def listVitimas = Facadas.executeQuery("select distinct vitima.vitima.nome from Facadas where vitima.matador.nome=$jogador and date(dataFacada)=$dataRelatorio")
            println listVitimas
            listVitimas.each { vit ->
                println vit
                relFac = new RelFacas()
                relFac.somaFacas = Facadas.executeQuery("select sum(qtdeFacadas)from Facadas where vitima.vitima.nome=$vit and vitima.matador.nome=$jogador and date(dataFacada)=$dataRelatorio").get(0)
                relFac.matador = jogador
                relFac.vitima = vit
                relFacas.add(relFac)
            }

            /////////// Tiros

            nTirosDoMatador = Tiros.executeQuery("select sum(qtdeTiros)from Tiros where vitima.matador.nome=$jogador and date(dataTiro)=$dataRelatorio").get(0)
            nRel.matador = jogador
            println "nTirosDoMatador: " + nTirosDoMatador
            if(nTirosDoMatador==null) {
                nRel.nTirosMatador = 0
                nTirosDoMatador = 0  // kill/death
                println "Entrou no nTirosDoMatador: " + kd
            }
            else nRel.nTirosMatador = nTirosDoMatador
            nTirosDaVitima = Tiros.executeQuery("select sum(qtdeTiros)from Tiros where vitima.vitima.nome=$jogador and date(dataTiro)=$dataRelatorio").get(0)
            println "nTirosDaVitima: " + nTirosDaVitima
            if(nTirosDaVitima==null) {
                nRel.nTirosVitima = 0
                nTirosDaVitima = 0  // kill/death
            }
            else {
                kd = nTirosDoMatador/nTirosDaVitima // kill/death
                nRel.nTirosVitima = nTirosDaVitima
            }
            nRel.kd = kd

            def dataString =

            relatorioList.add(nRel)
            println "Facadas: " + jogador + " - " + nFacadasDoMatador + " - " + nFacadasDaVitima
            println "Tiros: " + jogador + " - " + nTirosDoMatador + " - " + nTirosDaVitima
            println "KD: " + jogador + " - " + kd

        }


//        println "nFacadas: " + nFacadas
//        println "nFacadas: " + nRelList.nFacadasMatador
//        println "nFacadas: " + nFacadasDaVitima

//		println jogadores
        if(params.tipo=='facadas') {
            render(view:'facadas',model:[facadasList:lista,listaAlgozes:listaAlgozes,jogadoresList:jogadores,nRelList:relatorioList,data:dataRelatorio, relFacas:relFacas,
            dataView: dataView])
            println "view: facadas"
        }
        else render(view:'index',model:[facadasList:lista,listaAlgozes:listaAlgozes,jogadoresList:jogadores,nRelList:relatorioList,data:dataRelatorio, relFacas:relFacas,
        boaTarde:boaTarde])
    }
}
