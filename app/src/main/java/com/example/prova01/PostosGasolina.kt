package com.example.prova01

class PostosGasolina constructor(tanque_gasolina: Float, nome: String, cnpj: String, caixa: Float) : Empresa(nome, cnpj, caixa) {

    var tanque_gasolina: Float = 0.0f

    init {
        this.tanque_gasolina = tanque_gasolina
    }

    override fun toString(): String {
        return super.toString() +"  - Tanque de gasolina: " + this.tanque_gasolina
    }

}