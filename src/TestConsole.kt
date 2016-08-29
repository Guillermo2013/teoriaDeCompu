
/**
 * Created by PC on 04/08/2016.
 */
fun main(args : Array<String>) {
    var Estadoa =Estado("a",false)
    var Estadob=Estado("b",false)
    var Estadoc=Estado("c",true)
    var Estadod=Estado("d",false)
    var Estadoe=Estado("e",false)
    var Estadof=Estado("f",false)
    var Estadog=Estado("g",false)
    var Estadoh=Estado("h",false)
    var DFA:DFA=DFA(mutableListOf("0","1"),mutableListOf(Estadoa,Estadob,Estadoc,Estadod,Estadoe,Estadof,Estadog,Estadoh),Estadoa,mutableListOf())
    DFA.insertarTransacion(Transicion(Estadoa,Estadob,"0"))
    DFA.insertarTransacion(Transicion(Estadoa,Estadof,"1"))
    DFA.insertarTransacion(Transicion(Estadob,Estadog,"0"))
    DFA.insertarTransacion(Transicion(Estadob,Estadoc,"1"))
    DFA.insertarTransacion(Transicion(Estadoc,Estadoc,"1"))
    DFA.insertarTransacion(Transicion(Estadod,Estadoc,"0"))
    DFA.insertarTransacion(Transicion(Estadod,Estadog,"1"))
    DFA.insertarTransacion(Transicion(Estadoe,Estadoh,"0"))
    DFA.insertarTransacion(Transicion(Estadoe,Estadof,"1"))
    DFA.insertarTransacion(Transicion(Estadof,Estadoc,"0"))
    DFA.insertarTransacion(Transicion(Estadof,Estadog,"1"))
    DFA.insertarTransacion(Transicion(Estadog,Estadog,"0"))
    DFA.insertarTransacion(Transicion(Estadog,Estadoe,"1"))
    DFA.insertarTransacion(Transicion(Estadoh,Estadog,"0"))
    DFA.insertarTransacion(Transicion(Estadoh,Estadoc,"1"))
    var listaDeEstadoIguales: MutableList<MutableList<Estado>> = mutableListOf()
    var listaDeEstadoNoIguales: MutableList<MutableList<Estado>> = mutableListOf()
   DFA = DFA.Minimizar(listaDeEstadoIguales,listaDeEstadoNoIguales)
    for(estados in DFA.estados){
        println("Estado:"+estados.NombreEstado)
    }
    for(estados in DFA.transiciones){
        println("Estado Inicial:"+estados.EstadoInicial.NombreEstado+" Estado Final:"+estados.EstadoFinal.NombreEstado+ " Simbolo"+ estados.Simbolo)
    }
}