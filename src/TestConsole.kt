import Archivo
import automatas.PDA
import org.omg.CORBA.Object
import java.awt.FlowLayout
import java.awt.event.ActionEvent
import java.awt.event.ActionListener
import java.io.*
import javax.swing.JFileChooser;
import javax.swing.JButton
import javax.swing.JDialog
import javax.swing.JFrame

/**
 * Created by PC on 04/08/2016.
 */
fun main(args : Array<String>) {
    var Estado0 = Estado("q0",false)
    var Estado1 = Estado("q1",false)
    var Estado2 = Estado("q2",true)

   var MT : MDT = MDT( mutableListOf("1","0","B"), mutableListOf(Estado0,Estado1,Estado2),Estado0, mutableListOf())
    MT.insertarTransacion(Transicion(Estado0,Estado0,"0,0,->"))
    MT.insertarTransacion(Transicion(Estado0,Estado0,"1,1,->"))
    MT.insertarTransacion(Transicion(Estado0,Estado1,"B,B,<-"))
    MT.insertarTransacion(Transicion(Estado1,Estado2,"0,0,->"))
    MT.llenarCinta("0000110")
    println("repuesta"+MT.EvaluarCadena("0000110"))
}

