package com.example.prova01


class Cinemas constructor (quantidade_assentos: Int, nome: String, cnpj: String, caixa:Float) :Empresa(nome, cnpj, caixa) {


    var quantidade_assentos: Int = 0

        init {
            this.quantidade_assentos = quantidade_assentos
        }

    override fun toString(): String {
        return super.toString() + " - Quantidade de assentos: " + this.quantidade_assentos
    }
}