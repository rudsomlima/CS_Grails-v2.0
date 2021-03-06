package cs_grails

import grails.converters.JSON

import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths
import java.time.LocalDateTime
import java.util.stream.Stream

class TesteController {

    def rodarRapido() {
        println "dado: " + params.id
        LocalDateTime t_inicio = LocalDateTime.now()
        int arquivo=0, n_linha=0
        Path caminho
        def mapRetorno = [:]   //retorna um map pq vai para json
        File dir = new File("uploadLogs")
//        dir.mkdir()
        def diretorio = dir.getAbsolutePath()
        mapRetorno << [dado:diretorio]
//        println diretorio
//        println mapRetorno as JSON
//        render mapRetorno as JSON

        render(template:"status", model: [n_linha: 96477123])
//        render(template:"status", model: [n_linha: caminho.getFileName()])

        if (dir.isDirectory()) {
            new File("uploadLogs").listFiles().sort{it.name}.each { file ->
                arquivo++
                caminho = Paths.get(file.absolutePath)
                println caminho.getFileName()
                render(template:"status", model: [n_linha: n_linha])

////                file.each { linha ->
////                        n_linha++
////                        println arquivo + " - " + n_linha + " - " + linha
////                        render(template:"status", model: [n_linha: n_linha])
//
//                }
            }
        }
        dir.deleteDir()  //apaga os arquivos anteriores, se houverem
        println t_inicio
        println LocalDateTime.now()
        render(view: "/teste/upload")
    }


