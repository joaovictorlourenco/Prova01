package com.example.prova01

class Supermercado constructor( ar_condicionado: Boolean,nome: String, cnpj: String, caixa: Float) : Empresa(nome, cnpj, caixa) {
    var ar_condicionado: Boolean

       init{
           this.ar_condicionado = ar_condicionado
       }

    fun Tipo(ar_condicionado : Boolean) : String{
        if (ar_condicionado){
            return "Sim"
        }else{
            return "Não"
        }
    }

    override fun toString(): String {
        return super.toString() + " - Ar condicionado: " + Tipo(this.ar_condicionado)
    }

}