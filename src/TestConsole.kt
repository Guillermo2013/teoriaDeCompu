
/**
 * Created by PC on 04/08/2016.
 */
fun main(args : Array<String>) {
    var Estadoa =Estado("a",false)
    var Estadob=Estado("b",false)
    var Estadoc=Estado("c",false)
    var Estadod=Estado("d",true)
    var Estadoe=Estado("e",false)
    var Estadof=Estado("f",false)
    var DFA:DFA=DFA(mutableListOf("0","1"),mutableListOf(Estadoa,Estadob,Estadoc,Estadod,Estadoe,Estadof),Estadoa,mutableListOf())

    DFA.insertarTransacion(Transicion(Estadoa,Estadob,"0"))
    DFA.insertarTransacion(Transicion(Estadoa,Estadoc,"1"))
    DFA.insertarTransacion(Transicion(Estadob,Estadoa,"0"))
    DFA.insertarTransacion(Transicion(Estadob,Estadod,"1"))
    DFA.insertarTransacion(Transicion(Estadoc,Estadoe,"0"))
    DFA.insertarTransacion(Transicion(Estadoc,Estadof,"1"))
    DFA.insertarTransacion(Transicion(Estadod,Estadoe,"0"))
    DFA.insertarTransacion(Transicion(Estadod,Estadof,"1"))
    DFA.insertarTransacion(Transicion(Estadoe,Estadoe,"0"))
    DFA.insertarTransacion(Transicion(Estadoe,Estadof,"1"))
    DFA.insertarTransacion(Transicion(Estadof,Estadof,"0"))
    DFA.insertarTransacion(Transicion(Estadof,Estadof,"1"))
    DFA.Minimizar()
   /* for (estados in DFA.estados){
        println(estados.NombreEstado)
    }*/

}