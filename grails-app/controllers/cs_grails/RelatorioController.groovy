package cs_grails

class RelatorioController {

    static defaultAction = "principal"

    def excluir() {
        def jogadoresList = Jogador.findAll()
        println "Antes de deletar: " + jogadoresList.size()
        Facadas.executeUpdate("delete from Facadas")
        Vitima.executeUpdate("delete from Vitima")
        Jogador.executeUpdate("delete from Jogador")
        jogadoresList = Jogador.findAll()
        println "Depois de deletar: " + jogadoresList.size()
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
        def nRel
        def nFacadasDaVitima
        def nFacadasDoMatador
        def nTirosDaVitima
        def nTirosDoMatador
        def jogadores = []
        for(jogador in players.unique()){
            Jogador jog = new Jogador()
            jog.nome = jogador
            jogadores.push(jog)

            ////////////// Facas
            nRel = new Relatorio()
            nFacadasDaVitima = Facadas.executeQuery("select sum(qtdeFacadas)from Facadas where vitima.vitima.nome=$jogador and dataFacada=$dataRelatorio").get(0)
            println "nFacadasDaVitima: " + nFacadasDaVitima
            nRel.vitima = jogador
            if(nFacadasDaVitima==null) nRel.nFacadasVitima = 0
            else nRel.nFacadasVitima = nFacadasDaVitima
            nFacadasDoMatador = Facadas.executeQuery("select sum(qtdeFacadas)from Facadas where vitima.matador.nome=$jogador and dataFacada=$dataRelatorio").get(0)
            nRel.matador = jogador
            if(nFacadasDoMatador==null) nRel.nFacadasMatador = 0
            else nRel.nFacadasMatador = nFacadasDoMatador

            /////////// Tiros
            nTirosDaVitima = Tiros.executeQuery("select sum(qtdeTiros)from Tiros where vitima.vitima.nome=$jogador and dataTiro=$dataRelatorio").get(0)
            println "nTirosDaVitima: " + nTirosDaVitima
            if(nTirosDaVitima==null) nRel.nTirosVitima = 0
            else nRel.nTirosVitima = nTirosDaVitima
            nTirosDoMatador = Tiros.executeQuery("select sum(qtdeTiros)from Tiros where vitima.matador.nome=$jogador and dataTiro=$dataRelatorio").get(0)
            nRel.matador = jogador
            if(nTirosDoMatador==null) nRel.nTirosMatador = 0
            else nRel.nTirosMatador = nTirosDoMatador

            relatorioList.add(nRel)
            println "Facadas: " + jogador + " - " + nFacadasDoMatador + " - " + nFacadasDaVitima
            println "Tiros: " + jogador + " - " + nTirosDoMatador + " - " + nTirosDaVitima

        }

//        println "nFacadas: " + nFacadas
//        println "nFacadas: " + nRelList.nFacadasMatador
//        println "nFacadas: " + nFacadasDaVitima

//		println jogadores
        if(params.tipo=='facadas') {
            render(view:'facadas',model:[facadasList:lista,listaAlgozes:listaAlgozes,jogadoresList:jogadores,nRelList:relatorioList,data:dataRelatorio])
            println "view: facadas"
        }
        else render(view:'index',model:[facadasList:lista,listaAlgozes:listaAlgozes,jogadoresList:jogadores,nRelList:relatorioList,data:dataRelatorio])
    }
}
