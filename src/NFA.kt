/**
 * Created by PC on 24/07/2016.
 */
open class NFA( alfabeto : MutableList<String>,estados : MutableList<Estado> ,estadoInicial : Estado ,transiciones  : MutableList<Transicion> ): Automatas(alfabeto,estados,estadoInicial,transiciones) {
     fun validadTransicion(estadoInicial: Estado,estadoFinal:Estado,simbolo:String):Boolean   {
        val transicionAinsertar = Transicion(estadoInicial, estadoFinal, simbolo, v3)
        for (transiciones in transiciones){
            if(transiciones.EstadoInicial.equals(transicionAinsertar.EstadoInicial)&&
                    transiciones.Simbolo.equals(transicionAinsertar.Simbolo)&&
                    transiciones.EstadoFinal.equals(transicionAinsertar.EstadoFinal)) {
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
   fun evaluarCadena(cadena:String) :Boolean {
        if (cadena.isEmpty()) {
            return estadoInicial.EsAcceptable;
        }
        if (!verificarCadena(cadena)){
            return false
        }
        var evaluar = cadena.toCharArray()
        var estadosFinales :MutableList<Estado> = mutableListOf()
        estadosFinales.add(estadoInicial)
        estadosFinales  = evaluarCadenaFuncionEstendida(estadosFinales ,evaluar,0);
        if(estadosFinales.isEmpty()){
            return false
        }
        for(estado in estadosFinales){
            if(estado.EsAcceptable)
                return true;

        }
        return false;
    }
   fun evaluarCadenaFuncionEstendida(ListaDeEstadosfinales:MutableList<Estado>, evaluar:CharArray, posicion:Int):MutableList<Estado> {
        var finalesTemporal:MutableList<Estado> = mutableListOf()
        if(posicion == evaluar.size)
            return ListaDeEstadosfinales
        for(transicion in transiciones){
            for(estado in ListaDeEstadosfinales)
            if(transicion.EstadoInicial.NombreEstado.equals(estado.NombreEstado)&&
                    transicion.Simbolo.equals(evaluar.get(posicion))){

                finalesTemporal.add(transicion.EstadoFinal)
                break;
            }
        }
        if(finalesTemporal.isEmpty()){
            return finalesTemporal
        }
        return evaluarCadenaFuncionEstendida(finalesTemporal, evaluar, posicion +1);
    }
    fun enDfa():DFA{
        var DFA:DFA=DFA(mutableListOf(),mutableListOf(),Estado("",false),mutableListOf())
        DFA.alfabeto = alfabeto
        DFA.insertarEstado(this.estadoInicial)
        DFA.estadoInicial = this.estadoInicial

        var x = 0
        while(x<DFA.estados.size){
            crearDFA(DFA,DFA.estados.get(x))
            x++
        }

        return DFA
    }

    fun crearDFA(DFA: DFA, estadosDFA: Estado) {
        var listaDeEstados: MutableList<String> = obtenerListaDeEstados(estadosDFA.NombreEstado)
        for (letraDeAlfabeto in DFA.alfabeto) {
            var estadoNuevo: String = crearTransiciones(listaDeEstados, letraDeAlfabeto.toString())
            if (!estadoNuevo.isEmpty()) {
                if (DFA.obtenerEstado(estadoNuevo).NombreEstado.equals("NULL")) {
                    DFA.insertarEstado(Estado(estadoNuevo, esEstadoDeAceptacion(estadoNuevo)))
                }
                DFA.insertarTransacion(Transicion(estadosDFA, DFA.obtenerEstado(estadoNuevo), letraDeAlfabeto, v3))
            }
        }
    }

    fun crearTransiciones(listaDeEstados: MutableList<String>, letraDeAlfabeto: String): String {
        var estado = ""
        var estadoDeTransicion : MutableList<String> = mutableListOf()
         for (estado in listaDeEstados) {
             for (transicion in transiciones) {
                if(transicion.EstadoInicial.NombreEstado.equals(estado)&&transicion.Simbolo.equals(letraDeAlfabeto)){
                    estadoDeTransicion.add(transicion.EstadoFinal.NombreEstado)
                }
             }
         }

        estadoDeTransicion = quitarRepetidos(estadoDeTransicion)
        estadoDeTransicion = ordenarEstado(estadoDeTransicion)
        for (estadoFinal in estadoDeTransicion){
             if(estadoFinal.equals(estadoDeTransicion.lastIndex)){
                 estado += (estadoFinal)
             }else{

                 estado += (estadoFinal+",")
             }
        }

        return estado
    }

    fun ordenarEstado(estadoDeTransicion: MutableList<String>): MutableList<String> {
        var ListaOrdenada : MutableList<String> = mutableListOf()
        for(estadoOrden in this.estados){
            for(estado in estadoDeTransicion){
               if(estadoOrden.NombreEstado.equals(estado)){
                   ListaOrdenada.add(estado)
               }

            }
        }
        return ListaOrdenada
    }

    fun quitarRepetidos(estadoDeTransicion: MutableList<String>): MutableList<String> {
        var list : MutableList<String> = mutableListOf()
        for( x in 0.. (estadoDeTransicion.size-1)){
            for (y in (x+1)..(estadoDeTransicion.size-1)){
                if (estadoDeTransicion.get(x)==estadoDeTransicion.get(y)){
                    list.add(estadoDeTransicion.get(y))
                }
            }
        }
        for (x in list){
            estadoDeTransicion.remove(x)
        }
        return  estadoDeTransicion
    }

    fun esEstadoDeAceptacion(estadoNuevo: String): Boolean {
        for(estado in obtenerListaDeEstados(estadoNuevo)){
            for(estadoNfa in this.estados){
                if (estado.equals(estadoNfa.NombreEstado)&&estadoNfa.EsAcceptable==true){
                    return true
                }

            }
        }
        return false
    }

    fun obtenerListaDeEstados(nombreEstado: String): MutableList<String> {
        var ListaEstados :MutableList<String> = mutableListOf();
        var nombreDeSubEstado = ""
        for(letra in nombreEstado){
            if (letra != ','){
                nombreDeSubEstado +=letra
            }
            else{
                ListaEstados.add(nombreDeSubEstado)
                nombreDeSubEstado = ""
            }
        }
        if(nombreDeSubEstado !="") {
            ListaEstados.add(nombreDeSubEstado)
        }
        return ListaEstados
    }


}


