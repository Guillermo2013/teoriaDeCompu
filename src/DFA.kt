import com.sun.org.apache.bcel.internal.generic.RETURN

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
                if (transicion.EstadoInicial.NombreEstado.equals(fin.NombreEstado) && transicion.Simbolo.equals(letra.toChar())) {
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

    fun validadTransicion(estadoInicial: Estado, estadoFinal: Estado, simbolo: String): Boolean {
        val transicionAinsertar = Transicion(estadoInicial, estadoFinal, simbolo)
        for (transiciones in transiciones) {
            if (transiciones.EstadoInicial.equals(transicionAinsertar.EstadoInicial) &&
                    transiciones.Simbolo.equals(transicionAinsertar.Simbolo)) {
                return false
            }
        }
        return true
    }

    fun insertarTransacion(Transiciones: Transicion): Boolean {
        if (validadTransicion(Transiciones.EstadoInicial, Transiciones.EstadoFinal, Transiciones.Simbolo)) {
            transiciones.add(Transiciones)
            return true
        }
        return false
    }

    fun CrearExpresionRegular(): String {
        var ExpresionRegular = ""
        for (estados in estados) {
            if (estados.EsAcceptable) {
                ExpresionRegular += ER(DejarUnSoloEstado(estados.NombreEstado), transiciones)
            }
        }

        return ExpresionRegular
    }

    fun DejarUnSoloEstado(nombreEstado: String): MutableList<Estado> {
        var Estados: MutableList<Estado> = mutableListOf()
        for (estado in estados) {
            if (estado.NombreEstado.equals(nombreEstado)) {
                estado.EsAcceptable = true
                Estados.add(estado)
            } else {
                estado.EsAcceptable = false
                Estados.add(estado)
            }

        }
        return Estados
    }

    fun ER(estados: MutableList<Estado>, transiciones: MutableList<Transicion>): String {
        var estado = 0
        while (estado < estados.size) {
            var TransicionesHaciaElestado = transicionesHaciaEl(estados[estado].NombreEstado, transiciones)
            var TransicionesDesdeElestado = transicionesDesdeEl(estados[estado].NombreEstado, transiciones)
            for (transicionHacia in TransicionesHaciaElestado) {
                for (transicionDesde in TransicionesDesdeElestado) {
                    if (caminoDirecto(transicionHacia.EstadoInicial.NombreEstado, transicionDesde.EstadoFinal.NombreEstado, transiciones).Simbolo.equals("") == true &&
                            transicionHacia.EstadoInicial.NombreEstado.equals(transicionDesde.EstadoFinal.NombreEstado) == false
                            && estados[estado].EsAcceptable == false
                    ) {
                        var simboloDeRetorno = transicionHaciaElMismo(estados[estado].NombreEstado, transiciones)
                        if (!simboloDeRetorno.isEmpty()) {
                            transiciones.add(Transicion(transicionHacia.EstadoInicial, transicionDesde.EstadoFinal, "(" + transicionHacia.Simbolo + simboloDeRetorno[0].Simbolo + "*" + transicionDesde.Simbolo + ")"))
                        } else {
                            transiciones.add(Transicion(transicionHacia.EstadoInicial, transicionDesde.EstadoFinal, "(" + transicionHacia.Simbolo + transicionDesde.Simbolo + ")"))

                        }
                    } else if (caminoDirecto(transicionHacia.EstadoInicial.NombreEstado, transicionDesde.EstadoFinal.NombreEstado, transiciones).Simbolo.equals("") == false &&
                            caminoDirecto(transicionHacia.EstadoInicial.NombreEstado, transicionDesde.EstadoFinal.NombreEstado, transiciones).Simbolo.length <= 1
                            && transicionHacia.EstadoInicial.NombreEstado.equals(transicionHacia.EstadoFinal.NombreEstado) == false
                            && estados[estado].EsAcceptable == false) {
                        var caminoDirectoSimbolo = caminoDirecto(transicionHacia.EstadoInicial.NombreEstado, transicionDesde.EstadoFinal.NombreEstado, transiciones).Simbolo
                        var simboloDeRetorno = transicionHaciaElMismo(estados[estado].NombreEstado, transiciones)
                        var cerradura = ""
                        if (!simboloDeRetorno.isEmpty()) {
                            cerradura = simboloDeRetorno[0].Simbolo + "*"
                        }
                        var simboloAconcatenar = ""
                        var x = 0
                        while (x < transiciones.size) {
                            if (transiciones[x].EstadoInicial.NombreEstado.equals(transicionHacia.EstadoInicial.NombreEstado) &&
                                    transiciones[x].EstadoFinal.NombreEstado.equals(transicionDesde.EstadoFinal.NombreEstado)) {
                                simboloAconcatenar = transiciones[x].Simbolo + "+"
                                transiciones.removeAt(x)
                            }
                            x++
                        }
                        var simbolo = simboloAconcatenar + "(" + transicionHacia.Simbolo + cerradura + transicionDesde.Simbolo + ")"
                        transiciones.add(Transicion(transicionHacia.EstadoInicial, transicionDesde.EstadoFinal, simbolo))
                    } else if (transicionDesde.EstadoInicial.NombreEstado.equals(transicionHacia.EstadoFinal.NombreEstado)
                            && transicionHacia.EstadoInicial.NombreEstado.equals(transicionHacia.EstadoFinal.NombreEstado) == false
                            && estados[estado].EsAcceptable == false) {
                        var simboloDeRetorno = transicionHaciaElMismo(estados[estado].NombreEstado, transiciones)
                        var cerradura = ""
                        if (!simboloDeRetorno.isEmpty()) {
                            cerradura = simboloDeRetorno[0].Simbolo + "*"
                        }
                        var simboloAconcatenar = ""
                        var x = 0
                        while (x < transiciones.size) {
                            if (transiciones[x].EstadoInicial.NombreEstado.equals(transicionHacia.EstadoInicial.NombreEstado) &&
                                    transiciones[x].EstadoFinal.NombreEstado.equals(transicionHacia.EstadoInicial.NombreEstado)) {
                                simboloAconcatenar = transiciones[x].Simbolo + "+"
                                transiciones.removeAt(x)
                            }
                            x++
                        }
                        var simbolo = simboloAconcatenar + "(" + transicionHacia.Simbolo + cerradura + transicionDesde.Simbolo + ")"
                        transiciones.add(Transicion(transicionHacia.EstadoInicial, transicionDesde.EstadoFinal, simbolo))
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
        for (estado in estados) {
            if (estado.NombreEstado.equals(estadoInicial.NombreEstado)) {
                if (estado.EsAcceptable) {
                    return "(" + transicionHaciaElMismo(estadoInicial.NombreEstado, transiciones)[0].Simbolo + ")*"
                }
            }
        }
        var R = ""
        var S = ""
        var U = ""
        var T = ""
        for (transicion in transiciones) {
            if (transicion.EstadoInicial.equals(estadoInicial.NombreEstado) && transicion.EstadoFinal.equals(estadoInicial.NombreEstado)) {
                R = transicion.Simbolo + "*"
            } else if (transicion.EstadoFinal.equals(estadoInicial.NombreEstado) && transicion.EstadoInicial.equals(estadoInicial.NombreEstado) == false) {
                T = transicion.Simbolo
            } else if (!transicion.EstadoInicial.equals(estadoInicial.NombreEstado) && !transicion.EstadoFinal.equals(estadoInicial.NombreEstado)) {
                U = transicion.Simbolo + "*"
            }

        }
        S = transicionesDesdeEl(estadoInicial.NombreEstado, transiciones)[0].Simbolo

        Expresion = "(" + R + S + U + ")(" + T + R + S + U + ")*"


        return Expresion
    }

    fun caminoDirecto(nombreEstado: String, nombreEstado1: String, transiciones: MutableList<Transicion>): Transicion {
        var x = transiciones.size - 1
        var transicion: Transicion = Transicion(Estado("", false), Estado("", false), "")
        while (x >= 0) {
            if (this.transiciones[x].EstadoInicial.NombreEstado.equals(nombreEstado)
                    && this.transiciones[x].EstadoFinal.NombreEstado.equals(nombreEstado1)) {
                transicion = this.transiciones[x]
            }
            x--
        }
        return transicion
    }


    fun transicionHaciaElMismo(nombreEstado: String, transiciones: MutableList<Transicion>): MutableList<Transicion> {
        var transicionlist: MutableList<Transicion> = mutableListOf()
        for (transiciones in transiciones) {
            if (transiciones.EstadoInicial.NombreEstado.equals(nombreEstado) && transiciones.EstadoFinal.NombreEstado.equals(nombreEstado)) {

                transicionlist.add(transiciones)
            }
        }

        return transicionlist
    }

    fun transicionesDesdeEl(nombreEstado: String, transiciones: MutableList<Transicion>): MutableList<Transicion> {
        var lista: MutableList<Transicion> = mutableListOf()
        for (transicion in transiciones) {
            if (transicion.EstadoInicial.NombreEstado.equals(nombreEstado) == true) {
                if (transicion.EstadoFinal.NombreEstado.equals(nombreEstado) == false) {
                    lista.add(transicion)
                }
            }
        }
        return lista
    }

    fun transicionesHaciaEl(nombreEstado: String, transiciones: MutableList<Transicion>): MutableList<Transicion> {
        var lista: MutableList<Transicion> = mutableListOf()
        for (transicion in transiciones) {
            if (transicion.EstadoFinal.NombreEstado.equals(nombreEstado)) {
                lista.add(transicion)
            }
        }
        return lista
    }

    fun Minimizar(listaDeEstadoIguales: MutableList<MutableList<Estado>>,listaDeEstadoNoIguales: MutableList<MutableList<Estado>>):DFA {
        var filaEstado = 0
        verificarEstadosAceptados(listaDeEstadoIguales,  listaDeEstadoNoIguales)
        while (filaEstado < estados.size-1) {
            var colummnaEstado = filaEstado + 1
            while (colummnaEstado < estados.size) {
                verificarEstados(estados[colummnaEstado], estados[filaEstado], listaDeEstadoIguales, listaDeEstadoNoIguales)
                colummnaEstado++
            }
            filaEstado++
        }
         quitarRepetidos(listaDeEstadoIguales)
         quitarRepetidos(listaDeEstadoNoIguales)
         return crearDFA(listaDeEstadoIguales,listaDeEstadoNoIguales)
    }

    fun quitarRepetidos(listaDeEstadoNoIguales: MutableList<MutableList<Estado>>) {
        var x=0
        while ( x <listaDeEstadoNoIguales.size){
            var y = x+1
            while  (y <listaDeEstadoNoIguales.size){
                if (listaDeEstadoNoIguales[x][0].NombreEstado.equals(listaDeEstadoNoIguales[y][0].NombreEstado)&&
                        listaDeEstadoNoIguales[x][1].NombreEstado.equals(listaDeEstadoNoIguales[y][1].NombreEstado)){
                    listaDeEstadoNoIguales.removeAt(y)
                }
                y++
            }
            x++
        }

        return
    }

    fun crearDFA(listaDeEstadoIguales: MutableList<MutableList<Estado>>, listaDeEstadoNoIguales: MutableList<MutableList<Estado>>):DFA {
        var dfa :DFA = DFA(alfabeto, mutableListOf(),Estado("",false), mutableListOf())
        crearEstados(dfa, listaDeEstadoIguales)
        for(listaDeEstados in listaDeEstadoIguales){
            for (alfabeto in alfabeto) {
            }
        }
         return dfa
    }

    private fun crearEstados(dfa: DFA, listaDeEstadoIguales: MutableList<MutableList<Estado>>) {
        for (estados in estados) {
            var encontrado = false
            for (estadosIguales in listaDeEstadoIguales) {
                if (estadosIguales[0].NombreEstado.equals(estados.NombreEstado) ||
                        estadosIguales[1].NombreEstado.equals(estados.NombreEstado)) {
                    encontrado = true
                    var Estado1 = obtenerEstado(estadosIguales[0].NombreEstado)
                    var Estado2 = obtenerEstado(estadosIguales[1].NombreEstado)
                    var estado = false
                    if (Estado1.EsAcceptable) {
                        estado = true
                    }
                    if (Estado2.EsAcceptable) {
                        estado = true
                    }
                    var nombre = estadosIguales[0].NombreEstado + "," + estadosIguales[1].NombreEstado
                    dfa.insertarEstado(Estado(nombre, estado))
                }
            }
            if (encontrado == false) {
                dfa.insertarEstado(estados)
            }
        }
    }

    fun  ordenarEstado(estado: Estado, estado1: Estado): MutableList<Estado> {
        var lista : MutableList<Estado> = mutableListOf()
        for (estados in estados){
            if (estados.NombreEstado.equals(estado.NombreEstado)) {
                lista.add(estados)
            }
            if (estados.NombreEstado.equals(estado1.NombreEstado)) {
                lista.add(estados)
            }
        }
        if ( lista.size == 1){
            lista.add(Estado("",false))
        }
        return lista
    }

    fun verificarEstadosAceptados( listaDeEstadoIguales: MutableList<MutableList<Estado>>, listaDeEstadoNoIguales: MutableList<MutableList<Estado>>) {
        var EstadoFinales :MutableList<Estado> = mutableListOf()
        var cantidadDeAceptados1 = 0
        for (estado in estados) {
            if (estado.EsAcceptable) {
                cantidadDeAceptados1 += 1
                EstadoFinales.add(estado)
            }
        }
        for(estado in estados) {
            if(!estado.NombreEstado.equals(EstadoFinales[0].NombreEstado)) {
                listaDeEstadoNoIguales.add(ordenarEstado(estado, EstadoFinales[0]))
            }
        }


    }


    fun verificarEstados(colummnaEstado: Estado, filaEstado: Estado, listaDeEstadoIguales: MutableList<MutableList<Estado>>, listaDeEstadoNoIguales: MutableList<MutableList<Estado>>):Boolean {
        var estadoFila: MutableList<Estado> = mutableListOf()
        var estadoColummna: MutableList<Estado> = mutableListOf()
        for(alfabeto in alfabeto) {
            for (transiciones in transiciones) {
                if (transiciones.EstadoInicial.NombreEstado.equals(colummnaEstado.NombreEstado) &&
                        transiciones.Simbolo.equals(alfabeto)) {
                    estadoColummna.add(transiciones.EstadoFinal)
                }
                if (transiciones.EstadoInicial.NombreEstado.equals(filaEstado.NombreEstado) &&
                        transiciones.Simbolo.equals(alfabeto)) {
                    estadoFila.add(transiciones.EstadoFinal)
                }
            }

            if(estadoFila.size<estadoColummna.size){
                estadoFila.add(Estado("",false))
            }else if (estadoFila.size>estadoColummna.size) {
                estadoColummna.add(Estado("",false))
            }
        }
        var listaVerifiacar = obtenerLista(estadoFila,estadoColummna)
        var x=0
        var esIgual:MutableList<Boolean> = mutableListOf()
        while (x< alfabeto.size){
            for(listaDeEstadoNoIguales1 in listaDeEstadoNoIguales){
                if(listaVerifiacar[x][0].NombreEstado.equals(listaDeEstadoNoIguales1[0].NombreEstado)&&
                        listaVerifiacar[x][1].NombreEstado.equals(listaDeEstadoNoIguales1[1].NombreEstado)){
                    listaDeEstadoNoIguales.add(ordenarEstado(filaEstado, colummnaEstado))
                    return false
                }
            }
            if (listaVerifiacar[x][0].NombreEstado.equals("")&&!listaVerifiacar[x][1].NombreEstado.equals("")){
                listaDeEstadoNoIguales.add(ordenarEstado(filaEstado, colummnaEstado))
                return false
            }
            else if(!listaVerifiacar[x][0].NombreEstado.equals("")&&listaVerifiacar[x][1].NombreEstado.equals("")){
                listaDeEstadoNoIguales.add(ordenarEstado(filaEstado, colummnaEstado))
                return false
            }
            x++
        }
        x=0
        while (x<alfabeto.size) {
            if(listaVerifiacar[x][0].NombreEstado.equals(listaVerifiacar[x][1].NombreEstado)){
                esIgual.add(true)
            }else if(!listaVerifiacar[x][0].NombreEstado.equals(listaVerifiacar[x][1].NombreEstado)){
                var entro = false
                for(listaDeEstadoIgual in listaDeEstadoIguales){
                    if(listaVerifiacar[x][0].NombreEstado.equals(listaDeEstadoIgual[0].NombreEstado)&&
                            listaVerifiacar[x][1].NombreEstado.equals(listaDeEstadoIgual[1].NombreEstado)){
                        entro = true
                        esIgual.add(true)
                    }
                }
                if (entro == false) {
                    esIgual.add(verificarEstados(estadoFila[x], estadoColummna[x], listaDeEstadoIguales, listaDeEstadoNoIguales))
                }
            }
            x++
        }
        var contado =0
        for(igualdad in esIgual){
            if (igualdad == false){
                listaDeEstadoNoIguales.add(ordenarEstado(filaEstado, colummnaEstado))
                return false
            }
            contado++
        }
        if (contado == alfabeto.size){
            listaDeEstadoIguales.add(ordenarEstado(filaEstado, colummnaEstado))
            return true
        }

            return false
    }

     fun  obtenerLista(estadoFila: MutableList<Estado>, colummnaEstado: MutableList<Estado>): MutableList<MutableList<Estado>> {
        var lista : MutableList<MutableList<Estado>> = mutableListOf()
        var x = 0
        while (x < alfabeto.size){
            lista.add(ordenarEstado(estadoFila[x],colummnaEstado[x]))
        x++
        }
        return lista
    }
}