package cs_grails

class Tiros {

    int qtdeTiros
    Date dataTiro
    static belongsTo = [vitima:Vitima]

    static constraints = {
        qtdeTiros nullable:false
        vitima nullable:false
    }

}
