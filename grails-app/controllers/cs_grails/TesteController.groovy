package cs_grails

class TesteController {

    def rodar() {

        def linha;
        int n_arquivo=0;
        int atual=0
        int progresso = 0
        int n_linha=0
        def dataLog

        File dir = new File("grails-app/uploadLogs")
//        dir.mkdir()
        println dir.getAbsolutePath()

        if( dir.isDirectory()) {
//            dir.delete()  //apaga os arquivos anteriores, se houverem
            new File("grails-app/uploadLogs").eachFile { file-> n_arquivo++; }

            def boaTarde = 0   //flag pra marcar somente a 1 facada

            new File("grails-app/uploadLogs").eachFile { file->
                //Renderiza o numero do arquivo
                progresso = 100 * atual / n_arquivo
                atual++

                println "Quantidade de arquivos: $n_arquivo"

                file.withReader { reader->
                    while ((linha = reader.readLine())!=null) {
                        n_linha++;
                        if(linha.contains("with \"knife\"")) {
                            //Nome do Matador
                            def nomeMatador = linha.substring(linha.indexOf("\"") + 1, linha.indexOf("<"));
                            def existeMatador = Jogador.findAll { nome == nomeMatador }
                            Jogador matador = new Jogador()
                            if(existeMatador.empty){
                                matador.nome = nomeMatador
                                matador.save(flush:true)
                                println "Salvou Jogador com sucesso!"
                            }else{
                                matador = existeMatador.get(0)
                            }

                            //Somente para achar o nome Killed
                            def subLinha = linha.substring(linha.indexOf("killed \""));

                            //Extrai o nome da Vitima
                            def nomeVitima = subLinha.substring(subLinha.indexOf("\"") + 1, subLinha.indexOf("<"));
                            def existeVitima = Jogador.findAll { nome == nomeVitima }

                            Jogador vitima = new Jogador()

                            if(existeVitima.empty){
                                vitima.nome = nomeVitima
                                vitima.save(flush:true)
                                println "Salvou com sucesso!"
                            }else{
                                vitima = existeVitima.get(0)
                            }

                            Vitima assassinato = new Vitima()

                            assassinato.matador = matador
                            assassinato.vitima = vitima


                            Facadas facada = new Facadas()
                            facada.qtdeFacadas = 1;
                            String dataFacada =  linha.substring(2, 12);
                            println "Data original: " + dataFacada
                            Date dataFacadaFormatada = Date.parse('MM/dd/yyyy',dataFacada)
                            println "Data convertida: " + dataFacadaFormatada.format('yyyy-MM-dd')
                            String dataFormatada = dataFacadaFormatada.format('yyyy-MM-dd')
                            Date dataFinal = Date.parse('yyyy-MM-dd',dataFormatada)
                            dataLog = dataFormatada + " 00:00:00"
                            facada.dataFacada =  dataFinal
                            facada.vitima = assassinato
                            assassinato.dataFacada = dataFinal
                            if(boaTarde==0) assassinato.boaTarde = 1   //define que o jogador levou boa tarde

                            def existeAssassianto = Vitima.findAllByMatadorAndVitimaAndDataFacada(matador,vitima,dataFinal)
                            println "O assassinato encontrato é: " + assassinato

                            if(existeAssassianto.empty){
                                assassinato.save(flush:true)
                                boaTarde = 1 // para de registrar o boa tarde
                                println "Salvou com sucesso o assassinato!"
                            }else{
                                assassinato = existeAssassianto.get(0)
                                println "Acho o assassinato: " + assassinato
                            }

                            def existeFacadas = Facadas.findAllByVitima(assassinato)
                            println "Foram encontradas as seguintes facadas: " + existeFacadas

                            if(existeFacadas.empty){
                                facada.save(flush:true)
                                println "Salvou com sucesso a facada!"
                            }else{
                                facada = existeFacadas.get(0)
                                println "Facada:" + facada + " Quantidade de facadas antes: " + facada.qtdeFacadas
                                //Automatic Dirty Detection - persiste a alteração mesmo sem mandarmos salvar. Caso não se queira
                                //o comportamento, usar read ao invés de get()
                                //fonte: http://stackoverflow.com/questions/32503852/grails-2-4-4-updating-a-user-auto-saves-user-before-hitting-back-end-code
                                facada.qtdeFacadas +=1
                                println "Facada:" + facada + " Quantidade de facadas depois: " + facada.qtdeFacadas
                            }
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


    def upload() {
//        println params

        File dir = new File("grails-app/uploadLogs");
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
