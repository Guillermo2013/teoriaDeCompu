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
    DFA2 = DFA2.complemento(DFA2)
    for (estado in DFA2.estados){
        println(estado.NombreEstado+" "+estado.EsAcceptable)
    }
    for (transiciones in DFA2.transiciones){
        println("Inicial:"+transiciones.EstadoInicial.NombreEstado+" Final:"+transiciones.EstadoFinal.NombreEstado+" Simbolo:"+transiciones.Simbolo)
    }
}

