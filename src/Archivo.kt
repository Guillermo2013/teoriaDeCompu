import java.awt.event.ActionEvent
import java.io.*

class Archivo : javax.swing.JFrame(),Serializable {

    fun guardarAutomata(Automata:Automatas,carpeta:String,nombre:String){
        val fos = FileOutputStream(carpeta+"/"+nombre+".txt")
        val out = ObjectOutputStream(fos)
        var Alfabeto = ""
        for (alfabeto in Automata.alfabeto){
            Alfabeto += alfabeto+","
        }
        out.writeObject("Alfabeto")
        out.writeObject(Alfabeto)
        out.writeObject("Estados")
        for (estados in Automata.estados){
            out.writeObject(estados.NombreEstado+","+estados.EsAcceptable+","+estados.Vertex)
        }
        out.writeObject("EstadoInicial")
        out.writeObject(Automata.estadoInicial.NombreEstado)
        out.writeObject("Transaciones")
        for (Transacion in Automata.transiciones){
            out.writeObject(Transacion.EstadoInicial.NombreEstado+","+Transacion.EstadoFinal.NombreEstado+","+Transacion.Simbolo)
        }
        out.writeObject("end")
    }
    fun leerAutomata(path:String):DFA{
        val fis = FileInputStream(path)
        val `in` = ObjectInputStream(fis)
        var Automata : DFA = DFA(mutableListOf(),mutableListOf(),Estado("",false), mutableListOf())
        var objeto =" "
        while (!objeto.equals("end")){
            objeto = `in`.readObject() as String
            if(objeto.equals("Alfabeto")){
                objeto = `in`.readObject() as String
                var Alfabeto = objeto.split(",").dropLastWhile { it.isEmpty() }.toTypedArray()
                for (alfabeto in Alfabeto){
                    Automata.alfabeto.add(alfabeto)
                }
            }
            objeto = `in`.readObject() as String
            if(objeto.equals("Estados")){
                objeto = `in`.readObject() as String
                while(!objeto.equals("EstadoInicial")){
                    var Estado = objeto.split(",").dropLastWhile { it.isEmpty() }.toTypedArray()
                    Automata.insertarEstado(Estado(Estado[0],Estado[1].toBoolean()))
                    Automata.obtenerEstado(Estado[0]).Vertex = Estado[2]
                    objeto = `in`.readObject() as String
                }
            }
            objeto = `in`.readObject() as String
            Automata.estadoInicial = Automata.obtenerEstado(objeto)
            objeto = `in`.readObject() as String
            if(objeto.equals("Transaciones")) {
                objeto = `in`.readObject() as String
                while(!objeto.equals("end")){
                    var transicion = objeto.split(",").dropLastWhile { it.isEmpty() }.toTypedArray()
                    Automata.insertarTransacion(Transicion(Automata.obtenerEstado(transicion[0]),Automata.obtenerEstado(transicion[1]),transicion[2]))
                    objeto = `in`.readObject() as String
                }
            }
        }
        return Automata
    }

}