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
    val fos = FileOutputStream("fichero.bin")
    val fis = FileInputStream("fichero.bin")
    val out = ObjectOutputStream(fos)
    val `in` = ObjectInputStream(fis)


    var DFA3 : DFA = DFA(mutableListOf(),mutableListOf(),Estado("",false), mutableListOf())
    var objeto =" "

    while (!objeto.equals("end")){
        objeto = `in`.readObject() as String
        if(objeto.equals("Alfabeto")){
            objeto = `in`.readObject() as String
            var Alfabeto = objeto.split(",").dropLastWhile { it.isEmpty() }.toTypedArray()
            for (alfabeto in Alfabeto){
                DFA3.alfabeto.add(alfabeto)
            }
        }
        objeto = `in`.readObject() as String
        if(objeto.equals("Estados")){
            objeto = `in`.readObject() as String
            while(!objeto.equals("EstadoInicial")){
                var Estado = objeto.split(",").dropLastWhile { it.isEmpty() }.toTypedArray()
                DFA3.insertarEstado(Estado(Estado[0],Estado[1].toBoolean()))
                DFA3.obtenerEstado(Estado[0]).Vertex = Estado[2]
                objeto = `in`.readObject() as String
            }
        }
        objeto = `in`.readObject() as String
        DFA3.estadoInicial = DFA3.obtenerEstado(objeto)
        objeto = `in`.readObject() as String
        if(objeto.equals("Transaciones")) {
            objeto = `in`.readObject() as String
            while(!objeto.equals("end")){
                var transicion = objeto.split(",").dropLastWhile { it.isEmpty() }.toTypedArray()
                DFA3.insertarTransacion(Transicion(DFA3.obtenerEstado(transicion[0]),DFA3.obtenerEstado(transicion[1]),transicion[2]))
                objeto = `in`.readObject() as String
            }
        }
    }
    for(estados in DFA3.estados){
        println(estados.NombreEstado+" "+estados.EsAcceptable)
    }
    for(transiciones in DFA3.transiciones){
        println(transiciones.EstadoInicial.NombreEstado+" "+transiciones.EstadoFinal.NombreEstado+" "+transiciones.Simbolo )
    }

}

