package cs_grails

class RelatorioController {

    static defaultAction = "principal"

    def tabela() {
        render(view: "tabela")
    }

    def periodo() {
        String dataInicio = params.dataI
        String dataFim = params.dataF
        println dataInicio
        println dataFim

        Date dataI = Date.parse('dd/MM/yyyy', dataInicio)
        Date dataF = Date.parse('dd/MM/yyyy', dataFim)


        if (dataI==dataF) {
            dataInicio = dataI.format("yyyy-MM-dd").toString()
            println "dataInicio: " + dataInicio
            redirect(controller: "relatorio", action: "index", params: [id: dataInicio])
        }

        def dataRelatorio = dataI


        def lista = Facadas.withCriteria {
            vitima {
                matador{
                    order('nome','asc')
                }
            }
            between('dataFacada', dataI, dataF)
        }
        println "lista: " + lista

        def listaAlgozes = Facadas.withCriteria {
            vitima {
                vitima{
                    order('nome','asc')
                }
            }
            between('dataFacada', dataI, dataF)

        }

        def players = []

        for(facada in lista){
//            println "facada: " + facada
            players.push(facada.vitima.matador.nome)
            players.push(facada.vitima.vitima.nome)
//            println "facada: " + facada.vitima.matador.nome + " - " + facada.vitima.vitima.nome
        }


            def boaTarde = Vitima.executeQuery("from Vitima where date(dataFacada) between $dataI and $dataF and ehFaca=1 and ehBot=0 order by dataFacada asc")
            if (boaTarde != "") boaTarde = boaTarde.get(0)
            println "boaTarde: " + boaTarde


            List facaAmiga = Vitima.executeQuery("from Vitima where date(dataFacada) between $dataI and $dataF and facaAmiga=1 order by dataFacada asc")
            List<RelFacaAmiga> relAmiga = new ArrayList<RelFacaAmiga>()
            facaAmiga.each { nome ->
                println "facaAmiga: " + nome
                RelFacaAmiga facaAm = new RelFacaAmiga()
                facaAm.facaAmiga = nome
                relAmiga.add(facaAm)
            }
//            println relAmiga.facaAmiga
            println "###############################"


        List<Relatorio> relatorioList = new ArrayList<Relatorio>()
        List<RelFacas> relFacas = new ArrayList<RelFacas>()
        def listaDetalhada
        def nRel
        def relFac
        def nFacadasDaVitima
        def nFacadasDoMatador
        def nTirosDaVitima
        def nTirosDoMatador
//        def facaAmiga
        def jogadores = []
        float kd
        for(jogador in players.unique()){
            Jogador jog = new Jogador()
            jog.nome = jogador
            println "Jogador: " + jog.nome
            jogadores.push(jog)

            ////////////// Facas
            nRel = new Relatorio()
            nFacadasDaVitima = Facadas.executeQuery("select sum(qtdeFacadas)from Facadas where vitima.vitima.nome=$jogador and date(dataFacada) between $dataI and $dataF").get(0)
            nFacadasDaVitima as Integer
            println "nFacadasDaVitima: " + nFacadasDaVitima
            nRel.vitima = jogador
            if(nFacadasDaVitima==null) {
                nFacadasDaVitima = 0
                nRel.nFacadasVitima = 0
            }
            else nRel.nFacadasVitima = nFacadasDaVitima
            nFacadasDoMatador = Facadas.executeQuery("select sum(qtdeFacadas)from Facadas where vitima.matador.nome=$jogador and date(dataFacada) between $dataI and $dataF").get(0)
            nFacadasDoMatador as Integer
            println "nFacadasDoMatador: " + nFacadasDoMatador
            nRel.matador = jogador
            if(nFacadasDoMatador==null) {
                nFacadasDoMatador = 0
                nRel.nFacadasMatador = 0
            }
            else nRel.nFacadasMatador = nFacadasDoMatador

            def listVitimas = Facadas.executeQuery("select distinct vitima.vitima.nome from Facadas where vitima.matador.nome=$jogador and date(dataFacada) between $dataI and $dataF")
//            println listVitimas
            listVitimas.each { vit ->
//                println vit
                relFac = new RelFacas()
                relFac.somaFacas = Facadas.executeQuery("select sum(qtdeFacadas)from Facadas where vitima.vitima.nome=$vit and vitima.matador.nome=$jogador and date(dataFacada) between $dataI and $dataF").get(0)
                relFac.matador = jogador
                relFac.vitima = vit
                relFacas.add(relFac)
            }



            println "relFacas: " + relFacas.matador
            /////////// Tiros

            nTirosDoMatador = Tiros.executeQuery("select sum(qtdeTiros)from Tiros where vitima.matador.nome=$jogador and date(dataTiro) between $dataI and $dataF").get(0)
            nTirosDoMatador as Integer
            nRel.matador = jogador
            println "nTirosDoMatador: " + nTirosDoMatador
            if(nTirosDoMatador==null) {
                nRel.nTirosMatador = 0
                nTirosDoMatador = 0  // kill/death
                println "Entrou no nTirosDoMatador: " + kd
            }
            else nRel.nTirosMatador = nTirosDoMatador + nFacadasDoMatador
            nTirosDaVitima = Tiros.executeQuery("select sum(qtdeTiros)from Tiros where vitima.vitima.nome=$jogador and date(dataTiro) between $dataI and $dataF").get(0)
            nTirosDaVitima as Integer
            println "nTirosDaVitima: " + nTirosDaVitima
            if(nTirosDaVitima==null) {
                nRel.nTirosVitima = 0
                nTirosDaVitima = 0  // kill/death
            }
            else {
                nRel.nTirosVitima = nTirosDaVitima + nFacadasDaVitima
                kd = nRel.nTirosMatador/nRel.nTirosVitima // kill/death
            }
            nRel.kd = kd

//            if (!nRel.hasErrors()) {
//                nRel.save(flush: true)
//                println "-------@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@"
//            }
//            else println "---kkkkkkkkkkkkkkkkkkkkkkkkkkkkk"

            relatorioList.add(nRel)

            println "Facadas: " + jogador + " - " + nFacadasDoMatador + " - " + nFacadasDaVitima
            println "Tiros: " + jogador + " - " + nTirosDoMatador + " - " + nTirosDaVitima
            println "KD: " + jogador + " - " + kd
            println "----------------------------"
            println "KD: " + jogador + " - " + kd
            println "----------------------------"

        }


//        println relatorioList.nFacadasMatador
//
//        println "nFacadas: " + nFacadas
//        println "nFacadas: " + nRelList.nFacadasMatador
//        println "nFacadas: " + nFacadasDaVitima

		println jogadores
        if(params.tipo=='facadas') {
            render(view:'facadas',model:[facadasList:lista,listaAlgozes:listaAlgozes,jogadoresList:jogadores,nRelList:relatorioList,data:dataRelatorio, relFacas:relFacas, dataI: dataI, dataF: dataF])
            println "view: facadas"
        }
        else {
            render(view:'indexPeriodo', model:[facadasList:lista,listaAlgozes:listaAlgozes,jogadoresList:jogadores,nRelList:relatorioList,dataI:dataI, dataF:dataF, relFacas:relFacas,
                                              boaTarde:boaTarde, relAmiga: relAmiga])
            println "view: indexPeriodo"
        }

    }

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
        Partidas.executeUpdate("delete from Partidas where date(dataGame)=$dataDelete")
        TempoJogos.executeUpdate("delete from TempoJogos where date(dataGameStart)=$dataDelete")
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
        Partidas.executeUpdate("delete from Partidas")
        TempoJogos.executeUpdate("delete from TempoJogos")
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
            eq 'dataFacada', dataRelatorio.clearTime()  //cleartime não considera a hora
        }


