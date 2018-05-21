package cs_grails

class RelatorioController {

    static defaultAction = "principal"

    def principal(){
        def datas = Facadas.where {}.projections { distinct 'dataFacada' }
        render(view:'principal',model:[datasList:datas])

    }
    def index() {
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


//		def lista = Facadas.findAll {
//			vitima.matador.nome == 'Kratos'
//		}

        def players = []

        for(facada in lista){
            players.push(facada.vitima.matador.nome)
            players.push(facada.vitima.vitima.nome)

        }

        def jogadores = []

        for(jogador in players.unique()){
            Jogador jog = new Jogador()
            jog.nome = jogador
            jogadores.push(jog)
        }


//		println jogadores
        render(view:'index',model:[facadasList:lista,jogadoresList:jogadores])



    }
}
