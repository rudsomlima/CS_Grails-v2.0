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

//            List<Jogador> matadorList = new ArrayList<Jogador>()
//            List<Jogador> assassinatoList = new ArrayList<Vitima>()

            new File("grails-app/uploadLogs").eachFile { file->
                //Renderiza o numero do arquivo
                progresso = 100 * atual / n_arquivo
                atual++

                println "Quantidade de arquivos: $n_arquivo"
                Date dataFinal

                file.withReader { reader->
                    while ((linha = reader.readLine())!=null) {
                        n_linha++;

                        //////////////////////// FACADAS ////////////////////////////////////
                        if(linha.contains("with \"knife\"")) {
                            //Nome do Matador
                            def nomeMatador = linha.substring(linha.indexOf("\"") + 1, linha.indexOf("<"));
                            def matadorList = Jogador.findAllByNomeIlike(nomeMatador)
                            Jogador matador = new Jogador()
                            if(matadorList.empty){
                                matador.nome = nomeMatador
                                matador.save flush:true
                                println "Salvou Jogador com sucesso!"
                            }else{
                                matador = matadorList.get(0)
                            }

                            //Somente para achar o nome Killed
                            def subLinha = linha.substring(linha.indexOf("killed \""));

                            //Extrai o nome da Vitima
                            def nomeVitima = subLinha.substring(subLinha.indexOf("\"") + 1, subLinha.indexOf("<"));
                            def vitimaList = Jogador.findAllByNome(nomeVitima)

                            Jogador vitima = new Jogador()

                            if(vitimaList.empty){
                                vitima.nome = nomeVitima
                                vitima.save flush:true
                                println "Salvou com sucesso!"
                            }else{
                                vitima = vitimaList.get(0)
                            }

                            Vitima assassinato = new Vitima()

                            assassinato.matador = matador
                            assassinato.vitima = vitima

                            Facadas facada = new Facadas()
                            facada.qtdeFacadas = 1
                            String dataFacada =  linha.substring(2, 12)
//                            println "Data original: " + dataFacada
                            Date dataFacadaFormatada = Date.parse('MM/dd/yyyy',dataFacada)
//                            println "Data convertida: " + dataFacadaFormatada.format('yyyy-MM-dd')
                            String dataFormatada = dataFacadaFormatada.format('yyyy-MM-dd')
                            dataFinal = Date.parse('yyyy-MM-dd',dataFormatada)
                            dataLog = dataFormatada + " 00:00:00"
                            facada.dataFacada =  dataFinal
                            facada.vitima = assassinato
                            assassinato.dataFacada = dataFinal
                            if(boaTarde==0) assassinato.boaTarde = 1   //define que o jogador levou boa tarde

                            def existeAssassinato = Vitima.findAllByMatadorAndVitimaAndDataFacada(matador,vitima,dataFinal)
                            println "O assassinato encontrato é: " + assassinato

                            if(existeAssassinato.empty){
                                assassinato.save flush:true
                                boaTarde = 1 // para de registrar o boa tarde
                                println "Salvou com sucesso o assassinato!"
                            }else{
                                assassinato = existeAssassinato.get(0)
                                println "Achou o assassinato: " + assassinato
                            }

                            def existeFacadas = Facadas.findAllByVitima(assassinato)
                            println "Foram encontradas as seguintes facadas: " + existeFacadas

                            if(existeFacadas.empty){
                                facada.save flush:true
                                println "Salvou com sucesso a facada!"
                            }else{
                                facada = existeFacadas.get(0)
//                                println "Facada:" + facada + " Quantidade de facadas antes: " + facada.qtdeFacadas
                                //Automatic Dirty Detection - persiste a alteração mesmo sem mandarmos salvar. Caso não se queira
                                //o comportamento, usar read ao invés de get()
                                //fonte: http://stackoverflow.com/questions/32503852/grails-2-4-4-updating-a-user-auto-saves-user-before-hitting-back-end-code
                                facada.qtdeFacadas +=1
//                                println "Facada:" + facada + " Quantidade de facadas depois: " + facada.qtdeFacadas
                            }
                        }

                        //////////////////////////////// TIROS ////////////////////////////////////////////////////////////////
                        if(linha.contains(">\" with \"") && !linha.contains("with \"knife\"")) {
                            //Nome do Matador
                            println n_linha + " - " + linha
                            def nomeMatador = linha.substring(linha.indexOf("\"") + 1, linha.indexOf("<"));
                            def matadorList = Jogador.findAllByNomeIlike(nomeMatador)
                            Jogador matador = new Jogador()
                            if (matadorList.empty) {
                                matador.nome = nomeMatador
                                matador.save flush:true
                                println "Salvou Jogador com sucesso!"
                            } else {
                                matador = matadorList.get(0)
                                println "Já existe jogador matador"
                            }

                            //Somente para achar o nome Killed
                            def subLinha = linha.substring(linha.indexOf("killed \""));

                            //Extrai o nome da Vitima
                            def nomeVitima = subLinha.substring(subLinha.indexOf("\"") + 1, subLinha.indexOf("<"));
                            def vitimaList = Jogador.findAllByNomeIlike(nomeVitima)

                            Jogador vitima = new Jogador()
//
                            if(vitimaList.empty){
                                vitima.nome = nomeVitima
                                vitima.save flush:true
                                println "Salvou com vitima com sucesso!"
                            }else{
                                vitima = vitimaList.get(0)
                                println "Já existe jogador vitima"
                            }
//
                            def existeAssassinato = Vitima.findAllByMatadorAndVitimaAndDataFacada(matador,vitima,dataFinal)
                            println "Já existe tiro do: " + matador + " - " + vitima

                            if(existeAssassinato.empty){
                                vitima.save flush:true
                                println "Salvou com sucesso o assassinato de tiro!"
                            }else{
                                vitima = existeAssassinato.get(0)
                                println "Já existe esse assassinato"
                            }

                            Jogador jogador = Jogador.findAllByNome(matador.nome).get(0)

                            //Aqui ele pode trazer uma lista e fizemos o teste do codigo pegando o indice 0
                            //Trocar para ArrayList
                            List<Vitima> vit = Vitima.findAllByVitima(jogador)
                            if(vit.size()==0) {
                                println "Não achou vitima"
                            }

                            //Aqui ele pode trazer uma lista e fizemos o teste do codigo pegando o indice 0
                            //Trocar para ArrayList
                            List<Vitima> mat = Vitima.findAllByMatador(jogador)
                            if(mat.size()==0) {
                                println "Não achou matador"
                            }

                            def tirosListVitima = Tiros.findAllByVitima(vit)
                            def tirosListMatador = Tiros.findAllByVitima(mat)

                            println "Vitimas dentro de Tiros: " + tirosListVitima
                            println "Matadores dentro de Tiros: " + tirosListMatador

                            Tiros tiros = new Tiros()
                            tiros.qtdeTiros = 1
                            tiros.dataTiro =  dataFinal
//                            tiros.vitima.vitima.nome = tirosListVitima.get(0).vitima.vitima.nome
//                            tiros.vitima.matador = tirosListMatador.get(0)

                            if(tirosListVitima.empty){
                                tiros.save flush:true
                                println "Salvou com sucesso o tiro: $matador - $vitima"
                            }else{
//                                tiros = tirosList.get(0)
////                                println "Tiro:" + tiros + " Quantidade de tiros antes: " + tiros.qtdeTiros
                                //Automatic Dirty Detection - persiste a alteração mesmo sem mandarmos salvar. Caso não se queira
                                //o comportamento, usar read ao invés de get()
                                //fonte: http://stackoverflow.com/questions/32503852/grails-2-4-4-updating-a-user-auto-saves-user-before-hitting-back-end-code
                                tiros.qtdeTiros +=1
                                tiros.save flush:true
////                                println "Tiro:" + tiros + " Quantidade de tiros depois: " + tiros.qtdeTiros
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
