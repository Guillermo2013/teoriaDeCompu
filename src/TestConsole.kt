
/**
 * Created by PC on 04/08/2016.
 */
fun main(args : Array<String>) {
    var Estado1 =Estado("1",false)
    var Estado2=Estado("2",false)
    var Estado3 =Estado("3",false)
    var Estado4 =Estado("4",false)
    var Estado5 =Estado("5",false)
    var Estado6 =Estado("6",true)
    var NFAE:NFAE=NFAE(mutableListOf("1","0"),mutableListOf(Estado1,Estado2,Estado3,Estado4,Estado5,Estado6),Estado1,mutableListOf())
    NFAE.transiciones.add(Transicion(Estado1,Estado2,"ε"))
    NFAE.transiciones.add(Transicion(Estado1,Estado6,"ε"))
    NFAE.transiciones.add(Transicion(Estado2,Estado3,"1"))
    NFAE.transiciones.add(Transicion(Estado3,Estado4,"ε"))
    NFAE.transiciones.add(Transicion(Estado4,Estado5,"0"))
    NFAE.transiciones.add(Transicion(Estado5,Estado6,"ε"))
    NFAE.transiciones.add(Transicion(Estado5,Estado2,"ε"))

     var DFA:DFA=DFA(mutableListOf(),mutableListOf(),Estado("",false),mutableListOf())
    DFA = NFAE.enDfa()

    println("Estados Inicial:"+DFA.estadoInicial.NombreEstado)
    for (estado in DFA.estados){
        println("Estados:"+estado.NombreEstado+" Es Aceptado:"+estado.EsAcceptable)
    }
    for (estado in DFA.transiciones){
        println("EstadosInicial:"+estado.EstadoInicial.NombreEstado+" EstadoFinal:"+estado.EstadoFinal.NombreEstado+" Simbolo:"+estado.Simbolo)
    }


}