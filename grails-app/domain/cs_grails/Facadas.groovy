package cs_grails

class Facadas {

    int qtdeFacadas
    Date dataFacada
    static belongsTo = [vitima:Vitima]

    static constraints = {
        qtdeFacadas nullable:false
        vitima nullable:false
    }
}
