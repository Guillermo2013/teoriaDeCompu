/**
 * Created by Dell on 11/9/2016.
 */
 class MDT( alfabeto : MutableList<String>,estados : MutableList<Estado> ,estadoInicial : Estado ,transiciones  : MutableList<Transicion> ): Automatas(alfabeto,estados,estadoInicial,transiciones),java.io.Serializable {
    var cinta:MutableList<String> = mutableListOf()
    var posicionActualCinta = 2


    fun  EvaluarCadena(cadena:String):Boolean {
        if(!cadena.isEmpty()){
            if(verificarCadena(cadena)==false){
                return false
            }
        }
        var finales :MutableList<Estado> = mutableListOf()
        finales.add(estadoInicial);
        finales = terminarDeEvaluarCadena(finales);
        if(finales.isEmpty()){
            return false;
        }

        for( estadoFinal in finales){
            if(estadoFinal.EsAcceptable)
            return true;

        }
        return false;
    }


    fun terminarDeEvaluarCadena(finales:MutableList<Estado>):MutableList<Estado> {
        var nuevosFinales :MutableList<Estado> = mutableListOf()
        for(transicion in transiciones){
        var t =transicion.Simbolo.split(",".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
        for(estado in finales)
        if(transicion.EstadoInicial.NombreEstado.equals(estado.NombreEstado)
                && t[0].equals(cinta.get(posicionActualCinta))){
            cinta.set(posicionActualCinta, t[1]);
            if(t[2].equals("->"))
                posicionActualCinta+=1;
            else
                posicionActualCinta-=1;
            nuevosFinales.add(transicion.EstadoFinal);
            break;
        }
        if(!nuevosFinales.isEmpty())
            break;
        }

        if(nuevosFinales.isEmpty())
            return finales;

        return terminarDeEvaluarCadena(nuevosFinales);

    }
    fun insertarTransacion(Transiciones:Transicion):Boolean{
        if (validadTransicion(Transiciones.EstadoInicial,Transiciones.EstadoFinal,Transiciones.Simbolo)){
            transiciones.add(Transiciones)
            return true
        }
        return false
    }
    fun validadTransicion(estadoInicial: Estado,estadoFinal:Estado,simbolo:String):Boolean   {
        val transicionAinsertar = Transicion(estadoInicial,estadoFinal,simbolo)
        for (transiciones in transiciones){
            if(transiciones.EstadoInicial.equals(transicionAinsertar.EstadoInicial)&&
                    transiciones.Simbolo.equals(transicionAinsertar.Simbolo)&&
                    transiciones.EstadoFinal.equals(transicionAinsertar.EstadoFinal)) {
                return false
            }
        }
        return true
    }

     fun llenarCinta(cadenaAEvaluar: String) {
        if (!cinta.isEmpty()) {
            cinta = mutableListOf()
            posicionActualCinta = 2
        }
        for (i in 0..1)
            cinta.add("B")
        for (i in 0..cadenaAEvaluar.length - 1)
            cinta.add(cadenaAEvaluar[i].toString())
        for (i in 0..1)
            cinta.add("B")
    }
    override fun  verificarCadena( cadena:String):Boolean {
        var evaluar = cadena.toCharArray()
        var stay=false;
        var i=0
        while (i<evaluar.size){
            for(c in this.alfabeto){
                if(c[0].equals(evaluar[i])){
                    stay=true;
                    break;
                }

            }
            if(stay == false){
                return false
            }
            i++
        }
        return stay;
    }
}