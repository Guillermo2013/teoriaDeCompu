import regularexpresion.tree.ANDNode
import regularexpresion.tree.CharNode
import regularexpresion.tree.ORNode
import regularexpresion.tree.RepeatNode

/**
 * Created by PC on 25/07/2016.
 */
class NFAE( alfabeto : MutableList<String>,estados : MutableList<Estado> ,estadoInicial : Estado ,transiciones  : MutableList<Transicion> ): Automatas(alfabeto,estados,estadoInicial,transiciones),java.io.Serializable {
    var estadoNum = 0
    override fun verificarCadena(cadena:String):Boolean{
        var quedarse =true
        for(letras in cadena.toCharArray()){
            for(caracteres in alfabeto){
                if(caracteres.equals(letras.toString())){
                    quedarse=true
                    break;
                }else{
                    quedarse= false
                }
            }
        }
      if (cadena.isEmpty()){
          quedarse=true
      }
        return quedarse
    }

    fun validadTransicion(estadoInicial: Estado,estadoFinal:Estado,simbolo:String):Boolean   {
        val transicionAinsertar = Transicion(estadoInicial,estadoFinal,simbolo)
        var insertar = false
        for (transiciones in transiciones){
            if(transiciones.EstadoInicial.equals(transicionAinsertar.EstadoInicial)&&
                    transiciones.Simbolo.equals(transicionAinsertar.Simbolo)&&
                    transiciones.EstadoFinal.equals(transicionAinsertar.EstadoFinal)){
                return true
            }
        }
        return true
    }
    fun evaluarCadena(cadena:String) :Boolean {
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
            print(estado.NombreEstado)

        }
        for(estado in estadosFinales){
              if(estado.EsAcceptable)
                return true;

        }
        return false;
    }
  fun evaluarCadenaFuncionEstendida(ListaDeEstadosfinales:MutableList<Estado>, evaluar:CharArray, posicion:Int):MutableList<Estado> {
        var finalesTemporal:MutableList<Estado> = mutableListOf()
        finalesTemporal = clausura(finalesTemporal)
        if(posicion == evaluar.size)
            return ListaDeEstadosfinales
        for(transicion in transiciones){
            for(estado in ListaDeEstadosfinales)
                if(transicion.EstadoInicial.NombreEstado.equals(estado.NombreEstado)&&
                        transicion.Simbolo.equals(evaluar.get(posicion).toString())){

                    finalesTemporal.add(transicion.EstadoFinal)
                    break;
                }
        }
        if(finalesTemporal.isEmpty()){
            return finalesTemporal
        }
        return evaluarCadenaFuncionEstendida(finalesTemporal, evaluar, posicion +1);
    }
    fun clausura(finales:MutableList<Estado>):MutableList<Estado> {
        var nuevoFinales : MutableList<Estado> =mutableListOf()
        var temporal:MutableList<Estado> = mutableListOf()
        for(transicion in transiciones){
            for(estado in finales)
            if(transicion.EstadoInicial.NombreEstado.equals(estado.NombreEstado)&&
                    transicion.Simbolo.equals("ε")){

                nuevoFinales.add(transicion.EstadoFinal)
                break;
            }
        }

        if(!nuevoFinales.isEmpty()) {
            temporal = clausura(nuevoFinales)
        }
        if(!temporal.isEmpty()){
            for( estado in temporal){
                finales.add(estado);
            }
        }
        return finales;
    }
    fun insertarTransacion(Transiciones:Transicion):Boolean{
        if (validadTransicion(Transiciones.EstadoInicial,Transiciones.EstadoFinal,Transiciones.Simbolo)){
            transiciones.add(Transiciones)
            return true
        }
        return false
    }

   override  fun agregarAlfabeto(cadena:String) {
       if (alfabeto.isEmpty()) {
           this.alfabeto.add("ε")
           var cadena = cadena.replace(',', ' ')
           for (caracteres in cadena.toCharArray()) {
               if (caracteres != ' ') {
                   this.alfabeto.add(caracteres.toString())
               }
           }

       }
   }
    fun enDfa():DFA{
        var DFA:DFA=DFA(mutableListOf(),mutableListOf(),Estado("",false),mutableListOf())
        DFA.alfabeto = quitarEpsilon(alfabeto)
        var listaDeEstadoInicial = clausura(mutableListOf(estadoInicial))
	    var estado = ""
        var listaDeInicial :MutableList<String> = mutableListOf()
        for(lista in listaDeEstadoInicial){
            listaDeInicial.add(lista.NombreEstado)
        }
        listaDeInicial = quitarRepetidos(listaDeInicial)
        listaDeInicial = ordenarEstado(listaDeInicial )
        for (estadoFinal in listaDeInicial ){
             if(estadoFinal.equals(listaDeInicial.get(listaDeInicial.lastIndex))){
                 estado += (estadoFinal)
             }else{

                 estado += (estadoFinal+",")
             }
        }
	    var Estado: Estado = Estado(estado,esEstadoDeAceptacion(estado))
	    DFA.insertarEstado(Estado)
        DFA.estadoInicial = Estado
	
        var x = 0
        while(x<DFA.estados.size){
            crearDFA(DFA,DFA.estados.get(x))
            x++
        }

        return DFA
    }

    private fun quitarEpsilon(alfabeto: MutableList<String>): MutableList<String> {
        var x = 0
        while(x < alfabeto.size){
            if(alfabeto.get(x).equals("ε")){
                alfabeto.removeAt(x)
            }
            x++
        }
        return alfabeto
    }

    fun crearDFA(DFA: DFA, estadosDFA: Estado) {
        var listaDeEstados: MutableList<String> = obtenerListaDeEstados(estadosDFA.NombreEstado)
        for (letraDeAlfabeto in DFA.alfabeto) {
            var estadoNuevo: String = crearTransiciones(listaDeEstados, letraDeAlfabeto)
            if (!estadoNuevo.isEmpty()) {
                var listaEstado : MutableList<Estado> = mutableListOf()
                for (estadosAntiguo in obtenerListaDeEstados(estadoNuevo)){
                    listaEstado.add(obtenerEstado(estadosAntiguo))
                }
                var EstadoConClausura = clausura(listaEstado)
                var listaDeInicial :MutableList<String> = mutableListOf()
                for(lista in EstadoConClausura){
                    listaDeInicial.add(lista.NombreEstado)
                }
                var estadoFinal2 = ""
                listaDeInicial = quitarRepetidos(listaDeInicial)
                listaDeInicial = ordenarEstado(listaDeInicial )
                for (estadoFinal in listaDeInicial ){
                    if(estadoFinal.equals(listaDeInicial.get(listaDeInicial.lastIndex))){
                        estadoFinal2 += (estadoFinal)
                    }else{

                        estadoFinal2 += (estadoFinal+",")
                    }
                }
                if (DFA.obtenerEstado(estadoFinal2).NombreEstado.equals("NULL")) {
                    DFA.insertarEstado(Estado(estadoFinal2, esEstadoDeAceptacion(estadoFinal2)))
                }
                DFA.insertarTransacion(Transicion(estadosDFA, DFA.obtenerEstado(estadoFinal2), letraDeAlfabeto))
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
    fun concatenacionAutomatas(NFAE1 : NFAE,NFAE2: NFAE): NFAE {
        var NFAE = NFAE(mutableListOf(), mutableListOf(),NFAE1.estadoInicial, mutableListOf())
        var x =0
        NFAE.estados.addAll(NFAE1.estados)
        NFAE.estados.addAll(NFAE2.estados)
        NFAE.transiciones.addAll(NFAE1.transiciones)
        NFAE.transiciones.addAll(NFAE2.transiciones)
        while (x<NFAE1.estados.size){
            if(NFAE1.estados[x].EsAcceptable){
                NFAE1.estados[x].EsAcceptable = false
                NFAE.insertarTransacion(Transicion(NFAE1.estados[x],NFAE2.estadoInicial,"ε"))
            }
            x++
        }

        return NFAE
    }
    fun crearAutomataCaracter(cantidadDeEstados :Int,caracter:String): NFAE {
        var NFAE = NFAE(mutableListOf(), mutableListOf(),Estado("",false), mutableListOf())
        var EstadoInicial = Estado(cantidadDeEstados.toString(),false)
        NFAE.insertarEstado(EstadoInicial)
        NFAE.estadoInicial =EstadoInicial
        var EstadoFinal = Estado((cantidadDeEstados+1).toString(),true)
        NFAE.insertarEstado(EstadoFinal)
        NFAE.insertarTransacion(Transicion(EstadoInicial,EstadoFinal,caracter))
        return NFAE
    }
    fun seleccionAutomatas(NFAE1 : NFAE,NFAE2: NFAE,cantidadDeEstados :Int): NFAE {
        var NFAE = NFAE(mutableListOf(), mutableListOf(),Estado("",false), mutableListOf())
        var x =0
        NFAE.estados.addAll(NFAE1.estados)
        NFAE.estados.addAll(NFAE2.estados)
        NFAE.transiciones.addAll(NFAE1.transiciones)
        NFAE.transiciones.addAll(NFAE2.transiciones)
        var EstadoInicial = Estado(cantidadDeEstados.toString(),false)
        var EstadoFinal = Estado((cantidadDeEstados+1).toString(),true)
        NFAE.estados.addAll(mutableListOf(EstadoFinal,EstadoInicial))
        NFAE.insertarTransacion(Transicion(EstadoInicial,NFAE1.estadoInicial,"ε"))
        NFAE.insertarTransacion(Transicion(EstadoInicial,NFAE2.estadoInicial,"ε"))
        NFAE.estadoInicial = EstadoInicial
        while (x<NFAE1.estados.size){
            if(NFAE1.estados[x].EsAcceptable){
                NFAE1.estados[x].EsAcceptable = false
                NFAE.insertarTransacion(Transicion(NFAE1.estados[x],EstadoFinal,"ε"))
            }
            x++
        }
        x = 0
        while (x<NFAE2.estados.size){
            if(NFAE2.estados[x].EsAcceptable){
                NFAE2.estados[x].EsAcceptable = false
                NFAE.insertarTransacion(Transicion(NFAE2.estados[x],EstadoFinal,"ε"))
            }
            x++
        }
        return NFAE
    }
    fun repeticionAutomata (NFAE1 : NFAE,cantidadDeEstados :Int): NFAE {
        var NFAE = NFAE(mutableListOf(), mutableListOf(),Estado("",false), mutableListOf())
        NFAE.estados.addAll(NFAE1.estados)
        NFAE.transiciones.addAll(NFAE1.transiciones)
        var EstadoInicial = Estado(cantidadDeEstados.toString(),false)
        var EstadoFinal = Estado((cantidadDeEstados+1).toString(),true)
        NFAE.estadoInicial = EstadoInicial
        NFAE.estados.addAll(mutableListOf(EstadoFinal,EstadoInicial))
        NFAE.insertarTransacion(Transicion(EstadoInicial,NFAE1.estadoInicial,"ε"))
        NFAE.insertarTransacion(Transicion(EstadoInicial,EstadoFinal,"ε"))
        var x = 0
        while (x<NFAE1.estados.size){
            if(NFAE1.estados[x].EsAcceptable){
                NFAE1.estados[x].EsAcceptable = false
                NFAE.insertarTransacion(Transicion(NFAE1.estados[x],EstadoFinal,"ε"))
                NFAE.insertarTransacion(Transicion(NFAE1.estados[x],NFAE1.estadoInicial,"ε"))
            }
            x++
        }
    return NFAE
    }
    fun obtainAutomata(rootNode: regularexpresion.tree.Node, nfaeList: NFAE ) {
        if (rootNode is CharNode) {
            val e1 = Estado(estadoNum.toString(), false)
            estadoNum += 1
            val e2 = Estado(estadoNum.toString(), true)
            estadoNum += 1
            val t = Transicion(e1, e2, rootNode.value)
            nfaeList.alfabeto.add(rootNode.value)
            nfaeList.alfabeto = quitarRepetidos(nfaeList.alfabeto)
            nfaeList.estadoInicial = e1
            nfaeList.estados.add(e1)
            nfaeList.estados.add(e2)
            nfaeList.transiciones.add(t)

        } else if (rootNode is ORNode) {
            var e1 = Estado(estadoNum.toString(), false)
            estadoNum += 1
            nfaeList.estados.add(e1)
            obtainAutomata(rootNode.leftNode, nfaeList)
            var t = Transicion(e1,nfaeList.estadoInicial ,"ε")
            nfaeList.transiciones.add(t)
            obtainAutomata(rootNode.rightNode, nfaeList)
            t = Transicion(e1,nfaeList.estadoInicial ,"ε")
            nfaeList.transiciones.add(t)
            nfaeList.estadoInicial = e1

            var final = Estado(estadoNum.toString(), false)
            nfaeList.estados.add(final)
            for(estado in nfaeList.estados){
                if(estado.EsAcceptable){
                    estado.EsAcceptable = false
                    t = Transicion(estado,final ,"ε")
                    nfaeList.transiciones.add(t)
                }
            }
            nfaeList.obtenerEstado(final.NombreEstado).EsAcceptable =true
            estadoNum += 1
        } else if (rootNode is ANDNode) {
            var Estado = Estado("",false)
            obtainAutomata(rootNode.rightNode, nfaeList)
            for (estado in nfaeList.estados){
                if(estado.EsAcceptable){
                    estado.EsAcceptable = false
                    Estado = estado
                }
            }
            var estadoInicial = nfaeList.estadoInicial
            obtainAutomata(rootNode.leftNode, nfaeList)
           var t = Transicion(Estado,nfaeList.estadoInicial ,"ε")
            nfaeList.transiciones.add(t)
            nfaeList.estadoInicial = obtenerEstado(estadoInicial.NombreEstado)
            estadoNum += 1
        } else {
            val e1 = Estado((estadoNum).toString(), false)
            estadoNum += 1
            nfaeList.estados.add(e1)
            obtainAutomata((rootNode as RepeatNode).node, nfaeList)
            val e2 = Estado((estadoNum).toString(), false)
            nfaeList.estados.add(e2)
            for(estado in nfaeList.estados){
                if(estado.EsAcceptable){
                    estado.EsAcceptable = false
                    var t = Transicion(estado,e2,"ε")
                    nfaeList.transiciones.add(t)
                    t = Transicion(estado,nfaeList.estadoInicial,"ε")
                    nfaeList.transiciones.add(t)
                }
            }
            nfaeList.obtenerEstado(e2.NombreEstado).EsAcceptable =true
            var t = Transicion(e1,nfaeList.estadoInicial,"ε")
            nfaeList.transiciones.add(t)
             t = Transicion(e1,e2,"ε")
            nfaeList.transiciones.add(t)
            nfaeList.estadoInicial = e1
            estadoNum += 1
        }

    }

}