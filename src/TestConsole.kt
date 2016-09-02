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
    var DFA1 : DFA = DFA(mutableListOf("1","0"),mutableListOf(Estado0,Estado1,Estado2),Estado1, mutableListOf())
    DFA1.insertarTransacion(Transicion(Estado0,Estado0,"0"))
    DFA1.insertarTransacion(Transicion(Estado0,Estado1,"1"))
    DFA1.insertarTransacion(Transicion(Estado1,Estado2,"0"))
    DFA1.insertarTransacion(Transicion(Estado1,Estado0,"1"))
    DFA1.insertarTransacion(Transicion(Estado2,Estado1,"0"))
    DFA1.insertarTransacion(Transicion(Estado2,Estado2,"1"))
    Estado2.EsAcceptable = false
    Estado1.EsAcceptable = true
    var DFA2 : DFA = DFA(mutableListOf("1","0"),mutableListOf(Estado0,Estado1),Estado1, mutableListOf())
    DFA1.insertarTransacion(Transicion(Estado0,Estado0,"1"))
    DFA1.insertarTransacion(Transicion(Estado0,Estado1,"0"))
    DFA1.insertarTransacion(Transicion(Estado1,Estado0,"0"))
    DFA1.insertarTransacion(Transicion(Estado1,Estado1,"1"))
    var DFA3 : DFA = DFA(mutableListOf(),mutableListOf(),Estado("",false), mutableListOf())
    


}

