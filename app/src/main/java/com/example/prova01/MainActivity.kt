package com.example.prova01

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.RadioGroup
import android.widget.Toast

class MainActivity : AppCompatActivity() {
    lateinit var categoryBussiness : RadioGroup
    lateinit var caixaFloat : EditText
    lateinit var cnpjText : EditText
    lateinit var nomeText : EditText

    lateinit var capacityTank : EditText
    lateinit var capacitySeats : EditText

    lateinit var isAirConditioning : CheckBox

    lateinit var insertButton : Button
    lateinit var secondScrenn: Button


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        categoryBussiness = findViewById(R.id.categoryBussiness)
        caixaFloat = findViewById(R.id.CaixaFloat)
        cnpjText = findViewById(R.id.CnpjText)
        nomeText = findViewById(R.id.NomeText)
        capacityTank = findViewById(R.id.capacityTank)
        capacitySeats = findViewById(R.id.qtd_assentos)
        isAirConditioning = findViewById(R.id.isCondicionado)
        insertButton = findViewById(R.id.insertButton)
        secondScrenn = findViewById(R.id.secondScreen)


        var tipo = 0;
        var listBussiness = ArrayList<String>()

        secondScrenn.setOnClickListener {
            val intent = Intent(this, second_screnn::class.java)
            intent.putExtra("listBussiness", listBussiness)
            startActivity(intent)
        }

        categoryBussiness.setOnCheckedChangeListener(
            RadioGroup.OnCheckedChangeListener { _, checkedId ->
                if (checkedId == R.id.supermercadoButton) {
                    capacityTank.isEnabled = false
                    capacitySeats.isEnabled = false
                    isAirConditioning.isEnabled = true
                    tipo = 1
                } else if (checkedId == R.id.postoButton) {
                    capacityTank.isEnabled = true
                    capacitySeats.isEnabled = false
                    isAirConditioning.isEnabled = false
                    tipo = 2
                } else if (checkedId == R.id.cinemaButton) {
                    capacityTank.isEnabled = false
                    capacitySeats.isEnabled = true
                    isAirConditioning.isEnabled = false
                    tipo = 3
                }
            }
        )

        insertButton.setOnClickListener {

            when(tipo){
                1 -> {
                   listBussiness.add(inserir(1, nomeText.text.toString(), cnpjText.text.toString(), caixaFloat.text.toString().toFloat(), isAirConditioning.isChecked, 0.0f, 0))
                }
                2-> {
                    listBussiness.add(inserir(2, nomeText.text.toString(), cnpjText.text.toString(), caixaFloat.text.toString().toFloat(), false, capacityTank.text.toString().toFloat(), 0))
                }
                3->{
                    listBussiness.add(inserir(3, nomeText.text.toString(), cnpjText.text.toString(), caixaFloat.text.toString().toFloat(), false, 0.0f, capacitySeats.text.toString().toInt()))
                }
            }

            val toast = Toast.makeText(applicationContext, "Cadastrado Com Sucesso", Toast.LENGTH_SHORT)
            toast.show()

        }





    }

    fun inserir(tipo: Number, nome: String, cnpj: String, caixa: Float , ar_condicionad: Boolean, tanque_gasolina: Float, quantidade_assentos: Int):String{

        when(tipo){
            1->{
                var supermercado = Supermercado(ar_condicionad, nome, cnpj, caixa)
                return supermercado.toString()
            }
            2->{
                var posto = PostosGasolina(tanque_gasolina, nome, cnpj, caixa)
                return posto.toString()
            }
            3->{
                var cinema = Cinemas(quantidade_assentos, nome, cnpj, caixa)
                return cinema.toString()
            }else->{
                return "Erro"
            }
        }
    }

}