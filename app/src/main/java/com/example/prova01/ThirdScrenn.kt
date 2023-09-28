package com.example.prova01

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.RadioGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class ThirdScrenn : AppCompatActivity() {

    lateinit var categoryBussinessEdit : RadioGroup
    lateinit var caixaFloatEdit : EditText
    lateinit var cnpjTextEdit : EditText
    lateinit var nomeTextEdit : EditText
    lateinit var capacityTankEdit : EditText
    lateinit var capacitySeatsEdit : EditText

    lateinit var isCondicionadoEdit : CheckBox
    lateinit var confirmButton : Button
    lateinit var backToscreen : Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_third_screnn)

        backToscreen = findViewById(R.id.backToscreenEdit)
        isCondicionadoEdit = findViewById(R.id.isCondicionadoEdit)
        capacitySeatsEdit = findViewById(R.id.qtd_assentosEdit)
        capacityTankEdit = findViewById(R.id.capacityTankEdit)
        nomeTextEdit = findViewById(R.id.nomeTextEdit)
        cnpjTextEdit = findViewById(R.id.cnpjTextEdit)
        caixaFloatEdit = findViewById(R.id.caixaFloatEdit)
        categoryBussinessEdit = findViewById(R.id.categoryBussinessEdit)
        confirmButton = findViewById(R.id.confirmButton)
        var tipo = 0;


        var listBussiness = intent.getStringArrayListExtra("listBussiness") as ArrayList<String>
        var bussinessEdit : String = ""

        categoryBussinessEdit.setOnCheckedChangeListener { _, checkedId ->
            when (checkedId) {
                R.id.supermercadoButton -> {
                    capacityTankEdit.isEnabled = false
                    capacitySeatsEdit.isEnabled = false
                    isCondicionadoEdit.isEnabled = true
                    tipo = 1
                }

                R.id.postoButton -> {
                    capacityTankEdit.isEnabled = true
                    capacitySeatsEdit.isEnabled = false
                    isCondicionadoEdit.isEnabled = false
                    tipo = 2
                }

                R.id.cinemaButton -> {
                    capacityTankEdit.isEnabled = false
                    capacitySeatsEdit.isEnabled = true
                    isCondicionadoEdit.isEnabled = false
                    tipo = 3
                }
            }
        }

        confirmButton.setOnClickListener(){


                //verificando se os campos estão vazios
                if(nomeTextEdit.text.toString() == "" || cnpjTextEdit.text.toString() == "" || caixaFloatEdit.text.toString() == ""){
                    val toast = Toast.makeText(applicationContext, "Preencha todos os campos", Toast.LENGTH_SHORT)
                    toast.show()
                    return@setOnClickListener
                }

                //verificar se o cnpj já existe
                listBussiness.forEach(){
                    var splitArray = it.split(" - ")
                    var cnpj = splitArray[1].split(": ")

                    if(cnpj[1] == cnpjTextEdit.text.toString()){
                        val toast = Toast.makeText(applicationContext, "Cnpj já cadastrado", Toast.LENGTH_SHORT)
                        toast.show()
                        return@setOnClickListener
                    }

                }

                when(tipo){
                    1 -> {
                        bussinessEdit = inserir(1, nomeTextEdit.text.toString(), cnpjTextEdit.text.toString(), caixaFloatEdit.text.toString().toFloat(), isCondicionadoEdit.isChecked, 0.0f, 0)
                    }
                    2-> {
                        bussinessEdit = inserir(2, nomeTextEdit.text.toString(), cnpjTextEdit.text.toString(), caixaFloatEdit.text.toString().toFloat(), false, capacityTankEdit.text.toString().toFloat(), 0)
                    }
                    3->{
                        bussinessEdit = inserir(3, nomeTextEdit.text.toString(), cnpjTextEdit.text.toString(), caixaFloatEdit.text.toString().toFloat(), false, 0.0f, capacitySeatsEdit.text.toString().toInt())
                    }
                }

                val toast = Toast.makeText(applicationContext, bussinessEdit, Toast.LENGTH_SHORT)
                toast.show()

                val intent = Intent()
                intent.putExtra("bussinessEdit", bussinessEdit)
                setResult(RESULT_OK, intent)
                finish()

        }


        backToscreen.setOnClickListener(){
            finish()
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