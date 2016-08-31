/**
 * Created by decoe on 30/8/2016.
 */
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package automatas;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;
import com.sun.org.apache.bcel.internal.generic.RETURN
import Estado
import Transicion
import Automatas
public class PDA (alfabeto : MutableList<String>, estados : MutableList<Estado>, estadoInicial : Estado, transiciones  : MutableList<Transicion> ): Automatas(alfabeto,estados,estadoInicial,transiciones)
{
    var simboloInicial:String = ""
    var simboloActualDePila:String = ""
    var stack:Stack<String> = Stack()
    fun EvaluarCadena(cadena:String):Boolean {
        var evaluar = cadena.toCharArray();
        if(!cadena.isEmpty()){
            if(!verificarCadena(evaluar))
                return false;
        }
        var finales :MutableList<Estado> = mutableListOf()
        finales.add(estadoInicial);
        finales = terminarDeEvaluarCadena(finales,evaluar,0);
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

    fun  verificarCadena( evaluar:CharArray):Boolean {
        var stay=true;
        var i=0
        while (i<evaluar.size){
        for(c in alfabeto){
        if(c.equals(evaluar[i])){
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
        var t :MutableList<String> = mutableListOf()
            var nombreDeSubEstado = ""
            for(letra in transicion.Simbolo.toCharArray()){
                if (letra != ','){
                    nombreDeSubEstado +=letra
                }
                else{
                    t.add(nombreDeSubEstado)
                    nombreDeSubEstado = ""
                }
            }
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
        var t :MutableList<String> = mutableListOf()
            var nombreDeSubEstado = ""
            for(letra in transicion.Simbolo.toCharArray()){
                if (letra != ','){
                    nombreDeSubEstado +=letra
                }
                else{
                    t.add(nombreDeSubEstado)
                    nombreDeSubEstado = ""
                }
            }
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
}

