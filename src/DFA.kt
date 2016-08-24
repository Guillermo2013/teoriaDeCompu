
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
    fun ConvertirER(): MutableList<Transicion> {
        var transiciones = transiciones
        for (estados in estados){
         if (!estados.EsAcceptable && !estadoInicial.NombreEstado.equals(estados.NombreEstado)){
             var TransicionesHaciaElestado =transicionesHaciaEl(estados.NombreEstado)
             var TransicionesDesdeElestado =transicionesDesdeEl(estados.NombreEstado)
             for (transicionHacia in TransicionesHaciaElestado){
                for(transicionDesde in TransicionesDesdeElestado){
                    if (transicionHacia.EstadoInicial.NombreEstado.equals(transicionDesde.EstadoFinal.NombreEstado)&&!transicionHaciaElMismo(estados.NombreEstado).isEmpty()&&
                        transicionHaciaElMismo(transicionHacia.EstadoInicial.NombreEstado).isEmpty()){
                        var simbolo = "("+transicionHacia.Simbolo+transicionHaciaElMismo(estados.NombreEstado)+"*"+transicionDesde.Simbolo+")"
                        transiciones.add(Transicion(transicionHacia.EstadoInicial,transicionHacia.EstadoInicial,simbolo))
                    }
                    else if(transicionHacia.EstadoInicial.NombreEstado.equals(transicionDesde.EstadoFinal.NombreEstado)&&!transicionHaciaElMismo(estados.NombreEstado).isEmpty()&&
                            !transicionHaciaElMismo(transicionHacia.EstadoInicial.NombreEstado).isEmpty()){
                        var simbolo = "("+transicionHacia.Simbolo+transicionHaciaElMismo(estados.NombreEstado)+"*"+transicionDesde.Simbolo+")"
                        for (transicion in transiciones){
                            if(transicion.EstadoInicial.NombreEstado.equals(transicionHacia.EstadoInicial.NombreEstado)&&
                                    transicion.EstadoFinal.NombreEstado.equals(transicionHacia.EstadoInicial.NombreEstado)){
                                transicion.Simbolo = transicionHaciaElMismo(transicion.EstadoInicial.NombreEstado)[0].Simbolo+"+"+ simbolo
                            }
                        }
                    }
                    else if (!transicionHaciaElMismo(estados.NombreEstado).isEmpty()){
                        var Simbolo = "("+transicionHacia.Simbolo+transicionHaciaElMismo(estados.NombreEstado)[0].Simbolo+"*"+transicionDesde.Simbolo+")"
                        transiciones.add(Transicion(transicionHacia.EstadoInicial,transicionDesde.EstadoFinal,Simbolo))
                    }
                    else{
                        var Simbolo = "("+transicionHacia.Simbolo+transicionDesde.Simbolo+")"
                        transiciones.add(Transicion(transicionHacia.EstadoInicial,transicionDesde.EstadoFinal,Simbolo))
                    }
                }
             }
             var x = 0
             while (x<transiciones.size){
                 if(transiciones[x].EstadoInicial.NombreEstado.equals(estados.NombreEstado)||transiciones[x].EstadoFinal.NombreEstado.equals(estados.NombreEstado)){
                     transiciones.removeAt(x)
                 }
                 x++
             }
         }

        }
        return transiciones
    }

    private fun transicionHaciaElMismo(nombreEstado: String): MutableList<Transicion> {
        var transicion :MutableList<Transicion> = mutableListOf()
        for(transiciones in transiciones){
            if ( transiciones.EstadoInicial.NombreEstado.equals(nombreEstado)&&
                    transiciones.EstadoFinal.NombreEstado.equals(nombreEstado)
            ){
                transicion.add(transiciones)
            }
        }
        var simbolo = ""
        if(transicion.size>1){
           for(transicion1 in transicion){
               if(transicion1.equals(transicion.get(transicion.size))){
                   simbolo += transicion1.Simbolo
               }else{
                   simbolo += ("("+transicion1.Simbolo+"+")
               }
           }
            simbolo += ")"
            transicion.clear()
            transicion.add(Transicion(obtenerEstado(nombreEstado),obtenerEstado(nombreEstado),simbolo))
        }

        return transicion
    }

    private fun  transicionesDesdeEl(nombreEstado: String): MutableList<Transicion> {
        var lista : MutableList<Transicion> = mutableListOf()
        for (transicion in transiciones){
            if (transicion.EstadoInicial.NombreEstado.equals(nombreEstado)){
                if(!transicion.EstadoInicial.NombreEstado.equals(nombreEstado))
                    lista.add(transicion)
            }
        }
        return lista
    }

    private fun  transicionesHaciaEl(nombreEstado: String): MutableList<Transicion> {
        var lista : MutableList<Transicion> = mutableListOf()
            for (transicion in transiciones){
                if (transicion.EstadoFinal.NombreEstado.equals(nombreEstado)){
                    lista.add(transicion)
                }
            }
        return lista
    }
}