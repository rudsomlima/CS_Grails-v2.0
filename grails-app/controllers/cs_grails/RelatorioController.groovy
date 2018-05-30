package cs_grails

class RelatorioController {

    static defaultAction = "principal"

    def principal() {
        def datas = Facadas.where {}.projections { distinct 'dataFacada' }
//        Date datasF = datas.format('dd-')
        println "---------------------------- " + datas.list()
//        render(view: "principal")
        render(view: "principal", model:[datasList:datas])
    }

    def tabela() {
        println "params.id: " + params.id
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

//		def jogadores = Jogador.findAll {
//			order('nome','asc')
//
//		}


		def lista1 = Facadas
//        println "lista: " + lista
//			vitima.matador.nome == 'Kratos'
//		}

        def players = []

        for(facada in lista){
            players.push(facada.vitima.matador.nome)
            players.push(facada.vitima.vitima.nome)
            println "facada: " + facada.vitima.matador.nome + " - " + facada.vitima.vitima.nome
        }

        List<Relatorio> nFacasList = new ArrayList<Relatorio>()
        def nFacas
        def nFacadasDaVitima
        def nFacadasDoMatador
        def jogadores = []
        for(jogador in players.unique()){
            Jogador jog = new Jogador()
            jog.nome = jogador
            jogadores.push(jog)
            nFacas = new Relatorio()
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
            nFacasList.add(nFacas)
            println jogador + " - " + nFacadasDoMatador + " - " + nFacadasDaVitima
        }
//        println "nFacadas: " + nFacadas
//        println "nFacadas: " + nFacasList.nFacadasMatador
//        println "nFacadas: " + nFacadasDaVitima

//		println jogadores
        render(view:'index',model:[facadasList:lista,listaAlgozes:listaAlgozes,jogadoresList:jogadores,nFacasList:nFacasList])
    }
}
