package cs_grails

class Jogador {

    String nome

    static hasMany = [vitima:Vitima]

    String toString() {
        this.nome
    }

    static constraints = {
        nome nullable:false, blank:false
    }


}
