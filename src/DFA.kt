import com.sun.org.apache.bcel.internal.generic.RETURN

class DFA(alfabeto : MutableList<String>, estados : MutableList<Estado>, estadoInicial : Estado, transiciones  : MutableList<Transicion> ): Automatas(alfabeto,estados,estadoInicial,transiciones) ,java.io.Serializable{

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
            if (estados.EsAcceptable&&!estados.NombreEstado.equals(estadoInicial)) {
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
                    if (caminoDirecto(transicionHacia.EstadoInicial.NombreEstado, transicionDesde.EstadoFinal.NombreEstado, transiciones).Simbolo.equals("") == true
                            &&transicionHacia.EstadoInicial.NombreEstado.equals(transicionDesde.EstadoFinal.NombreEstado) == false
                            && estados[estado].EsAcceptable == false
                            &&estados[estado].NombreEstado.equals(estadoInicial.NombreEstado)==false) {
                        var simboloDeRetorno = transicionHaciaElMismo(estados[estado].NombreEstado, transiciones)
                        if (!simboloDeRetorno.isEmpty()) {
                            transiciones.add(Transicion(transicionHacia.EstadoInicial, transicionDesde.EstadoFinal, "(" + transicionHacia.Simbolo + simboloDeRetorno[0].Simbolo + "*" + transicionDesde.Simbolo + ")"))
                        } else {
                            transiciones.add(Transicion(transicionHacia.EstadoInicial, transicionDesde.EstadoFinal, "(" + transicionHacia.Simbolo + transicionDesde.Simbolo + ")"))

                        }
                    } else if (caminoDirecto(transicionHacia.EstadoInicial.NombreEstado, transicionDesde.EstadoFinal.NombreEstado, transiciones).Simbolo.equals("") == false &&
                            caminoDirecto(transicionHacia.EstadoInicial.NombreEstado, transicionDesde.EstadoFinal.NombreEstado, transiciones).Simbolo.length <= 1
                            && transicionHacia.EstadoInicial.NombreEstado.equals(transicionHacia.EstadoFinal.NombreEstado) == false
                            && estados[estado].EsAcceptable == false
                            &&estados[estado].NombreEstado.equals(estadoInicial.NombreEstado)==false) {
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
                       if(estados[estado].EsAcceptable==false)
                        transiciones.add(Transicion(transicionHacia.EstadoInicial, transicionDesde.EstadoFinal, simbolo))
                    } else if (transicionDesde.EstadoInicial.NombreEstado.equals(transicionHacia.EstadoFinal.NombreEstado)
                            && transicionHacia.EstadoInicial.NombreEstado.equals(transicionHacia.EstadoFinal.NombreEstado) == false
                            && estados[estado].EsAcceptable == false
                    &&estados[estado].NombreEstado.equals(estadoInicial.NombreEstado)==false) {
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
                var x = 0
                while (x < transiciones.size-1) {
                    if (transiciones[x].EstadoInicial.NombreEstado.equals(estados[estado].NombreEstado)
                            || transiciones[x].EstadoFinal.NombreEstado.equals(estados[estado].NombreEstado)) {
                        transiciones.removeAt(x)
                    }
                    x++
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

        for (transiciones in transiciones){
           println(transiciones.EstadoInicial.NombreEstado+" "+transiciones.EstadoFinal.NombreEstado+" "+transiciones.Simbolo)
            if(transiciones.EstadoInicial.NombreEstado.equals(transiciones.EstadoFinal.NombreEstado)){
                if(transiciones.EstadoInicial.NombreEstado.equals(estadoInicial.NombreEstado)){
                    R = transiciones.Simbolo+"*"
                }else{
                    U = transiciones.Simbolo+"*"
                }
            }else if (transiciones.EstadoFinal.NombreEstado.equals(estadoInicial.NombreEstado)){
                T = transiciones.Simbolo
            }
        }

        S = transicionesDesdeEl(estadoInicial.NombreEstado, transiciones)[0].Simbolo

        Expresion = "(" +R + S + U + ")+(" + R + S + U + ")("+ T + R + S + U + ")*"


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

    fun Minimizar(listaDeEstadoIguales: MutableList<MutableList<Estado>>, listaDeEstadoNoIguales: MutableList<MutableList<Estado>>): DFA {
        var filaEstado = 0
        verificarEstadosAceptados(listaDeEstadoIguales, listaDeEstadoNoIguales)
        while (filaEstado < estados.size - 1) {
            var colummnaEstado = filaEstado + 1
            while (colummnaEstado < estados.size) {
                verificarEstados(estados[colummnaEstado], estados[filaEstado], listaDeEstadoIguales, listaDeEstadoNoIguales)
                colummnaEstado++
            }
            filaEstado++
        }
        quitarRepetidos(listaDeEstadoIguales)
        quitarRepetidos(listaDeEstadoNoIguales)
        return crearDFA(listaDeEstadoIguales, listaDeEstadoNoIguales)
    }

    fun quitarRepetidos(listaDeEstadoNoIguales: MutableList<MutableList<Estado>>) {
        var x = 0
        while (x < listaDeEstadoNoIguales.size) {
            var y = x + 1
            while (y < listaDeEstadoNoIguales.size) {
                if (listaDeEstadoNoIguales[x][0].NombreEstado.equals(listaDeEstadoNoIguales[y][0].NombreEstado) &&
                        listaDeEstadoNoIguales[x][1].NombreEstado.equals(listaDeEstadoNoIguales[y][1].NombreEstado)) {
                    listaDeEstadoNoIguales.removeAt(y)
                }
                y++
            }
            x++
        }

        return
    }

    fun crearDFA(listaDeEstadoIguales: MutableList<MutableList<Estado>>, listaDeEstadoNoIguales: MutableList<MutableList<Estado>>): DFA {
        var dfa: DFA = DFA(alfabeto, mutableListOf(), Estado("", false), mutableListOf())
        crearEstados(dfa, listaDeEstadoIguales)
        dfa.estadoInicial = obtenerEstadoDeLista(estadoInicial, listaDeEstadoIguales)
        for (estado in dfa.estados) {
            for (alfabeto in alfabeto) {
                var entro = false
                for (listaDeEstados in listaDeEstadoIguales) {
                    var NombreAigualar = listaDeEstados[0].NombreEstado + "," + listaDeEstados[1].NombreEstado
                    if (estado.NombreEstado.equals(NombreAigualar)) {
                        CrearTransicionEstadosIguales(NombreAigualar, alfabeto, dfa, listaDeEstadoIguales, listaDeEstados)
                        entro = true
                    }
                }
                if (entro == false) {
                    var Estado = Estado("", false)
                    for (transiciones in transiciones) {
                        if (transiciones.EstadoInicial.NombreEstado.equals(estado.NombreEstado) && transiciones.Simbolo.equals(alfabeto)) {
                            Estado = obtenerEstadoDeLista(transiciones.EstadoFinal, listaDeEstadoIguales)
                        }
                    }
                    if (!Estado.NombreEstado.equals("")) {
                        dfa.insertarTransacion(Transicion(dfa.obtenerEstado(estado.NombreEstado), dfa.obtenerEstado(Estado.NombreEstado), alfabeto))
                    }
                }
            }
        }
        return dfa
    }

    private fun CrearTransicionEstadosIguales(NombreAigualar: String, alfabeto: String, dfa: DFA, listaDeEstadoIguales: MutableList<MutableList<Estado>>, listaDeEstados: MutableList<Estado>) {
        var Destino1: Estado = Estado("", false)
        var Destino2: Estado = Estado("", false)
        for (transicion in this.transiciones) {
            if (transicion.EstadoInicial.NombreEstado.equals(listaDeEstados[0].NombreEstado) && transicion.Simbolo.equals(alfabeto)) {
                Destino1 = transicion.EstadoFinal
            }
            if (transicion.EstadoInicial.NombreEstado.equals(listaDeEstados[1].NombreEstado) && transicion.Simbolo.equals(alfabeto)) {
                Destino2 = transicion.EstadoFinal
            }
        }
        var Estado: Estado = Estado("", false)
        if (Destino1.NombreEstado.equals(Destino2.NombreEstado)) {
            Estado = obtenerEstadoDeLista(Destino1, listaDeEstadoIguales)
        }
        if (Destino1.NombreEstado.equals("")) {
            Estado = obtenerEstadoDeLista(Destino2, listaDeEstadoIguales)
        }
        if (Destino2.NombreEstado.equals("")) {
            Estado = obtenerEstadoDeLista(Destino1, listaDeEstadoIguales)
        }
        if (!Destino1.NombreEstado.equals(Destino2.NombreEstado)) {
            var listaOrdenada = ordenarEstado(Destino1, Destino2)
            Estado = dfa.obtenerEstado(listaOrdenada[0].NombreEstado + "," + listaOrdenada[1].NombreEstado)
        }
        dfa.insertarTransacion(Transicion(dfa.obtenerEstado(NombreAigualar), dfa.obtenerEstado(Estado.NombreEstado), alfabeto))
    }

    fun obtenerEstadoDeLista(destino1: Estado, listaDeEstadoIguales: MutableList<MutableList<Estado>>): Estado {
        for (lista in listaDeEstadoIguales) {
            if (lista[0].NombreEstado.equals(destino1.NombreEstado) || lista[1].NombreEstado.equals(destino1.NombreEstado)) {
                var nombre = lista[0].NombreEstado + "," + lista[1].NombreEstado
                var aceptacion = obtenerEstado(lista[0].NombreEstado).EsAcceptable || obtenerEstado(lista[1].NombreEstado).EsAcceptable
                return Estado(nombre, aceptacion)
            }
        }
        return destino1
    }

    fun crearEstados(dfa: DFA, listaDeEstadoIguales: MutableList<MutableList<Estado>>) {
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

    fun ordenarEstado(estado: Estado, estado1: Estado): MutableList<Estado> {
        var lista: MutableList<Estado> = mutableListOf()
        for (estados in estados) {
            if (estados.NombreEstado.equals(estado.NombreEstado)) {
                lista.add(estados)
            }
            if (estados.NombreEstado.equals(estado1.NombreEstado)) {
                lista.add(estados)
            }
        }
        if (lista.size == 1) {
            lista.add(Estado("", false))
        }
        return lista
    }

    fun verificarEstadosAceptados(listaDeEstadoIguales: MutableList<MutableList<Estado>>, listaDeEstadoNoIguales: MutableList<MutableList<Estado>>) {
        var EstadoFinales: MutableList<Estado> = mutableListOf()
        var cantidadDeAceptados1 = 0
        for (estado in estados) {
            if (estado.EsAcceptable) {
                cantidadDeAceptados1 += 1
                EstadoFinales.add(estado)
            }
        }
        for (estado in estados) {
            if (!estado.NombreEstado.equals(EstadoFinales[0].NombreEstado)) {
                listaDeEstadoNoIguales.add(ordenarEstado(estado, EstadoFinales[0]))
            }
        }


    }


    fun verificarEstados(colummnaEstado: Estado, filaEstado: Estado, listaDeEstadoIguales: MutableList<MutableList<Estado>>, listaDeEstadoNoIguales: MutableList<MutableList<Estado>>): Boolean {
        var estadoFila: MutableList<Estado> = mutableListOf()
        var estadoColummna: MutableList<Estado> = mutableListOf()
        for (alfabeto in alfabeto) {
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

            if (estadoFila.size < estadoColummna.size) {
                estadoFila.add(Estado("", false))
            } else if (estadoFila.size > estadoColummna.size) {
                estadoColummna.add(Estado("", false))
            }
        }
        var listaVerifiacar = obtenerLista(estadoFila, estadoColummna)
        var x = 0
        var esIgual: MutableList<Boolean> = mutableListOf()
        while (x < alfabeto.size) {
            for (listaDeEstadoNoIguales1 in listaDeEstadoNoIguales) {
                if (listaVerifiacar[x][0].NombreEstado.equals(listaDeEstadoNoIguales1[0].NombreEstado) &&
                        listaVerifiacar[x][1].NombreEstado.equals(listaDeEstadoNoIguales1[1].NombreEstado)) {
                    listaDeEstadoNoIguales.add(ordenarEstado(filaEstado, colummnaEstado))
                    return false
                }
            }
            if (listaVerifiacar[x][0].NombreEstado.equals("") && !listaVerifiacar[x][1].NombreEstado.equals("")) {
                listaDeEstadoNoIguales.add(ordenarEstado(filaEstado, colummnaEstado))
                return false
            } else if (!listaVerifiacar[x][0].NombreEstado.equals("") && listaVerifiacar[x][1].NombreEstado.equals("")) {
                listaDeEstadoNoIguales.add(ordenarEstado(filaEstado, colummnaEstado))
                return false
            }
            x++
        }
        x = 0
        while (x < alfabeto.size) {
            if (listaVerifiacar[x][0].NombreEstado.equals(listaVerifiacar[x][1].NombreEstado)) {
                esIgual.add(true)
            } else if (!listaVerifiacar[x][0].NombreEstado.equals(listaVerifiacar[x][1].NombreEstado)) {
                var entro = false
                for (listaDeEstadoIgual in listaDeEstadoIguales) {
                    if (listaVerifiacar[x][0].NombreEstado.equals(listaDeEstadoIgual[0].NombreEstado) &&
                            listaVerifiacar[x][1].NombreEstado.equals(listaDeEstadoIgual[1].NombreEstado)) {
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
        var contado = 0
        for (igualdad in esIgual) {
            if (igualdad == false) {
                listaDeEstadoNoIguales.add(ordenarEstado(filaEstado, colummnaEstado))
                return false
            }
            contado++
        }
        if (contado == alfabeto.size) {
            listaDeEstadoIguales.add(ordenarEstado(filaEstado, colummnaEstado))
            return true
        }

        return false
    }

    fun obtenerLista(estadoFila: MutableList<Estado>, colummnaEstado: MutableList<Estado>): MutableList<MutableList<Estado>> {
        var lista: MutableList<MutableList<Estado>> = mutableListOf()
        var x = 0
        while (x < alfabeto.size) {
            lista.add(ordenarEstado(estadoFila[x], colummnaEstado[x]))
            x++
        }
        return lista
    }

    fun union(Automata1: DFA, Automata2: DFA): DFA {
        var DFA = DFA(mutableListOf(), mutableListOf(), Estado("", false), mutableListOf())
        DFA = crearAutomata(Automata1, Automata2)
        for (estados in DFA.estados) {
            var Estado = estados.NombreEstado.split(",").dropLastWhile { it.isEmpty() }.toTypedArray()
            var aceptacion1 = false
            var aceptacion2 = false
            var aceptacion3 = false
            var aceptacion4 = false
            if (Estado.size == 1) {
                aceptacion1 = esAceptado(Estado[0], Automata1)
                aceptacion2 = esAceptado(Estado[0], Automata2)
            } else if (Estado.size > 1) {
                aceptacion1 = esAceptado(Estado[0], Automata1)
                aceptacion2 = esAceptado(Estado[0], Automata2)
                aceptacion3 = esAceptado(Estado[1], Automata1)
                aceptacion4 = esAceptado(Estado[1], Automata2)
            }
            if (aceptacion1 || aceptacion2 || aceptacion3 || aceptacion4) {
                estados.EsAcceptable = true
            }
        }

        return DFA
    }

    private fun esAceptado(s: String, automata1: DFA): Boolean {
        for (estado in automata1.estados) {
            if (estado.NombreEstado.equals(s)) {
                if (estado.EsAcceptable) {
                    return true
                }
            }
        }
        return false
    }

    fun crearAutomata(Automata1: DFA, Automata2: DFA): DFA {
        var DFA = DFA(mutableListOf(), mutableListOf(), Estado("", false), mutableListOf())
        DFA.alfabeto = crearAlfabeto(Automata1, Automata2)
        var Estado = Estado("", false)
        if (!Automata1.estadoInicial.NombreEstado.equals(Automata2.estadoInicial.NombreEstado)) {
            Estado.NombreEstado = Automata1.estadoInicial.NombreEstado + "," + Automata2.estadoInicial.NombreEstado
            Estado.EsAcceptable = (Automata1.estadoInicial.EsAcceptable || Automata2.estadoInicial.EsAcceptable)
        } else {
            Estado = Automata1.estadoInicial
        }
        DFA.insertarEstado(Estado)
        DFA.estadoInicial = Estado
        var x = 0
        while (x < DFA.estados.size) {
            var Estado = DFA.estados[x].NombreEstado.split(",").dropLastWhile { it.isEmpty() }.toTypedArray()
            for (alfabeto in DFA.alfabeto) {
                var EstadoAinsertar: MutableList<String> = mutableListOf()
                if (Estado.size > 1) {
                    EstadoAinsertar = obtenerTransiciones(Estado[0], Estado[1], Automata1.transiciones, Automata2.transiciones, alfabeto)
                } else if (Estado.size == 1) {
                    EstadoAinsertar = obtenerTransiciones(Estado[0], Estado[0], Automata1.transiciones, Automata2.transiciones, alfabeto)
                }

                if (EstadoAinsertar.size == 1) {
                    if (DFA.obtenerEstado(EstadoAinsertar.get(0)).NombreEstado.equals("NULL")) {
                        DFA.insertarEstado(Estado(EstadoAinsertar[0], false))
                    }
                    DFA.insertarTransacion(Transicion(DFA.estados[x], DFA.obtenerEstado(EstadoAinsertar[0]), alfabeto))
                } else if (EstadoAinsertar.size > 1) {
                    if (!DFA.obtenerEstado(EstadoAinsertar[0] + "," + EstadoAinsertar[1]).NombreEstado.equals("NULL")) {
                        DFA.insertarTransacion(Transicion(DFA.estados[x], DFA.obtenerEstado(EstadoAinsertar[0] + "," + EstadoAinsertar[1]), alfabeto))
                    } else if (!DFA.obtenerEstado(EstadoAinsertar[1] + "," + EstadoAinsertar[0]).NombreEstado.equals("NULL")) {
                        DFA.insertarTransacion(Transicion(DFA.estados[x], DFA.obtenerEstado(EstadoAinsertar[1] + "," + EstadoAinsertar[0]), alfabeto))
                    } else if (DFA.obtenerEstado(EstadoAinsertar[1] + "," + EstadoAinsertar[0]).NombreEstado.equals("NULL") &&
                            DFA.obtenerEstado(EstadoAinsertar[0] + "," + EstadoAinsertar[1]).NombreEstado.equals("NULL")) {
                        DFA.insertarEstado(Estado(EstadoAinsertar[0] + "," + EstadoAinsertar[1], false))
                        DFA.insertarTransacion(Transicion(DFA.estados[x], DFA.obtenerEstado(EstadoAinsertar[0] + "," + EstadoAinsertar[1]), alfabeto))
                    }
                }
            }
            x++
        }
        return DFA
    }

    fun crearAlfabeto(automata1: DFA, automata2: DFA): MutableList<String> {
        var alfabeto: MutableList<String> = mutableListOf()
        for (alfabeto1 in automata1.alfabeto) {
            alfabeto.add(alfabeto1)
        }
        for (alfabeto1 in automata2.alfabeto) {
            var x = 0
            var insertar = false
            while (x < alfabeto.size) {
                if (alfabeto1.equals(alfabeto[x])) {
                    insertar = true
                }
                x++
            }
            if (insertar == false) {
                alfabeto.add(alfabeto1)
            }
        }
        return alfabeto
    }

    fun obtenerTransiciones(s: String, s1: String, transiciones: MutableList<Transicion>, transiciones1: MutableList<Transicion>, alfabeto: String): MutableList<String> {
        var EstadoAinsertar: MutableList<String> = mutableListOf()
        for (transicion1 in transiciones) {
            if (transicion1.EstadoInicial.NombreEstado.equals(s) && transicion1.Simbolo.equals(alfabeto)) {
                EstadoAinsertar.add(transicion1.EstadoFinal.NombreEstado)
            }
        }
        for (transicion2 in transiciones1) {
            if (transicion2.EstadoInicial.NombreEstado.equals(s1) && transicion2.Simbolo.equals(alfabeto)) {
                EstadoAinsertar.add(transicion2.EstadoFinal.NombreEstado)
            }
        }
        if (EstadoAinsertar.size > 1) {
            if (EstadoAinsertar[0] == EstadoAinsertar[1]) {
                EstadoAinsertar.removeAt(1)
            }
        }

        return EstadoAinsertar

    }
    fun intersecion(Automata1: DFA, Automata2: DFA): DFA {
        var DFA = DFA(mutableListOf(), mutableListOf(), Estado("", false), mutableListOf())
        DFA = crearAutomata(Automata1, Automata2)
        for (estados in DFA.estados) {
            var Estado = estados.NombreEstado.split(",").dropLastWhile { it.isEmpty() }.toTypedArray()
            var aceptacion1 = false
            var aceptacion2 = false
            if (Estado.size == 1) {
                aceptacion1 = esAceptado(Estado[0], Automata1)
                aceptacion2 = esAceptado(Estado[0], Automata2)
            } else if (Estado.size > 1) {
                aceptacion1 = esAceptado(Estado[0], Automata1)
                aceptacion2 = esAceptado(Estado[1], Automata2)
            }
            if (aceptacion1 == true && aceptacion2 == true ) {
                estados.EsAcceptable = true
            }else{
                estados.EsAcceptable = false
            }
        }

        return DFA
    }
    fun resta(Automata1: DFA, Automata2: DFA): DFA {
        var x = 0
        while (x < Automata2.estados.size){
            if(Automata2.estados[x].EsAcceptable){
                Automata2.estados[x].EsAcceptable = false
            }else {
                Automata2.estados[x].EsAcceptable = true
            }
            x++
        }

        return intersecion(Automata1,Automata2)
    }
    fun complemento(Automata1: DFA): DFA {
        var x = 0
        while (x < Automata1.estados.size) {
            if (Automata1.estados[x].EsAcceptable) {
                Automata1.estados[x].EsAcceptable = false
            } else {
                Automata1.estados[x].EsAcceptable = true
            }
            x++
        }
        if(Automata1.transiciones.size < (Automata1.estados.size * Automata1.alfabeto.size)) {
            Automata1.insertarEstado(Estado("sumidero", true))
        }
        for (estado in Automata1.estados){
            for (alfabeto in Automata1.alfabeto){
                Automata1.insertarTransacion(Transicion(estado,Estado("sumidero",true),alfabeto))
            }
        }
        return Automata1
    }
}