    def rodar() {

        int arquivo=0, n_linha=0
        Path caminho
        int ordemFacada
        int n_arquivo=0
        def dataLog
        String dataFacada
        String dataFacadaAnt
        def timeMatador
        Integer CT, TERRORIST
        def flag_salvarTimeGanhador = 0
        Date dataFinal
        def noBot = 0   //flag pra marcar somente a 1 facada

        File dir = new File("uploadLogs")
//        dir.mkdir()
        println dir.getAbsolutePath()

        if( dir.isDirectory()) {
//
            new File("uploadLogs").listFiles().sort{it.name}.each { file ->
                caminho = Paths.get(file.absolutePath)
                println caminho.getFileName()
            }

            def prin
            new File("uploadLogs").listFiles().sort{it.name}.each { file ->
                arquivo++
                caminho = Paths.get(file.absolutePath)
                println caminho.getFileName()

                file.each { String linha ->
                    n_linha++
                    println arquivo + " - " + n_linha + " - " + linha
//
//                        //////////////////////// FACADAS ////////////////////////////////////
                    if (linha.contains("with \"knife\"") && !linha.contains("suicide")) {

                        //Registra as ordens das facadas
                        dataFacada = linha.substring(2, 12)
                        if(dataFacada!=dataFacadaAnt) {
                            println "@@@@@@@@@@@@@@@@@@@ data diferente"
                            dataFacadaAnt = dataFacada
                            ordemFacada = 0
                        }
                        ordemFacada = ordemFacada + 1
                        println "-------------------- ordemFacada: " + ordemFacada

                        //Nome do Matador
                        def nomeMatador = linha.substring(linha.indexOf("\"") + 1, linha.indexOf("<"))
                        def matadorList = Jogador.findAllByNomeIlike(nomeMatador)
                        Jogador matador = new Jogador()
                        if (matadorList.empty) {
                            matador.nome = nomeMatador
                            matador.save flush:true
                            println "Salvou Jogador com sucesso!"
                        } else {
                            matador = matadorList.get(0)
                        }

                        //Somente para achar o nome Killed
                        def subLinha = linha.substring(linha.indexOf("killed \""))

                        //Extrai o time do matador
                        timeMatador = linha.replace('>',',').replace('<',',')
                        println "Linha tokenizada: " + timeMatador
                        timeMatador = timeMatador.tokenize(',').get(3)
                        println "timeMatador: " + timeMatador

                        //Extrai o time da vitima
                        def timeVitima = linha.replace('>',',').replace('<',',')
                        timeVitima = timeVitima.tokenize(',').get(7)
                        println "timeVitima: " + timeVitima

                        //Extrai o nome da Vitima
                        def nomeVitima = subLinha.substring(subLinha.indexOf("\"") + 1, subLinha.indexOf("<"))
                        def vitimaList = Jogador.findAllByNome(nomeVitima)
                        println "Vítima: " + nomeVitima

                        Jogador vitima = new Jogador()

                        if (vitimaList.empty) {
                            vitima.nome = nomeVitima
                            vitima.save flush:true
                            println "Salvou com sucesso!"
                        } else {
                            vitima = vitimaList.get(0)
                        }

                        Vitima assassinato = new Vitima()

                        assassinato.matador = matador
                        assassinato.vitima = vitima
                        assassinato.ordemFacada = ordemFacada
                        assassinato.timeDoAssassino = timeMatador


                        Facadas facada = new Facadas()
                        facada.qtdeFacadas = 1
//                            String dataFacada = linha.substring(2, 12)
                        String horaFacada = linha.substring(15, 23)
                        dataFacada = dataFacada + " " + horaFacada
                        println "Data original: " + dataFacada
                        Date dataFacadaFormatada = Date.parse('MM/dd/yyyy HH:mm:ss', dataFacada)
//                            println "Data convertida: " + dataFacadaFormatada.format('yyyy-MM-dd')
                        String dataFormatada = dataFacadaFormatada.format('yyyy-MM-dd HH:mm:ss')
                        dataFinal = Date.parse('yyyy-MM-dd HH:mm:ss', dataFormatada)
                        dataLog = dataFormatada + " 00:00:00"
                        facada.dataFacada = dataFinal
                        facada.vitima = assassinato
                        assassinato.dataFacada = dataFinal
                        assassinato.ehFaca = 1
                        if(nomeMatador.indexOf("XXBOT")==0 | nomeVitima.indexOf("XXBOT")==0 ) assassinato.ehBot = 1 //
                        else assassinato.ehBot = 0
                        if(timeMatador==timeVitima) assassinato.facaAmiga = 1 //
                        else assassinato.facaAmiga = 0
//                            println "indice matador: " + nomeMatador.indexOf("XXBOT")
//                            println "indice vitima: " + nomeVitima.indexOf("XXBOT")


                        def existeAssassinato = Vitima.findAllByMatadorAndVitimaAndDataFacada(matador, vitima, dataFinal)
                        println "O assassinato encontrato é: " + assassinato

                        if (existeAssassinato.empty) {
                            assassinato.save flush:true
                            println "Salvou com sucesso o assassinato!"
                        } else {
                            assassinato = existeAssassinato.get(0)
                            println "Achou o assassinato: " + assassinato
                        }


                        def existeFacadas = Facadas.findAllByVitima(assassinato)
                        println "Foram encontradas as seguintes facadas: " + existeFacadas

                        if (existeFacadas.empty) {
                            facada.save flush:true
                            println "Salvou com sucesso a facada!"
                        } else {
                            facada = existeFacadas.get(0)
//                                println "Facada:" + facada + " Quantidade de facadas antes: " + facada.qtdeFacadas
                            //Automatic Dirty Detection - persiste a alteração mesmo sem mandarmos salvar. Caso não se queira
                            //o comportamento, usar read ao invés de get()
                            //fonte: http://stackoverflow.com/questions/32503852/grails-2-4-4-updating-a-user-auto-saves-user-before-hitting-back-end-code
                            facada.qtdeFacadas += 1
//                                println "Facada:" + facada + " Quantidade de facadas depois: " + facada.qtdeFacadas
                        }
                    }

                    //////////////////////////////// HORARIO DOS JOGOS ////////////////////////////////////////////////////////////////
                    if(linha.contains("World triggered \"Game_Commencing\" (CT \"0\") (T \"0\")")) {
                        println n_linha + " - " + linha
//                            L 08/28/2018 - 13:03:16
                        def horaStart = linha.substring(2, 22)
                        Date dataGameStart = Date.parse('MM/dd/yyyy - HH:mm:ss', horaStart)
                        println "dataGameStart: " + dataGameStart
                        TempoJogos tempoJogos = new TempoJogos()
                        tempoJogos.dataGameStart = dataGameStart
                        tempoJogos.save flush:true
                    }

                    //////////////////////////////// TIME GANHADOR ////////////////////////////////////////////////////////////////
                    if(linha.contains("scored")) {

                        println n_linha + " - " + linha
//                            L 08/28/2018 - 13:03:16
                        def data = linha.substring(2, 22)
                        Date dataGame = Date.parse('MM/dd/yyyy - HH:mm:ss', data)
                        println "dataGame: " + dataGame

                        //Extrai o time do matador
                        def resultado = linha.tokenize('\"')
                        println "Linha resultado: " + resultado

                        def res_parcial = resultado.get(3) as Integer
                        println "res_parcial: " + res_parcial
                        if (flag_salvarTimeGanhador == 1 && res_parcial != null) {
                            TERRORIST = res_parcial
                            println "Terror: " + TERRORIST
//                                pulaLinha=1
                            flag_salvarTimeGanhador = 2
                        }
                        if (flag_salvarTimeGanhador == 0 && res_parcial != null) {
                            CT = res_parcial
//                                pulaLinha=0
                            println "CT: " + CT
                            flag_salvarTimeGanhador = 1
                        }

                        if (flag_salvarTimeGanhador==2) {
//                                println "dataGame " + dataGame
                            Partidas partida = new Partidas()
                            partida.dataGame = dataGame
                            partida.CT = CT
                            partida.TERRORIST = TERRORIST
                            partida.save flush:true
                            flag_salvarTimeGanhador = 0
                        }

                    }

                    //////////////////////////////// TIROS ////////////////////////////////////////////////////////////////
                    if(linha.contains(">\" with \"") && !linha.contains("with \"knife\"")) {

                        println n_linha + " - " + linha
                        //Nome do Matador
                        def nomeMatador = linha.substring(linha.indexOf("\"") + 1, linha.indexOf("<"))
                        def matadorList = Jogador.findAllByNomeIlike(nomeMatador)
                        Jogador matador = new Jogador()
                        if (matadorList.empty) {
                            matador.nome = nomeMatador
                            matador.save flush:true
                            println "Salvou Jogador com sucesso!"
                        } else {
                            matador = matadorList.get(0)
                        }

                        //Somente para achar o nome Killed
                        def subLinha = linha.substring(linha.indexOf("killed \""))

                        //Extrai o nome da Vitima
                        def nomeVitima = subLinha.substring(subLinha.indexOf("\"") + 1, subLinha.indexOf("<"))
                        def vitimaList = Jogador.findAllByNome(nomeVitima)

                        //Extrai o time do matador
                        timeMatador = linha.replace('>',',').replace('<',',')
                        println "Linha tokenizada: " + timeMatador
                        timeMatador = timeMatador.tokenize(',').get(3)
                        println "timeMatador: " + timeMatador

                        Jogador vitima = new Jogador()

                        if (vitimaList.empty) {
                            vitima.nome = nomeVitima
                            vitima.save flush:true
                            println "Salvou com sucesso!"
                        } else {
                            vitima = vitimaList.get(0)
                        }

                        Vitima assassinato = new Vitima()

                        assassinato.matador = matador
                        assassinato.vitima = vitima

                        def existeAssassinato = Vitima.findAllByMatadorAndVitimaAndDataFacada(matador, vitima, dataFinal)
                        println "O assassinato encontrato é: " + assassinato

                        if (existeAssassinato.empty) {
                            assassinato.save flush:true
                            println "Salvou com sucesso o assassinato!"
                        } else {
                            assassinato = existeAssassinato.get(0)
//                                assassinato.save flush:true
                            println "Achou o assassinato: " + assassinato
                        }

                        Tiros tiro = new Tiros()
                        tiro.qtdeTiros = 1
                        dataFacada = linha.substring(2, 12)
//                            println "Data original: " + dataFacada
                        Date dataFacadaFormatada = Date.parse('MM/dd/yyyy', dataFacada)
//                            println "Data convertida: " + dataFacadaFormatada.format('yyyy-MM-dd')
                        String dataFormatada = dataFacadaFormatada.format('yyyy-MM-dd')
                        dataFinal = Date.parse('yyyy-MM-dd', dataFormatada)
                        dataLog = dataFormatada + " 00:00:00"
                        tiro.dataTiro = dataFinal
                        tiro.vitima = assassinato
                        assassinato.dataFacada = dataFinal
                        assassinato.ehFaca = 0
                        assassinato.ehBot = 0 //O erro estava relacionado ao fato de vc não ter setado todos os parametros
                        assassinato.facaAmiga = 0
                        assassinato.ordemFacada = 0
                        assassinato.timeDoAssassino = timeMatador
                        assassinato.save flush:true


                        def existeTiros = Tiros.findAllByVitima(assassinato)
                        println "Foram encontradas as seguintes facadas: " + existeTiros

                        if (existeTiros.empty) {
                            tiro.save flush:true
                            println "Salvou com sucesso a facada!"
                        } else {
                            tiro = existeTiros.get(0)
//                                println "Facada:" + facada + " Quantidade de facadas antes: " + facada.qtdeFacadas
                            //Automatic Dirty Detection - persiste a alteração mesmo sem mandarmos salvar. Caso não se queira
                            //o comportamento, usar read ao invés de get()
                            //fonte: http://stackoverflow.com/questions/32503852/grails-2-4-4-updating-a-user-auto-saves-user-before-hitting-back-end-code
                            tiro.qtdeTiros += 1
                            tiro.save flush:true
//                                println "Facada:" + facada + " Quantidade de facadas depois: " + facada.qtdeFacadas
                        }
                    }
                }
//                file.delete() //apaga o arquivo para não processa-lo novamente
            }
        }
        dir.deleteDir()  //Apaga a pasta uploadLogs
        println "Apagou a pasta uploadLogs!"
        redirect(controller: "relatorio", action: "principal")
    }



    def upload() {
//        println params

        File dir = new File("uploadLogs")
        dir.mkdir()

        if (params.myfile) {
            def fis = params.myfile.getInputStream()
//            println "params.myfile: " + params.myfile
            println "Total file size to read (in bytes): " + fis.available()
            int content

            def file = new File(dir.getAbsolutePath(),params.myfile.filename)
            file = file.newOutputStream()

            file.withWriter('UTF-8'){ writer ->
                while ((content = fis.read()) != -1) {
                    writer.write((char) content)
                }
            }
        }

        render(view: "/teste/upload")
    }
}
