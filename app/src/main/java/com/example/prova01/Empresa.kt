package com.example.prova01

open class Empresa constructor (nome : String, cnpj: String, caixa: Float){
    val nome: String
    val cnpj: String
    val caixa: Float


    init{
        this.nome = nome
        this.cnpj = cnpj
        this.caixa = caixa
    }

    override fun toString(): String {
        return "Nome: " +this.nome+" - Cnpj: " +this.cnpj+" - Caixa: " +this.caixa
    }
}

