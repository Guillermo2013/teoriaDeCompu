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
    var PDA = PDA(mutableListOf(), mutableListOf(),Estado("",false), mutableListOf())
    var DFA3 = PDA.GramaticaToPDA("E->E+T|T,T->T*F|F,F->DIGITO|(E),DIGITO->0|1|2|3|4|5|6|7|8|9")
    for(estados in DFA3.estados){
        println(estados.NombreEstado+" "+estados.EsAcceptable)
    }
    for(transiciones in DFA3.transiciones){
        println(transiciones.EstadoInicial.NombreEstado+" "+transiciones.EstadoFinal.NombreEstado+" "+transiciones.Simbolo )
    }

}

