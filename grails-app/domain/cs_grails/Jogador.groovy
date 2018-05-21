package cs_grails

class Jogador {

    String nome

    String toString() {
        this.nome
    }

    static constraints = {
        nome nullable:false, blank:false
    }


}