//        def listaTiros = Tiros.executeQuery("from Tiros where date(dataTiro)=2019-10-06")
        def listaTiros = Tiros.findAllByDataTiro(dataRelatorio)
        println "listaTiros: " + listaTiros

        lista = lista + listaTiros
//
//        lista += Vitima.findAllByDataFacada(dataRelatorio)

        println "lista: " + lista

        def listaAlgozes = Facadas.withCriteria {
            vitima {
                vitima{
                    order('nome','asc')
                }
            }
            eq 'dataFacada', dataRelatorio.clearTime()   //cleartime não considera a hora

        }

        def players = []

        for(facada in lista){
//            println "facada: " + facada
            players.push(facada.vitima.matador.nome)
            players.push(facada.vitima.vitima.nome)
//            println "facada: " + facada.vitima.matador.nome + " - " + facada.vitima.vitima.nome
        }

        println "players: " + players


        def boaTarde = Vitima.executeQuery("from Vitima where date(dataFacada)=$dataRelatorio and ehFaca=1 and ehBot=0 order by dataFacada asc")
        if(!boaTarde.isEmpty()) boaTarde = boaTarde.get(0)
        println "boaTarde: " + boaTarde

        List facaAmiga = Vitima.executeQuery("from Vitima where date(dataFacada)=$dataRelatorio and facaAmiga=1 order by dataFacada asc")
        List<RelFacaAmiga> relAmiga = new ArrayList<RelFacaAmiga>()
        facaAmiga.each{ nome->
            println "facaAmiga: " + nome
            RelFacaAmiga facaAm = new RelFacaAmiga()
            facaAm.facaAmiga = nome
            relAmiga.add(facaAm)
        }
