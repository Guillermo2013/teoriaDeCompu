/**
 * Created by PC on 20/07/2016.
 */

class Transicion(estadoInicial: Estado,estadoFinal:Estado,simbolo:String):java.io.Serializable{
    var EstadoInicial : Estado = estadoInicial
    var EstadoFinal : Estado= estadoFinal
    var Simbolo : String = simbolo

}
