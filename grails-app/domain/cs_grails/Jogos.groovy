package cs_grails

//import org.grails.databinding.BindingFormat

class Jogos {

    String jogador
    String vitima
    String timeJogador
    String timeVitima
    //Integer facadas
//    @BindingFormat('MMddyyyy')
    String data

    static constraints = {
        jogador nullable:false, blank: false  //nome obrigatorio e nao branco
        //facadas min: 0  //valor minimo aceitavel
    }

}
