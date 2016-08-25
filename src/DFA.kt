
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
        var estado = 0
        while (estado < estados.size) {
            var TransicionesHaciaElestado = transicionesHaciaEl(estados[estado].NombreEstado)
            var TransicionesDesdeElestado = transicionesDesdeEl(estados[estado].NombreEstado)
            for (transicionHacia in TransicionesHaciaElestado) {
                for (transicionDesde in TransicionesDesdeElestado) {
                    if(transicionHacia.EstadoInicial.NombreEstado.equals(transicionDesde.EstadoFinal.NombreEstado)){
                        var EstadoDesde = transicionHaciaElMismo(estados[estado].NombreEstado,transiciones)
                        var SimboloRecursivoEstadoDesde = ""
                        if (!EstadoDesde.isEmpty()){
                            SimboloRecursivoEstadoDesde = EstadoDesde[0].Simbolo+"*"
                        }
                        var  EstadoHacia = transicionHaciaElMismo(transicionHacia.EstadoInicial.NombreEstado,transiciones)
                        if (!EstadoHacia.isEmpty()){
                        var simbolo = ""
                            var x = transiciones.size-1
                            while (x>=0){
                                if(transiciones[x].EstadoInicial.NombreEstado.equals(EstadoHacia[0].EstadoInicial.NombreEstado)&&
                                        transiciones[x].EstadoFinal.NombreEstado.equals(EstadoHacia[0].EstadoFinal.NombreEstado)){
                                    simbolo = transiciones[x].Simbolo+"+"
                                   }
                                x--
                            }

                            transiciones.add(Transicion(EstadoHacia[0].EstadoInicial,EstadoHacia[0].EstadoFinal,simbolo+"("+transicionHacia.Simbolo+SimboloRecursivoEstadoDesde+transicionDesde.Simbolo+")"))
                        }
                        transiciones.add(Transicion(transicionHacia.EstadoInicial,transicionHacia.EstadoInicial,"("+transicionHacia.Simbolo+SimboloRecursivoEstadoDesde+transicionDesde.Simbolo+")"))

                    }
                    if(transicionHacia.EstadoInicial.NombreEstado.equals(transicionDesde.EstadoFinal.NombreEstado)==false){
                        var EstadoDesde = transicionHaciaElMismo(estados[estado].NombreEstado,transiciones)
                        var SimboloRecursivoEstadoDesde = ""
                        if (!EstadoDesde.isEmpty()){
                            SimboloRecursivoEstadoDesde = EstadoDesde[0].Simbolo+"*"
                        }
                        var caminoDirecto = caminoDirecto(transicionHacia.EstadoInicial.NombreEstado,transicionDesde.EstadoFinal.NombreEstado)
                        if(!caminoDirecto.Simbolo.equals("")){
                            var simbolo =""
                            var x = transiciones.size - 1
                            while (x >= 0) {
                                if (transiciones[x].EstadoInicial.NombreEstado.equals(caminoDirecto.EstadoInicial.NombreEstado)
                                        || transiciones[x].EstadoInicial.NombreEstado.equals(caminoDirecto.EstadoInicial.NombreEstado)) {
                                   simbolo= transiciones[x].Simbolo

                                }
                                x--
                            }
                            transiciones.add(Transicion(transicionDesde.EstadoInicial,transicionHacia.EstadoFinal,simbolo+"("+transicionHacia.Simbolo+SimboloRecursivoEstadoDesde+transicionDesde.Simbolo+")"))

                        }
                        transiciones.add(Transicion(transicionDesde.EstadoInicial,transicionHacia.EstadoFinal,"("+transicionHacia.Simbolo+SimboloRecursivoEstadoDesde+transicionDesde.Simbolo+")"))

                    }

                }
            }

            estado++
        }
        estado = 0
        while (estado < estados.size) {
            if (estados[estado].EsAcceptable == false && estados[estado].NombreEstado.equals(estadoInicial.NombreEstado) == false) {
                var x = transiciones.size - 1
                while (x >= 0) {
                    if (transiciones[x].EstadoInicial.NombreEstado.equals(estados[estado].NombreEstado)
                            || transiciones[x].EstadoFinal.NombreEstado.equals(estados[estado].NombreEstado)) {
                        transiciones.removeAt(index = x)
                    }
                    x--
                }
            }
          estado ++
        }
        return transiciones
    }

    fun caminoDirecto(nombreEstado: String, nombreEstado1: String):Transicion {
        var x = transiciones.size - 1
        var transicion :Transicion = Transicion(Estado("",false),Estado("",false),"")
        while (x >= 0) {
            if (transiciones[x].EstadoInicial.NombreEstado.equals(nombreEstado)
                    || transiciones[x].EstadoFinal.NombreEstado.equals(nombreEstado1)) {
                transicion = transiciones[x]
            }
            x--
        }
       return transicion
    }


    fun transicionHaciaElMismo(nombreEstado: String,transiciones: MutableList<Transicion>): MutableList<Transicion> {
        var transicion :MutableList<Transicion> = mutableListOf()
        for(transiciones in transiciones){
            if ( transiciones.EstadoInicial.NombreEstado.equals(nombreEstado)&&
                    transiciones.EstadoFinal.NombreEstado.equals(nombreEstado)
            ){
                transicion.add(transiciones)
            }
        }
         var x=0
         while (x < transiciones.size){
             for(transicion in transicion){
                 if (transicion.EstadoInicial.NombreEstado.equals(transiciones.get(x).EstadoInicial.NombreEstado)&&
                         transicion.EstadoFinal.NombreEstado.equals(transiciones.get(x).EstadoFinal.NombreEstado)&&
                         transicion.Simbolo.equals(transiciones.get(x).Simbolo)){
                            transiciones.removeAt(x)
                         }
             }
             x++
         }
        var simbolo = "("
        if(transicion.size>1){
           for(transicion1 in transicion){
               if(transicion1.equals(transicion.get(transicion.size-1))){
                   simbolo += transicion1.Simbolo
               }else{
                   simbolo += (transicion1.Simbolo+"+")
               }
           }
            simbolo += ")"
            transicion.clear()
            transicion.add(Transicion(obtenerEstado(nombreEstado),obtenerEstado(nombreEstado),simbolo))
        }

        return transicion
    }

    fun  transicionesDesdeEl(nombreEstado: String): MutableList<Transicion> {
        var lista : MutableList<Transicion> = mutableListOf()
        for (transicion in transiciones){
            if (transicion.EstadoInicial.NombreEstado.equals(nombreEstado)==true && transicion.EstadoFinal.NombreEstado.equals(nombreEstado)==false){
                    lista.add(transicion)

            }
        }
        return lista
    }

    fun  transicionesHaciaEl(nombreEstado: String): MutableList<Transicion> {
        var lista : MutableList<Transicion> = mutableListOf()
            for (transicion in transiciones){
                if (transicion.EstadoFinal.NombreEstado.equals(nombreEstado)){
                    lista.add(transicion)
                }
            }
        return lista
    }
}