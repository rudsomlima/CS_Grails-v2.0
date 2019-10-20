package cs_grails

class Vitima {

    //Mapeia o atributo da classe Jogador
    static mappedBy = [matador: 'nome',vitima: 'nome']

//    static hasMany = [facadas:Facadas,tiros:Tiros]

    static belongsTo = [matador:Jogador, vitima:Jogador]

    Date dataFacada
    Integer ehBot
    Integer ehFaca
    Integer facaAmiga
    Integer ordemFacada

    static constraints = {
        matador nullable:false
        vitima nullable:false
    }

    String toString() {
        "$matador - $vitima"
    }
}