
class DFA(alfabeto : MutableList<String>, estados : MutableList<Estado>, estadoInicial : Estado, transiciones  : MutableList<Transicion> ): Automatas(alfabeto,estados,estadoInicial,transiciones) {

    fun evaluarCadena(cadena: String): Boolean {
        if (cadena.equals(" ")) {
            return estadoInicial.EsAcceptable
        }
        if (!verificarCadena(cadena)) {
            return false
        }
        var evaluar = cadena.toCharArray()
        var fin = estadoInicial
        var stay = true
        for (letra in evaluar) {
            for (transicion in transiciones) {
                if (transicion.EstadoInicial.NombreEstado.equals(fin.NombreEstado)&&transicion.Simbolo.equals(letra.toChar())) {
                    fin = transicion.EstadoFinal
                    stay = true
                    break
                } else {
                    stay = false
                }
            }
        }
        if (!stay) {
            return false
        }
        if (fin.EsAcceptable) {
            return true
        }
        return false
    }
    fun validadTransicion(estadoInicial: Estado,estadoFinal:Estado,simbolo:String):Boolean   {
        val transicionAinsertar = Transicion(estadoInicial,estadoFinal,simbolo)
        for (transiciones in transiciones) {
            if (transiciones.EstadoInicial.equals(transicionAinsertar.EstadoInicial) &&
                    transiciones.Simbolo.equals(transicionAinsertar.Simbolo)) {
                return false
            }
        }
            return true
        }
    fun insertarTransacion(Transiciones:Transicion):Boolean{
        if (validadTransicion(Transiciones.EstadoInicial,Transiciones.EstadoFinal,Transiciones.Simbolo)){
           transiciones.add(Transiciones)
            return true
        }
        return false
    }




}