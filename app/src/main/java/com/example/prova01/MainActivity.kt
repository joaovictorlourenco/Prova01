package com.example.prova01

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.RadioGroup
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts

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

    var listBussiness = ArrayList<String>()

    val register = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result: ActivityResult ->
        if (result.resultCode == RESULT_OK) {
            result.data?.let {
                if (it.hasExtra("updatedList")) {
                    listBussiness = it.getStringArrayListExtra("updatedList") ?: ArrayList()
                }
            }
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        categoryBussiness = findViewById(R.id.categoryBussinessEdit)
        caixaFloat = findViewById(R.id.CaixaFloat)
        cnpjText = findViewById(R.id.CnpjText)
        nomeText = findViewById(R.id.NomeText)
        capacityTank = findViewById(R.id.capacityTank)
        capacitySeats = findViewById(R.id.qtd_assentos)
        isAirConditioning = findViewById(R.id.isCondicionado)
        insertButton = findViewById(R.id.insertButton)
        secondScrenn = findViewById(R.id.secondScreen)


        var tipo = 0;

        secondScrenn.setOnClickListener {
            val intent = Intent(this, second_screnn::class.java)
            intent.putExtra("listBussiness", listBussiness)
            register.launch(intent)
        }


        categoryBussiness.setOnCheckedChangeListener { _, checkedId ->
            when (checkedId) {
                R.id.supermercadoButton -> {
                    capacityTank.isEnabled = false
                    capacitySeats.isEnabled = false
                    isAirConditioning.isEnabled = true
                    tipo = 1
                }

                R.id.postoButton -> {
                    capacityTank.isEnabled = true
                    capacitySeats.isEnabled = false
                    isAirConditioning.isEnabled = false
                    tipo = 2
                }

                R.id.cinemaButton -> {
                    capacityTank.isEnabled = false
                    capacitySeats.isEnabled = true
                    isAirConditioning.isEnabled = false
                    tipo = 3
                }
            }
        }

        insertButton.setOnClickListener {

            //verificando se os campos estão vazios
            if(nomeText.text.toString() == "" || cnpjText.text.toString() == "" || caixaFloat.text.toString() == ""){
                val toast = Toast.makeText(applicationContext, "Preencha todos os campos", Toast.LENGTH_SHORT)
                toast.show()
                return@setOnClickListener
            }

            //verificar se o cnpj já existe
            listBussiness.forEach(){
                var splitArray = it.split(" - ")
                var cnpj = splitArray[1].split(": ")

                if(cnpj[1] == cnpjText.text.toString()){
                    val toast = Toast.makeText(applicationContext, "Cnpj já cadastrado", Toast.LENGTH_SHORT)
                    toast.show()
                    return@setOnClickListener
                }

            }
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

    //Contrato para recuperação dos dados que vêm da tela 02.

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