//        println relAmiga.facaAmiga
        println "###############################"

        List<Relatorio> relatorioList = new ArrayList<Relatorio>()
        List<RelFacas> relFacas = new ArrayList<RelFacas>()
        def listaDetalhada
        def nRel
        def relFac
        def ordemFacada
        def ordemFacadaVitima
        def nFacadasDaVitima, nFacadasDaVitimaAnt
        def nFacadasDoMatador, nFacadasDoMatadorAnt
        def nFacadasAmiga
        def nTirosDaVitima, nTirosDaVitimaAnt
        def nTirosDoMatador, nTirosDoMatadorAnt
        def mapa
        def jogadores = []
        float kd
        for(jogador in players.unique()){

//            if (jogador.indexOf("XXBOT")!=0) {
                Jogador jog = new Jogador()
                jog.nome = jogador
                println "Jogador: " + jog.nome
                jogadores.push(jog)

                ////////////// Facas
                nRel = new Relatorio()
                nRel.jogador = jogador
                println "nRel.jogador: " + nRel.jogador

//                def vitima = Vitima.executeQuery("select vitima.nome from Vitima where matador.nome=$jogador and date(dataFacada)=$dataRelatorio")
//                println "vitima: " + vitima
//                nRel.vitima = vitima

                nFacadasAmiga = Vitima.executeQuery("select sum(facaAmiga)from Vitima where matador.nome=$jogador and date(dataFacada)=$dataRelatorio and facaAmiga=1").get(0)
                nFacadasAmiga as Integer
                println "nFacadasAmiga: " + nFacadasAmiga
    //                nRel.nFacadasAmiga = nFacadasAmiga
                if (nFacadasAmiga == null) {
                    nFacadasAmiga = 0
                    nRel.nFacadasAmiga = 0
                }
                else nRel.nFacadasAmiga = nFacadasAmiga

                nFacadasDaVitima = Facadas.executeQuery("select sum(qtdeFacadas)from Facadas where vitima.vitima.nome=$jogador and date(dataFacada)=$dataRelatorio").get(0)
                nFacadasDaVitima as Integer
                println "nFacadasDaVitima: " + nFacadasDaVitima
                if (nFacadasDaVitima == null) {
                    nFacadasDaVitima = 0
                    nRel.nFacadasVitima = 0
                } else nRel.nFacadasVitima = nFacadasDaVitima + nFacadasAmiga

                ////////////////////// ORDEM QUE O MATADOR DEU A FACA
                ordemFacada = Vitima.executeQuery("select ordemFacada from Vitima where matador.nome=$jogador and ehFaca=1 and date(dataFacada)=$dataRelatorio")
                println "ordemFacada: " + ordemFacada
                if (ordemFacada == []) {
                    ordemFacada = 0
                    nRel.ordemFacada = 0
                } else {
                    println "Ordem facada matador: " + ordemFacada
                    ordemFacada = ordemFacada.get(0)
                    ordemFacada as Integer
                    nRel.ordemFacada = ordemFacada
                }

                ////////////////////// ORDEM QUE A VÍTIMA LEVOU A FACA
                ordemFacadaVitima = Vitima.executeQuery("select ordemFacada from Vitima where vitima.nome=$jogador and ehFaca=1 and date(dataFacada)=$dataRelatorio")
                println "ordemFacadaVitima: " + ordemFacadaVitima
                if (ordemFacadaVitima == []) {
                    ordemFacadaVitima = 0
                    nRel.ordemFacadaVitima = 0
                } else {
                    ordemFacadaVitima = ordemFacadaVitima.get(0)
                    ordemFacadaVitima as Integer
                    nRel.ordemFacadaVitima = ordemFacadaVitima
                }

                nFacadasDoMatador = Facadas.executeQuery("select sum(qtdeFacadas) from Facadas where vitima.matador.nome=$jogador and date(dataFacada)=$dataRelatorio").get(0)
                nFacadasDoMatador as Integer
                println "nFacadasDoMatador: " + nFacadasDoMatador
//                nRel.matador = jogador
                if (nFacadasDoMatador == null) {
                    nFacadasDoMatador = 0 - nFacadasAmiga
                    nRel.nFacadasMatador = 0 - nFacadasAmiga
                } else {
                    nFacadasDoMatador = nFacadasDoMatador - nFacadasAmiga
                    nRel.nFacadasMatador = nFacadasDoMatador - nFacadasAmiga
                }

                def listVitimas = Facadas.executeQuery("select distinct vitima.vitima.nome from Facadas where vitima.matador.nome=$jogador and date(dataFacada)=$dataRelatorio")
                //            println listVitimas
                listVitimas.each { vit ->
                    //                println vit
                    relFac = new RelFacas()
                    relFac.somaFacas = Facadas.executeQuery("select sum(qtdeFacadas)from Facadas where vitima.vitima.nome=$vit and vitima.matador.nome=$jogador and date(dataFacada)=$dataRelatorio").get(0)
                    relFac.matador = jogador
                    relFac.vitima = vit
                    relFacas.add(relFac)
                }

                //            println "relFacas: " + relFacas.matador
                /////////// Tiros

                nTirosDoMatador = Tiros.executeQuery("select sum(qtdeTiros)from Tiros where vitima.matador.nome=$jogador and date(dataTiro)=$dataRelatorio").get(0)
                println "nTiros: " + nTirosDoMatador
                nTirosDoMatador as Integer
                nRel.matador = jogador
                println "nTirosDoMatador: " + nTirosDoMatador
                if (nTirosDoMatador == null) {
                    nRel.nTirosMatador = 0
                    nTirosDoMatador = 0  // kill/death
                    println "Entrou no nTirosDoMatador: " + kd
                } else nRel.nTirosMatador = nTirosDoMatador + nFacadasDoMatador

                nTirosDaVitima = Tiros.executeQuery("select sum(qtdeTiros)from Tiros where vitima.vitima.nome=$jogador and date(dataTiro)=$dataRelatorio").get(0)
                nTirosDaVitima as Integer
                println "nTirosDaVitima: " + nTirosDaVitima
                if (nTirosDaVitima == null) {
                    nRel.nTirosVitima = 0
                    nTirosDaVitima = 0  // kill/death
                    kd=0
                } else {
                    nRel.nTirosVitima = nTirosDaVitima + nFacadasDaVitima
                    if(nTirosDoMatador && nTirosDaVitima==0) kd=0
                    else kd = nTirosDoMatador / nTirosDaVitima // kill/death
                }
                nRel.kd = kd

                relatorioList.add(nRel)
                println "relatorioList.jogador: " + relatorioList.jogador
                println "Facadas: " + jogador + " - " + nFacadasDoMatador + " - " + nFacadasDaVitima
                println "Tiros: " + jogador + " - " + nTirosDoMatador + " - " + nTirosDaVitima
                println "KD: " + jogador + " - " + kd
                println "----------------------------"
//            }
        }

        println "listMatador: " + relatorioList.matador
        println "listFacasDadas: " + relatorioList.nFacadasMatador
        println "ordemFacadas: " + relatorioList.ordemFacada
        println "listFacasRecebidas: " + relatorioList.nFacadasVitima


        List listGame = []
        relatorioList.matador.eachWithIndex {m, index->
            def game=[:]
            game.put('nome', m)
            game.put('tiros', relatorioList.nTirosMatador[index])
            game.put('mortes', relatorioList.nTirosVitima[index])
            game.put('kd', relatorioList.kd[index])
            game.put('facaDada', relatorioList.nFacadasMatador[index])
            game.put('facaRecebida', relatorioList.nFacadasVitima[index])
            game.put('ordemFacaDada', relatorioList.ordemFacada[index])
            game.put('ordemFacaRecebida', relatorioList.ordemFacadaVitima[index])
            listGame << game
        }
        println "listGame: " + listGame

        /////////////////////////////////////////////// DEFINICAO DE CAMPEOES

        //////////// matador
        println "-----------------------matador"
        def maxTiros = listGame.tiros.max()
        println "maxTiros: " + maxTiros
        def maiorAtirador = listGame.findAll {it.tiros==maxTiros}
        println "maiorAtirador: " + maiorAtirador
        def minMortes = maiorAtirador.mortes.min()
        println "minMortes: " + minMortes
        def campeaoTiro = maiorAtirador.findAll {it.mortes==minMortes}.nome.get(0)
        println "campeaoTiro: " + campeaoTiro

        //////////// Peneira
        println "-----------------------peneira"
        def peneiraSemBot = listGame.findAll {it.nome.indexOf("XXBOT")<0}
        println "peneiraSemBot: " + peneiraSemBot
        def minKd = peneiraSemBot.kd.min()
        println "minKd: " + minKd
        def menorKd = peneiraSemBot.findAll {it.kd==minKd }
        println "menorKd: " + menorKd
        def maiorTiro = menorKd.tiros.min()
        println "maiorTiro: " + maiorTiro
        def campeaoPeneira = menorKd.findAll {it.tiros==maiorTiro}.nome.get(0)
        println "campeaoPeneira: " + campeaoPeneira

        //////////// esfaqueador
        println "-----------------------esfaqueador"
        def maxFacaDada = listGame.facaDada.max()
        println "maxFacaDada: " + maxFacaDada
        def maiorEsfaqueadores = listGame.findAll {it.facaDada==maxFacaDada}
        println "maiorEsfaqueadores: " + maiorEsfaqueadores
        def minFacaRecebida = maiorEsfaqueadores.facaRecebida.min()
        println "minFacaRecebida: " + minFacaRecebida
        def preCampeao = maiorEsfaqueadores.findAll {it.facaRecebida==minFacaRecebida}
        println "preCampeao: " + preCampeao
        def facadaPrimeiro = preCampeao.ordemFacaDada.min()
        println "facadaPrimeiro: " + facadaPrimeiro
        def campeaoFaca = preCampeao.findAll {it.ordemFacaDada==facadaPrimeiro}.nome.get(0)
        println "campeaoFaca: " + campeaoFaca
        println "----------------------IMA"

        //////////// ima
        def maxFacaRecebida = listGame.facaRecebida.max()
        println "maxFacaRecebida: " + maxFacaRecebida
        def maiorIma = listGame.findAll {it.facaRecebida==maxFacaRecebida}
        println "maiorIma: " + maiorIma
        def minFacaDada = maiorIma.facaDada.min()
        println "minFacaDada: " + minFacaDada
        def preCampeaoIma = maiorIma.findAll {it.facaDada==minFacaDada}
        println "preCampeaoIma: " + preCampeaoIma
        def levouFacadaPrimeiro = preCampeaoIma.ordemFacaDada.min()
        println "levouFacadaPrimeiro: " + levouFacadaPrimeiro
        def campeaoIma = preCampeaoIma.findAll {it.ordemFacaDada==levouFacadaPrimeiro}.nome.get(0)
        println "campeaoIma: " + campeaoIma
        println "-------------------------"


        ///// HORÁRIO DOS JOGOS /////////////////////////
        def listHoraInicio = TempoJogos.executeQuery("select dataGameStart from TempoJogos where date(dataGameStart)=$dataRelatorio order by dataGameStart asc")
        println "listHoraInicio: " + listHoraInicio

        ///// RESULTADO DOS MAPAS /////////////////////////
        def resultadoMapa = Partidas.executeQuery("select CT, TERRORIST, dataGame from Partidas where date(dataGame)=$dataRelatorio order by dataGame asc")
        println "resultadoMapa: " + resultadoMapa
        def listMapa = []
