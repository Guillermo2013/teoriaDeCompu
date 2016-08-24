
/**
 * Created by PC on 04/08/2016.
 */
fun main(args : Array<String>) {
    var Estado1 =Estado("1",false)
    var Estado2=Estado("2",false)
    var Estado3 =Estado("3",false)
    var Estado4 =Estado("4",false)
    var Estado5 =Estado("5",true)
    var Estado6 =Estado("6",false)
    var DFA:DFA=DFA(mutableListOf("0","1"),mutableListOf(Estado1,Estado2,Estado3,Estado4,Estado5,Estado6),Estado1,mutableListOf())
    DFA.transiciones.add(Transicion(Estado1,Estado2,"1"))
    DFA.transiciones.add(Transicion(Estado1,Estado6,"0"))
    DFA.transiciones.add(Transicion(Estado2,Estado3,"1"))
    DFA.transiciones.add(Transicion(Estado2,Estado5,"1"))
    DFA.transiciones.add(Transicion(Estado3,Estado4,"1"))
    DFA.transiciones.add(Transicion(Estado3,Estado2,"1"))
    DFA.transiciones.add(Transicion(Estado4,Estado5,"0"))
    DFA.transiciones.add(Transicion(Estado4,Estado1,"0"))
    DFA.transiciones.add(Transicion(Estado5,Estado1,"0"))
    DFA.transiciones.add(Transicion(Estado5,Estado2,"1"))
    DFA.transiciones.add(Transicion(Estado6,Estado4,"1"))
    DFA.transiciones.add(Transicion(Estado6,Estado5,"1"))

     var transicion = DFA.ConvertirER()
    for (transiciones in transicion){
        println("Estado Inicial:"+transiciones.EstadoInicial.NombreEstado+" Estado Final:"+transiciones.EstadoFinal.NombreEstado+" Simbolo:"+transiciones.Simbolo)
    }

}