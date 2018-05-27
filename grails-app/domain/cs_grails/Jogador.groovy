package cs_grails

class Jogador {

    String nome
//    Integer nFacasLevou
//    Integer nFacasDeu

    String toString() {
        this.nome
    }

    static constraints = {
        nome nullable:false, blank:false
    }


}
