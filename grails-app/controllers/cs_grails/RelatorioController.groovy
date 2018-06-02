package cs_grails

class RelatorioController {

    static defaultAction = "principal"

    def excluir() {
//        Vitima vitima = new Vitima()
        def jogadoresList = Jogador.findAll()
        println "Antes de deletar: " + jogadoresList.size()
        Jogador.executeUpdate("delete from Jogador")
        jogadoresList = Jogador.findAll()
        println "Depois de deletar: " + jogadoresList.size()

//        jogadoresList.each {
//            vitima.validate()
//            if(!vitima.hasErrors()) {
//                vitima.matador.delete(flush:true)
//                println "Apagou com sucesso"
//            }
//            else {
//                println vitima.errors
//                println "Não apagou"
//            }


        redirect(controller: "relatorio", action: "principal")

    }

    def principal() {
//        def datas = Facadas.where {}.projections { distinct 'dataFacada' }
        def datas = Facadas.executeQuery("select distinct dataFacada from Facadas order by dataFacada desc")
//        Date datasF = datas.format('dd-')
//        println "---------------------------- " + datas.list()
//        render(view: "principal")
        render(view: "principal", model:[datasList:datas])
    }

    def index() {
        println "params.id: " + params.id
        println "params.tipo: " + params.tipo
        String data = params.id
        Date dataRelatorio = Date.parse('yyyy-MM-dd HH:mm:ss',data)

        def lista = Facadas.withCriteria {
            vitima {
                matador{
                    order('nome','asc')
                }
            }
            eq 'dataFacada', dataRelatorio.clearTime()

        }

        def listaAlgozes = Facadas.withCriteria {
            vitima {
                vitima{
                    order('nome','asc')
                }
            }
            eq 'dataFacada', dataRelatorio.clearTime()

        }

        def players = []

        for(facada in lista){
            players.push(facada.vitima.matador.nome)
            players.push(facada.vitima.vitima.nome)
            println "facada: " + facada.vitima.matador.nome + " - " + facada.vitima.vitima.nome
        }

        List<Relatorio> relatorioList = new ArrayList<Relatorio>()
        def nFacas
        def nFacadasDaVitima
        def nFacadasDoMatador
        def nTiros
        def nTirosDaVitima
        def nTirosDoMatador
        def jogadores = []
        for(jogador in players.unique()){
            Jogador jog = new Jogador()
            jog.nome = jogador
            jogadores.push(jog)
            nFacas = new Relatorio()

            ////////////// Facas
            nFacadasDaVitima = Facadas.executeQuery("select sum(qtdeFacadas)from Facadas where vitima.vitima.nome=$jogador and dataFacada=$dataRelatorio").get(0)
            println "nFacadasDaVitima: " + nFacadasDaVitima
//            nFacadasDaVitima = Vitima.executeQuery("select count(qtdeFacadas(select vitima.nome from Vitima where matador.nome=$jogador and dataFacada=$dataRelatorio)")
            nFacas.vitima = jogador
            if(nFacadasDaVitima==null) nFacas.nFacadasVitima = 0
            else nFacas.nFacadasVitima = nFacadasDaVitima
//            nFacadasDoMatador = Vitima.executeQuery("select COUNT(vitima_id) from Vitima where matador.nome=$jogador and dataFacada=$dataRelatorio")
            nFacadasDoMatador = Facadas.executeQuery("select sum(qtdeFacadas)from Facadas where vitima.matador.nome=$jogador and dataFacada=$dataRelatorio").get(0)
            nFacas.matador = jogador
            if(nFacadasDoMatador==null) nFacas.nFacadasMatador = 0
            else nFacas.nFacadasMatador = nFacadasDoMatador

            ///////////// Tiros
            nTiros = new Relatorio()
            nTirosDaVitima = Tiros.executeQuery("select sum(qtdeTiros)from Tiros where vitima.vitima.nome=$jogador and dataTiro=$dataRelatorio").get(0)
            println "nTirosDaVitima: " + nTirosDaVitima
//            nFacadasDaVitima = Vitima.executeQuery("select count(qtdeFacadas(select vitima.nome from Vitima where matador.nome=$jogador and dataFacada=$dataRelatorio)")
            nTiros.vitima = jogador
            if(nTirosDaVitima==null) nFacas.nFacadasVitima = 0
            else nTiros.nTirosVitima = nTirosDaVitima
//            nFacadasDoMatador = Vitima.executeQuery("select COUNT(vitima_id) from Vitima where matador.nome=$jogador and dataFacada=$dataRelatorio")
            nTirosDoMatador = Tiros.executeQuery("select sum(qtdeTiros)from Tiros where vitima.matador.nome=$jogador and dataTiro=$dataRelatorio").get(0)
            nTiros.matador = jogador
            if(nTirosDoMatador==null) nTiros.nTirosMatador = 0
            else nTiros.nTirosMatador = nTirosDoMatador

            relatorioList.add(nFacas)
            println "Facadas: " + jogador + " - " + nFacadasDoMatador + " - " + nFacadasDaVitima
            println "Tiros: " + jogador + " - " + nTirosDoMatador + " - " + nTirosDaVitima

        }

//        println "nFacadas: " + nFacadas
//        println "nFacadas: " + nFacasList.nFacadasMatador
//        println "nFacadas: " + nFacadasDaVitima

//		println jogadores
        if(params.tipo=='facadas') {
            render(view:'facadas',model:[facadasList:lista,listaAlgozes:listaAlgozes,jogadoresList:jogadores,nFacasList:relatorioList,data:dataRelatorio])
            println "view: facadas"
        }
        else render(view:'index',model:[facadasList:lista,listaAlgozes:listaAlgozes,jogadoresList:jogadores,nFacasList:relatorioList,data:dataRelatorio])
    }
}
