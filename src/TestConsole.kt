import Archivo
import automatas.PDA

import org.omg.CORBA.Object
import regularexpresion.RegularExpressionParser
import regularexpresion.tree.ANDNode
import regularexpresion.tree.CharNode
import regularexpresion.tree.ORNode
import regularexpresion.tree.RepeatNode
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
    var  expresionRegularConvertida :NFAE = NFAE( mutableListOf() , mutableListOf() , Estado("",false) ,mutableListOf())
    var  expresionRegular = "(a+b)*"
    try {
       var rootNode = RegularExpressionParser().Parse(expresionRegular)
        expresionRegularConvertida.obtainAutomata(rootNode, expresionRegularConvertida)
    } catch (e: Exception) {
        print("nada")
    }
    for(estado in expresionRegularConvertida.estados){
        println(estado.NombreEstado)
    }

    for(transiciones in expresionRegularConvertida.transiciones){
        println(transiciones.EstadoInicial.NombreEstado+" "+transiciones.EstadoFinal.NombreEstado+ " "+ transiciones.Simbolo)
    }
}