//        println "Tamanho: " + resultadoMapa.size()
        def listResultMapa = []


        resultadoMapa.eachWithIndex { i, index ->
            def resultMapa=[:]
            resultMapa.put('CT', [i][0][0])
            resultMapa.put('Terror', [i][0][1])
            if(resultMapa.CT>resultMapa.Terror) resultMapa.put('Vencedor', "CT")
            if(resultMapa.CT==resultMapa.Terror) resultMapa.put('Vencedor', "Empate")
            if(resultMapa.CT<resultMapa.Terror) resultMapa.put('Vencedor', "Terror")
            listResultMapa << resultMapa
            mapa = " " + [i][0][0] + " x " + [i][0][1] + " ------------   " + resultMapa.Vencedor //+ listHoraInicio.get(index).format("HH:mm")
            println "mapa: " + index + " - " + mapa
            listMapa.add(mapa)
        }
        def timeCampeao
        println "listResultMapa: " + listResultMapa
        def nMapaCT = listResultMapa.findAll {it.CT>it.Terror}.size()
        println "nMapaCT: " + nMapaCT
        def nMapaTerror = listResultMapa.findAll {it.CT<it.Terror}.size()
        println "nMapaTerror: " + nMapaTerror
        if(nMapaCT>nMapaTerror) timeCampeao = "CT"
        else timeCampeao = "Terroristas"
        if(nMapaCT==nMapaTerror) {
            if(listResultMapa.sum() {it.CT} > listResultMapa.sum() {it.Terror}) timeCampeao = "CT"
            else timeCampeao="Terroristas"
        }
        println "timeCampeao: " + timeCampeao
        def listResultado = [campeaoTiro,campeaoPeneira,campeaoFaca,campeaoIma, timeCampeao]
//
        if(params.tipo=='facadas') {
            render(view:'facadas',model:[facadasList:lista,listaAlgozes:listaAlgozes,jogadoresList:jogadores,nRelList:relatorioList,data:dataRelatorio, relFacas:relFacas,
            dataView: dataView])
            println "view: facadas"
        }
        else {
            render(view:'index',model:[listResultado:listResultado, facadasList:lista, listaAlgozes:listaAlgozes, jogadoresList:jogadores, nRelList:relatorioList, data:dataRelatorio, relFacas:relFacas,
                                       boaTarde   :boaTarde, relAmiga: relAmiga, mapaFinal: listMapa, listHoraInicio: listHoraInicio])
            println "view: index"
        }
    }
}
