package cs_grails

class RelatorioController {

    static defaultAction = "principal"

    def principal(){
        def datas = Facadas.where {}.projections { distinct 'dataFacada' }
//        Date datasF = datas.format('dd-')
        println datas.list()
//        render(view: "principal")
        render(view: "principal", model:[datasList:datas])
    }

    def index() {
        println params
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
            nFacadasDaVitima = Vitima.executeQuery("select COUNT(vitima_id) from Vitima where vitima.nome=$jogador and dataFacada=$dataRelatorio")
            nFacas.vitima = jogador
            nFacas.nFacadasVitima = nFacadasDaVitima.get(0)
            nFacadasDoMatador = Vitima.executeQuery("select COUNT(vitima_id) from Vitima where matador.nome=$jogador and dataFacada=$dataRelatorio")
            nFacas.matador = jogador
            nFacas.nFacadasMatador = nFacadasDoMatador.get(0)
            nFacasList.add(nFacas)
            println jogador + " - " + nFacadasDoMatador + " - " + nFacadasDaVitima
        }
//        println "nFacadas: " + nFacadas
//        println "nFacadas: " + nFacasList.nFacadasMatador
//        println "nFacadas: " + nFacadasDaVitima

//		println jogadores
        render(view:'index',model:[facadasList:lista,jogadoresList:jogadores,nFacasList:nFacasList])
    }
}
