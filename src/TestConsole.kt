
/**
 * Created by PC on 04/08/2016.
 */
fun main(args : Array<String>) {
    var Estado1 =Estado("1",false)
    var Estado2=Estado("2",false)
    var Estado3=Estado("3",false)
    var Estado4=Estado("4",true)
    var Estado5=Estado("5",false)
    var DFA:DFA=DFA(mutableListOf("0","1"),mutableListOf(Estado1,Estado2,Estado3,Estado4,Estado5),Estado1,mutableListOf())
    DFA.insertarTransacion(Transicion(Estado1,Estado2,"0"))
    DFA.insertarTransacion(Transicion(Estado1,Estado4,"1"))
    DFA.insertarTransacion(Transicion(Estado2,Estado3,"0"))
    DFA.insertarTransacion(Transicion(Estado3,Estado2,"0"))
    DFA.insertarTransacion(Transicion(Estado3,Estado4,"1"))
    DFA.insertarTransacion(Transicion(Estado4,Estado4,"1"))
    DFA.insertarTransacion(Transicion(Estado4,Estado5,"0"))
    DFA.insertarTransacion(Transicion(Estado5,Estado4,"0"))


    print(DFA.CrearExpresionRegular())


}