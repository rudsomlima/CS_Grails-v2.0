package cs_grails

class ProcessaArquivoController {

    def index() {
        println "Teste"
    }

    def rodar() {
        def linha;
        def int n=0;
        new File("uploadLogs").eachFile { file->
            file.withReader { reader->
                while ((linha = reader.readLine())!=null) {
                    if(linha.contains("with \"knife\"")) {
                        Jogos jogo = new Jogos()
                        //def teste = SimpleDateFormat(mm/dd/yyyy, "09/28/2016");
                        //String hora = linha.substring(15,23);

                        def data =  linha.substring(2, 12);
                        println data
                        jogo.data = data;
                        //jogos.data = strData;
                        //jogos.hora = linha.substring(15,23);
                        jogo.timeJogador = "CS"
                        jogo.timeVitima = "CT"
                        jogo.jogador = linha.substring(linha.indexOf("\"") + 1, linha.indexOf("<"));
                        def subLinha = linha.substring(linha.indexOf("killed \""));
                        jogo.vitima = subLinha.substring(subLinha.indexOf("\"") + 1, subLinha.indexOf("<"));
                        //print data + " - " + jogador + " Deu faca em " + vitima + " às " + hora;
                        println jogo.jogador

                        jogo.validate()
                        if(!jogo.hasErrors()){
                            jogo.save(flush:true)
                            println "Salvou com sucesso!"
                        }else{
                            println "Não salvou!"
                        }
                    }
                }
            }


        }
        //apaga o diretorio com os logs apos o processamento
        File dir = new File("uploadLogs");
        if(!dir.isHidden()){  //se a pasta existe, apague-a
            dir.deleteDir();
            println "Apagou a pasta uploadLogs!"
        }

    }

    def teste() {
        File dir = new File("uploadLogs");
        new File("uploadLogs").eachFile() { file->
            println file.getAbsolutePath()
        }
    }
}

