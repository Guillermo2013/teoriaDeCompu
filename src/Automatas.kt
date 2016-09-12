

open class Automatas( alfabeto : MutableList<String>,estados : MutableList<Estado> ,estadoInicial : Estado ,transiciones  : MutableList<Transicion> ):java.io.Serializable{
        var alfabeto: MutableList<String> = alfabeto
        var estados: MutableList<Estado> =  estados
        var estadoInicial: Estado = estadoInicial
        var transiciones: MutableList<Transicion> = transiciones


     open fun verificarCadena(cadena:String):Boolean{
        var quedarse = true
        for(letras in cadena.toCharArray()){
            for(caracteres in this.alfabeto){
                if(caracteres.equals(letras)){
                    quedarse=true
                    break;

                }else{
                    quedarse= false
                }
            }
        }
        return quedarse
    }
    fun validarEstado(nombreEstado:String,estadoAceptado:Boolean):Boolean{
        val estadoAinsertar = Estado(nombreEstado,estadoAceptado)

        for (estados in this.estados){
            if(estados.NombreEstado.equals(estadoAinsertar.NombreEstado)){
                return false
            }
        }

        return true
    }
    fun insertarEstado(estado:Estado):Boolean{
        if (validarEstado(estado.NombreEstado,estado.EsAcceptable)){
            this.estados.add(estado)
            return true
        }
        return false
    }
 open  fun agregarAlfabeto(cadena:String) {
     if (alfabeto.isEmpty()) {
         var cadena = cadena.replace(',', ' ')
         for (caracteres in cadena.toCharArray()) {
             if (caracteres != ' ') {
                 this.alfabeto.add(caracteres.toString())
             }
         }

     }
 }
    fun obtenerEstado (nombreEstado: String):Estado{
        for(estado in this.estados){
            if (estado.NombreEstado.equals(nombreEstado)){
                return estado
            }
        }
        return Estado("NULL",false)
    }
    fun verificarLetra(letra:String):Boolean{

         for(alfabeto in this.alfabeto){
            if (alfabeto.equals(letra)){
                return true

            }
        }
        return false
    }

}