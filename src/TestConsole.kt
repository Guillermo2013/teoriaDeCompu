import Archivo
import automatas.PDA
import java.awt.FlowLayout
import java.awt.event.ActionEvent
import java.awt.event.ActionListener
import javax.swing.JFileChooser;
import java.io.File;
import javax.swing.JButton
import javax.swing.JDialog
import javax.swing.JFrame

/**
 * Created by PC on 04/08/2016.
 */
fun main(args : Array<String>) {
    var Estado0 = Estado("0",false)
    var Estado1 = Estado("1",false)
    var Estado2 = Estado("2",true)
    var DFA1 : DFA = DFA(mutableListOf("1","0"),mutableListOf(Estado0,Estado1,Estado2),Estado0, mutableListOf())
    DFA1.insertarTransacion(Transicion(Estado0,Estado0,"0"))
    DFA1.insertarTransacion(Transicion(Estado0,Estado1,"1"))
    DFA1.insertarTransacion(Transicion(Estado1,Estado2,"0"))
    DFA1.insertarTransacion(Transicion(Estado1,Estado1,"1"))
    DFA1.insertarTransacion(Transicion(Estado2,Estado1,"0"))
    DFA1.insertarTransacion(Transicion(Estado2,Estado1,"1"))
    var Estadoa = Estado("a",false)
    var Estadob = Estado("b",false)
    var Estadoc = Estado("c",true)
    var Estadod = Estado("d",true)
    var DFA2 : DFA = DFA(mutableListOf("1","0"),mutableListOf(Estadoa,Estadob,Estadoc,Estadod),Estadoa, mutableListOf())
    DFA2.insertarTransacion(Transicion(Estadoa,Estadob,"0"))
    DFA2.insertarTransacion(Transicion(Estadoa,Estadoa,"1"))
    DFA2.insertarTransacion(Transicion(Estadob,Estadob,"0"))
    DFA2.insertarTransacion(Transicion(Estadob,Estadoc,"1"))
    DFA2.insertarTransacion(Transicion(Estadoc,Estadod,"0"))
    DFA2.insertarTransacion(Transicion(Estadoc,Estadoa,"1"))
    DFA2.insertarTransacion(Transicion(Estadod,Estadod,"0"))
    DFA2.insertarTransacion(Transicion(Estadod,Estadod,"1"))
    var DFA3 : DFA = DFA(mutableListOf("0","1"),mutableListOf(),Estado("",false), mutableListOf())
    var DFA4 = DFA3.crearAutomata(DFA1,DFA2)
    for (estado in DFA4.estados){
        println(estado.NombreEstado)
    }
    for (transiciones in DFA4.transiciones){
        println("Inicial:"+transiciones.EstadoInicial.NombreEstado+" Final:"+transiciones.EstadoFinal.NombreEstado+" Simbolo:"+transiciones.Simbolo)
    }
}

