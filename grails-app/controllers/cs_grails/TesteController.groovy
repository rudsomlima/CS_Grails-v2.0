package cs_grails

class TesteController {

    def rodar() {

        def linha;
        int n_arquivo=0;
        int atual=0
        int progresso = 0
        int n_linha=0
        def dataLog
        String resultadoFinal = ""
        def pulaLinha = 0

        File dir = new File("uploadLogs")
//        dir.mkdir()
        println dir.getAbsolutePath()

        if( dir.isDirectory()) {
//            dir.delete()  //apaga os arquivos anteriores, se houverem
            new File("uploadLogs").eachFile { file -> n_arquivo++; }

            def noBot = 0   //flag pra marcar somente a 1 facada

//            List<Jogador> matadorList = new ArrayList<Jogador>()
//            List<Jogador> assassinatoList = new ArrayList<Vitima>()

            new File("uploadLogs").eachFile { file ->
                //Renderiza o numero do arquivo
                progresso = 100 * atual / n_arquivo
                atual++

                println "Quantidade de arquivos: $n_arquivo"
                Date dataFinal

                file.withReader { reader ->
                    while ((linha = reader.readLine()) != null) {
                        n_linha++;
//
//                        //////////////////////// FACADAS ////////////////////////////////////
                        if (linha.contains("with \"knife\"") && !linha.contains("suicide")) {

                            println n_linha + " - " + linha
                            //Nome do Matador
                            def nomeMatador = linha.substring(linha.indexOf("\"") + 1, linha.indexOf("<"));
                            def matadorList = Jogador.findAllByNomeIlike(nomeMatador)
                            Jogador matador = new Jogador()
                            if (matadorList.empty) {
                                matador.nome = nomeMatador
                                matador.save flush: true
                                println "Salvou Jogador com sucesso!"
                            } else {
                                matador = matadorList.get(0)
                            }

                            //Somente para achar o nome Killed
                            def subLinha = linha.substring(linha.indexOf("killed \""));

                            //Extrai o time do matador
                            def timeMatador = linha.replace('>',',').replace('<',',')
                            println "Linha tokenizada: " + timeMatador
                            timeMatador = timeMatador.tokenize(',').get(3)
                            println "timeMatador: " + timeMatador

                            //Extrai o time da vitima
                            def timeVitima = linha.replace('>',',').replace('<',',')
                            timeVitima = timeVitima.tokenize(',').get(7)
                            println "timeVitima: " + timeVitima

                            //Extrai o nome da Vitima
                            def nomeVitima = subLinha.substring(subLinha.indexOf("\"") + 1, subLinha.indexOf("<"));
                            def vitimaList = Jogador.findAllByNome(nomeVitima)

                            Jogador vitima = new Jogador()

                            if (vitimaList.empty) {
                                vitima.nome = nomeVitima
                                vitima.save flush: true
                                println "Salvou com sucesso!"
                            } else {
                                vitima = vitimaList.get(0)
                            }

                            Vitima assassinato = new Vitima()

                            assassinato.matador = matador
                            assassinato.vitima = vitima

                            Facadas facada = new Facadas()
                            facada.qtdeFacadas = 1
                            String dataFacada = linha.substring(2, 12)
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
                                assassinato.save flush: true
                                println "Salvou com sucesso o assassinato!"
                            } else {
                                assassinato = existeAssassinato.get(0)
                                println "Achou o assassinato: " + assassinato
                            }


                            def existeFacadas = Facadas.findAllByVitima(assassinato)
                            println "Foram encontradas as seguintes facadas: " + existeFacadas

                            if (existeFacadas.empty) {
                                facada.save flush: true
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

                        //////////////////////////////// TIME GANHADOR ////////////////////////////////////////////////////////////////

                        if(linha.contains("scored")) {
                            println n_linha + " - " + linha

                            //Extrai o time do matador
                            def resultado = linha.tokenize('\"')
//                            println "Linha resultado: " + resultado
                            if (pulaLinha==1) {
                                resultado = resultado.get(3) + " " + resultado.get(1)
                                resultadoFinal = resultadoFinal + resultado + '\n'
                                pulaLinha=0
                            }
                            else {
                                resultado = resultado.get(1) + " " + resultado.get(3) + " x "
                                resultadoFinal += resultado
                                pulaLinha=1
                            }
                            println resultadoFinal

                        }

                        //////////////////////////////// TIROS ////////////////////////////////////////////////////////////////
                        if(linha.contains(">\" with \"") && !linha.contains("with \"knife\"")) {

                            println n_linha + " - " + linha
                            //Nome do Matador
                            def nomeMatador = linha.substring(linha.indexOf("\"") + 1, linha.indexOf("<"));
                            def matadorList = Jogador.findAllByNomeIlike(nomeMatador)
                            Jogador matador = new Jogador()
                            if (matadorList.empty) {
                                matador.nome = nomeMatador
                                matador.save flush: true
                                println "Salvou Jogador com sucesso!"
                            } else {
                                matador = matadorList.get(0)
                            }

                            //Somente para achar o nome Killed
                            def subLinha = linha.substring(linha.indexOf("killed \""));

                            //Extrai o nome da Vitima
                            def nomeVitima = subLinha.substring(subLinha.indexOf("\"") + 1, subLinha.indexOf("<"));
                            def vitimaList = Jogador.findAllByNome(nomeVitima)

                            Jogador vitima = new Jogador()

                            if (vitimaList.empty) {
                                vitima.nome = nomeVitima
                                vitima.save flush: true
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
                                assassinato.save flush: true
                                println "Salvou com sucesso o assassinato!"
                            } else {
                                assassinato = existeAssassinato.get(0)
//                                assassinato.save flush: true
                                println "Achou o assassinato: " + assassinato
                            }

                            Tiros tiro = new Tiros()
                            tiro.qtdeTiros = 1
                            String dataFacada = linha.substring(2, 12)
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

                            assassinato.save flush: true


                            def existeTiros = Tiros.findAllByVitima(assassinato)
                            println "Foram encontradas as seguintes facadas: " + existeTiros

                            if (existeTiros.empty) {
                                tiro.save flush: true
                                println "Salvou com sucesso a facada!"
                            } else {
                                tiro = existeTiros.get(0)
//                                println "Facada:" + facada + " Quantidade de facadas antes: " + facada.qtdeFacadas
                                //Automatic Dirty Detection - persiste a alteração mesmo sem mandarmos salvar. Caso não se queira
                                //o comportamento, usar read ao invés de get()
                                //fonte: http://stackoverflow.com/questions/32503852/grails-2-4-4-updating-a-user-auto-saves-user-before-hitting-back-end-code
                                tiro.qtdeTiros += 1
                                tiro.save flush: true
//                                println "Facada:" + facada + " Quantidade de facadas depois: " + facada.qtdeFacadas
                            }
                        }



                    }
                    file.delete() //apaga o arquivo para não processa-lo novamente
                }
            }
            dir.deleteDir()
            println "Apagou a pasta uploadLogs!"
//        println dataLog
//        redirect(controller: "relatorio", id: dataLog, action: "index")
            redirect(controller: "relatorio", action: "principal")
        }
    }


    def upload() {
//        println params

        File dir = new File("uploadLogs");
        dir.mkdir()

        if (params.myfile) {
            def fis = params.myfile.getInputStream()
//            println "params.myfile: " + params.myfile
            println "Total file size to read (in bytes): " + fis.available()
            int content;

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
