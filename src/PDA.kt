
package automatas;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;
import com.sun.org.apache.bcel.internal.generic.RETURN
import Estado
import Transicion
import Automatas
public class PDA (alfabeto : MutableList<String>, estados : MutableList<Estado>, estadoInicial : Estado, transiciones  : MutableList<Transicion> ): Automatas(alfabeto,estados,estadoInicial,transiciones),java.io.Serializable
{
    var simboloInicial:String = ""
    var simboloActualDePila:String = ""
    var stack:Stack<String> = Stack()
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
    override  fun agregarAlfabeto(cadena:String) {
    if (alfabeto.isEmpty()) {
        this.alfabeto.add("ε")
        var cadena = cadena.split(",").dropLastWhile { it.isEmpty() }.toTypedArray()
        for (caracteres in cadena) {
                this.alfabeto.add(caracteres.toString())

        }

    }
}

    fun EvaluarCadena(cadena:String):Boolean {
        if(!cadena.isEmpty()){
            if(!verificarCadena(cadena))
                return false;
        }
        var finales :MutableList<Estado> = mutableListOf()
        finales.add(estadoInicial);
        finales = terminarDeEvaluarCadena(finales,cadena.toCharArray(),0);
        if(finales.isEmpty()){
            return false;
        }
        for( estadoFinal in finales){
        if(estadoFinal.EsAcceptable) {
            return true;
        }
    }
        return false;
    }

  override fun  verificarCadena( cadena:String):Boolean {
      var evaluar = cadena.toCharArray()

      var stay=true;
        var i=0
        while (i<evaluar.size){
        for(c in alfabeto){
        if(c[0].equals(evaluar[i])){
            stay=true;
            break;
        }else{
            stay= false;
        }
        }
            i++
    }
        return stay;
    }

    fun  terminarDeEvaluarCadena(finales:MutableList<Estado>,evaluar:CharArray,pos : Int):MutableList<Estado> {
        var nuevoFinales:MutableList<Estado> = mutableListOf()
        var finales = ClausuraPDA(finales);
        if(pos == evaluar.size)
            return finales;
        for(transicion in transiciones){
        for( estado  in finales){
            var t = transicion.Simbolo.split(",".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()

            if(t[0].equals(evaluar[pos].toString()) && t[1].equals(simboloActualDePila)
                && transicion.EstadoInicial.NombreEstado.equals(estado.NombreEstado)){
            stack.pop();
            if(t[2].contains(simboloInicial)){
                var sub = t[2].replace(simboloInicial, "");
                stack.push(simboloInicial);
                if(!sub.isEmpty()) {
                    var j = sub.length - 1
                    while (j >= 0) {
                        stack.push(sub.get(j).toString());
                        j--
                    }
                }
                nuevoFinales.add(transicion.EstadoFinal);
            }else{
                if(!t[2].equals("ε")){
                    var j = t[2].length-1
                    while(  j >= 0  ){
                        stack.push(t[2].get(j).toString());
                        j--
                    }
                }
                nuevoFinales.add(transicion.EstadoFinal);
            }
        }
    }
        simboloActualDePila = stack.pop();
        stack.push(simboloActualDePila);
    }
        return terminarDeEvaluarCadena(nuevoFinales, evaluar, pos+1);
    }

    fun  ClausuraPDA(finales:MutableList<Estado>):MutableList<Estado> {
        var nuevoFinales:MutableList<Estado> = mutableListOf()
        var temporal:MutableList<Estado> = mutableListOf()
        for( transicion in transiciones){
        for( estado in finales){
            var t = transicion.Simbolo.split(",").dropLastWhile { it.isEmpty() }.toTypedArray()
            if(t[0].equals("ε") && t[1].equals(simboloActualDePila)
                && transicion.EstadoInicial.NombreEstado.equals(estado.NombreEstado)){
            stack.pop()
            if(t[2].contains(simboloInicial)){
                var sub:String = t[2].replace(simboloInicial, "");
                stack.push(simboloInicial);
                if(!sub.isEmpty()){
                    var j = sub.length-1
                    while(  j >= 0  ){
                        stack.push(sub.get(j).toString());
                        j--
                    }
                    simboloActualDePila=sub.get(0).toString()
                }else{
                    simboloActualDePila=stack.pop();
                    stack.push(simboloActualDePila);
                }
                nuevoFinales.add(transicion.EstadoFinal);
            }else{
                if(!t[2].equals("ε")){
                    var j = t[2].length-1
                    while(  j >= 0  ){
                        stack.push (t[2].get(j).toString())
                        j--
                    }
                    simboloActualDePila= t[2].get(0).toString()
                }else{
                    simboloActualDePila=stack.pop();
                    stack.push(simboloActualDePila);
                }
                nuevoFinales.add(transicion.EstadoFinal);
            }
        }
    }
    }
        if(!nuevoFinales.isEmpty())
            temporal = ClausuraPDA(nuevoFinales);
        if(!temporal.isEmpty()){
            for( estado in temporal){
                finales.add(estado);
            }
        }
        return finales;
    }

    fun  GramaticaToPDA(nombre: String): PDA {
        var Estado0 = Estado("q0",false)
        var Estado1 = Estado("q1",false)
        var Estado2 = Estado("q2",true)
        var PDA = PDA(mutableListOf("ε"), mutableListOf(Estado0,Estado1,Estado2), Estado0, mutableListOf())
        PDA.simboloInicial = "Z0"
        PDA.simboloActualDePila = "Z0"
        PDA.stack.add(simboloActualDePila)
        PDA.transiciones.add(Transicion(Estado1,Estado2,"ε,Z0,ε"))
        var proyecciones = nombre.split(",").dropLastWhile { it.isEmpty() }.toTypedArray()
        var ListaNoTerminales :MutableList<String> = mutableListOf()
        var ListaTerminales :MutableList<String> = mutableListOf()

        for(proyec in proyecciones){
           var noterminal = proyec.split("->").dropLastWhile { it.isEmpty() }.toTypedArray()
            ListaNoTerminales.add(noterminal[0])
        }
        PDA.transiciones.add(Transicion(Estado0,Estado1,"ε,Z0,"+ListaNoTerminales[0]+"Z0"))
        for(proyec in proyecciones){
            var proyeccion = proyec.split("->").dropLastWhile { it.isEmpty() }.toTypedArray()
            var produce1 = proyeccion[1].split("|").dropLastWhile { it.isEmpty() }.toTypedArray()
            for(produccion in produce1){
                PDA.insertarTransacion(Transicion(Estado1,Estado1,"ε,"+proyeccion[0]+","+produccion))
                var Entro = false
                for(lista in ListaNoTerminales){
                    if(produccion.equals(lista)){
                        Entro = true
                    }
                }
                if (Entro ==false){
                var caracteres = produccion.toCharArray()
                    for (caracter in caracteres){
                        var EsTerminal = true
                        for (lista in ListaNoTerminales){
                            if(caracter.toString().equals(lista)){
                                EsTerminal = false
                            }
                        }
                        if(EsTerminal){
                            ListaTerminales.add(caracter.toString())
                        }
                    }

                }
            }

        }
       for(lista in ListaTerminales){
           PDA.insertarTransacion(Transicion(Estado1,Estado1,lista+","+lista+",ε"))
           PDA.alfabeto.add(lista)
       }

        return PDA
    }
}

