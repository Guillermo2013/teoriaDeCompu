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
    var PDA : PDA = PDA(mutableListOf("a","b"),mutableListOf(Estado0,Estado1,Estado2),Estado1, mutableListOf())
    PDA.simboloInicial = "Z"
    PDA.simboloActualDePila = "Z"
    PDA.insertarTransacion(Transicion(Estado0,Estado0,"a,Z,AZ"))
    PDA.insertarTransacion(Transicion(Estado0,Estado0,"a,A,AA"))
    PDA.insertarTransacion(Transicion(Estado0,Estado1,"b,A,ε"))
    PDA.insertarTransacion(Transicion(Estado0,Estado2,"ε,Z,Z"))
    PDA.insertarTransacion(Transicion(Estado1,Estado1,"b,A,ε"))
    PDA.insertarTransacion(Transicion(Estado1,Estado2,"ε,Z,Z"))
    print(PDA.EvaluarCadena("aabb"))
}

