
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
    fun CrearExpresionRegular():String{
     var ExpresionRegular = ""
        for(estados in estados){
                if(estados.EsAcceptable){
                    ExpresionRegular += ER(DejarUnSoloEstado(estados.NombreEstado),transiciones)
                }
        }

        return ExpresionRegular
    }

     fun DejarUnSoloEstado(nombreEstado: String): MutableList<Estado> {
         var Estados : MutableList<Estado> = mutableListOf()
         for (estado in estados){
           if(estado.NombreEstado.equals(nombreEstado)){
               estado.EsAcceptable = true
               Estados.add(estado)
           }else{
               estado.EsAcceptable = false
               Estados.add(estado)
           }

        }
        return Estados
    }

    fun ER(estados:MutableList<Estado>,transiciones: MutableList<Transicion>): String {
        var estado = 0
        while (estado < estados.size) {
            var TransicionesHaciaElestado = transicionesHaciaEl(estados[estado].NombreEstado,transiciones)
            var TransicionesDesdeElestado = transicionesDesdeEl(estados[estado].NombreEstado,transiciones)
            for (transicionHacia in TransicionesHaciaElestado) {
                for (transicionDesde in TransicionesDesdeElestado) {
                    if (caminoDirecto(transicionHacia.EstadoInicial.NombreEstado, transicionDesde.EstadoFinal.NombreEstado, transiciones).Simbolo.equals("")==true&&
                            transicionHacia.EstadoInicial.NombreEstado.equals(transicionDesde.EstadoFinal.NombreEstado)==false
                                    &&estados[estado].EsAcceptable==false
                    ){
                        var simboloDeRetorno = transicionHaciaElMismo(estados[estado].NombreEstado,transiciones)
                        if(!simboloDeRetorno.isEmpty()){
                            transiciones.add(Transicion(transicionHacia.EstadoInicial,transicionDesde.EstadoFinal,"("+transicionHacia.Simbolo+simboloDeRetorno[0].Simbolo+"*"+transicionDesde.Simbolo+")"))
                        }else{
                            transiciones.add(Transicion(transicionHacia.EstadoInicial,transicionDesde.EstadoFinal,"("+transicionHacia.Simbolo+transicionDesde.Simbolo+")"))

                        }
                       }
                   else if(caminoDirecto(transicionHacia.EstadoInicial.NombreEstado,transicionDesde.EstadoFinal.NombreEstado,transiciones).Simbolo.equals("")==false&&
                            caminoDirecto(transicionHacia.EstadoInicial.NombreEstado, transicionDesde.EstadoFinal.NombreEstado, transiciones).Simbolo.length<=1
                    &&transicionHacia.EstadoInicial.NombreEstado.equals(transicionHacia.EstadoFinal.NombreEstado)==false
                            &&estados[estado].EsAcceptable==false){
                        var caminoDirectoSimbolo =  caminoDirecto(transicionHacia.EstadoInicial.NombreEstado, transicionDesde.EstadoFinal.NombreEstado, transiciones).Simbolo
                        var simboloDeRetorno = transicionHaciaElMismo(estados[estado].NombreEstado,transiciones)
                        var cerradura = ""
                        if(!simboloDeRetorno.isEmpty()){
                            cerradura = simboloDeRetorno[0].Simbolo +"*"
                        }
                        var simboloAconcatenar = ""
                        var x = 0
                        while (x<transiciones.size){
                            if(transiciones[x].EstadoInicial.NombreEstado.equals(transicionHacia.EstadoInicial.NombreEstado)&&
                                    transiciones[x].EstadoFinal.NombreEstado.equals(transicionDesde.EstadoFinal.NombreEstado)){
                                simboloAconcatenar = transiciones[x].Simbolo+"+"
                                transiciones.removeAt(x)
                            }
                            x++
                        }
                        var simbolo = simboloAconcatenar + "("+transicionHacia.Simbolo+cerradura+transicionDesde.Simbolo+")"
                        transiciones.add(Transicion(transicionHacia.EstadoInicial,transicionDesde.EstadoFinal,simbolo))
                       }else if (transicionDesde.EstadoInicial.NombreEstado.equals(transicionHacia.EstadoFinal.NombreEstado)
                            &&transicionHacia.EstadoInicial.NombreEstado.equals(transicionHacia.EstadoFinal.NombreEstado)==false
                            &&estados[estado].EsAcceptable==false){
                        var simboloDeRetorno = transicionHaciaElMismo(estados[estado].NombreEstado,transiciones)
                        var cerradura = ""
                        if(!simboloDeRetorno.isEmpty()){
                            cerradura = simboloDeRetorno[0].Simbolo +"*"
                        }
                        var simboloAconcatenar = ""
                        var x = 0
                        while (x<transiciones.size){
                            if(transiciones[x].EstadoInicial.NombreEstado.equals(transicionHacia.EstadoInicial.NombreEstado)&&
                                    transiciones[x].EstadoFinal.NombreEstado.equals(transicionHacia.EstadoInicial.NombreEstado)){
                                simboloAconcatenar = transiciones[x].Simbolo+"+"
                                transiciones.removeAt(x)
                            }
                            x++
                        }
                        var simbolo = simboloAconcatenar + "("+transicionHacia.Simbolo+cerradura+transicionDesde.Simbolo+")"
                        transiciones.add(Transicion(transicionHacia.EstadoInicial,transicionDesde.EstadoFinal,simbolo))
}
                }
            }
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
            estado++
        }
        var Expresion = ""
        for (estado in estados){
            if (estado.NombreEstado.equals(estadoInicial.NombreEstado)){
                if(estado.EsAcceptable){
                    return "("+transicionHaciaElMismo(estadoInicial.NombreEstado,transiciones)[0].Simbolo+")*"
                }
            }
        }
            var R =""
            var S = ""
            var U =""
            var T= ""
            for (transicion in transiciones){
                if(transicion.EstadoInicial.equals(estadoInicial.NombreEstado)&&transicion.EstadoFinal.equals(estadoInicial.NombreEstado)){
                    R = transicion.Simbolo+"*"
                }else if (transicion.EstadoFinal.equals(estadoInicial.NombreEstado)&&transicion.EstadoInicial.equals(estadoInicial.NombreEstado)==false){
                    T = transicion.Simbolo
                }else if(!transicion.EstadoInicial.equals(estadoInicial.NombreEstado)&&!transicion.EstadoFinal.equals(estadoInicial.NombreEstado)){
                    U = transicion.Simbolo+"*"
                }

            }
            S = transicionesDesdeEl(estadoInicial.NombreEstado, transiciones)[0].Simbolo

            Expresion = "("+R+S+U+")("+T+R+S+U+")*"


        return Expresion
    }

    fun caminoDirecto(nombreEstado: String, nombreEstado1: String, transiciones: MutableList<Transicion>):Transicion {
        var x = transiciones.size - 1
        var transicion :Transicion = Transicion(Estado("",false),Estado("",false),"")
        while (x >= 0) {
            if (this.transiciones[x].EstadoInicial.NombreEstado.equals(nombreEstado)
                    && this.transiciones[x].EstadoFinal.NombreEstado.equals(nombreEstado1)) {
                transicion = this.transiciones[x]
            }
            x--
        }
       return transicion
    }


    fun transicionHaciaElMismo(nombreEstado: String,transiciones: MutableList<Transicion>): MutableList<Transicion> {
        var transicionlist :MutableList<Transicion> = mutableListOf()
        for(transiciones in transiciones){
            if ( transiciones.EstadoInicial.NombreEstado.equals(nombreEstado)&&transiciones.EstadoFinal.NombreEstado.equals(nombreEstado)){

                transicionlist.add(transiciones)
            }
        }

        return transicionlist
    }

    fun  transicionesDesdeEl(nombreEstado: String, transiciones: MutableList<Transicion>): MutableList<Transicion> {
        var lista : MutableList<Transicion> = mutableListOf()
        for (transicion in transiciones){
            if (transicion.EstadoInicial.NombreEstado.equals(nombreEstado)==true){
                if(transicion.EstadoFinal.NombreEstado.equals(nombreEstado)==false){
                    lista.add(transicion)
                }
            }
        }
        return lista
    }

    fun  transicionesHaciaEl(nombreEstado: String, transiciones: MutableList<Transicion>): MutableList<Transicion> {
        var lista : MutableList<Transicion> = mutableListOf()
            for (transicion in transiciones){
                if (transicion.EstadoFinal.NombreEstado.equals(nombreEstado)) {
                    lista.add(transicion)
                }
            }
        return lista
    }
}