package com.example.prova01

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.ListView
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import java.io.BufferedReader
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import java.io.IOException
import java.io.InputStreamReader
import java.io.ObjectOutputStream
import kotlin.math.log

class second_screnn : AppCompatActivity() {

    lateinit var backToscreen : Button
    lateinit var SecondList: ListView
    lateinit var updateButton: Button
    lateinit var removeButton: Button
    lateinit var inputText : EditText

    private var arquivoExterno: File?=null
    private val caminhoDoArquivo = "MeuArquivo"
    private val armazenamentoExternoSomenteLeitura: Boolean get() {
        var armazSomLeitRet = false
        val armazenamentoExterno = Environment.getExternalStorageState()
        if (Environment.MEDIA_MOUNTED_READ_ONLY == armazenamentoExterno) {
            armazSomLeitRet = true
        }
        return (armazSomLeitRet)
    }
    private val armazenamentoExternoDisponivel: Boolean get() {
        var armazExtDispRet = false
        val extStorageState = Environment.getExternalStorageState()
        if (Environment.MEDIA_MOUNTED == extStorageState) {
            armazExtDispRet = true
        }
        return(armazExtDispRet)
    }
    lateinit var saveButton : Button
    lateinit var showButton: Button

    var updatedBussiness : String = ""

    override fun onCreate(savedInstanceState: Bundle?) {

        val register = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result: ActivityResult ->
            if (result.resultCode == RESULT_OK) {
                result.data?.let {
                    if (it.hasExtra("bussinessEdit")) {
                        updatedBussiness = it.getStringExtra("bussinessEdit") ?: ""
                    }
                }
            }
        }

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second_screnn)

        backToscreen = findViewById(R.id.backToscreen)
        SecondList = findViewById(R.id.secondList)
        updateButton = findViewById(R.id.updateButton)
        removeButton = findViewById(R.id.removeButton)
        inputText = findViewById(R.id.editNumber)

        saveButton = findViewById(R.id.saveButton)
        showButton = findViewById(R.id.showButton)


        var listBussiness = intent.getStringArrayListExtra("listBussiness") as ArrayList<String>

        showButton. setOnClickListener(){
            arquivoExterno = File(getExternalFilesDir(caminhoDoArquivo),"MeuArquivo")

            val fileInputStream = FileInputStream(arquivoExterno)
            val inputStreamReader = InputStreamReader(fileInputStream)
            val bufferedReader = BufferedReader(inputStreamReader)
            val stringBuilder: StringBuilder = StringBuilder()
            var text: String? = null

            while ((bufferedReader.readLine().also { text = it }) != null)
            {
                stringBuilder.append(text)
            }

            fileInputStream.close()
            Log.i("Texto",stringBuilder.toString())
        }

        if (!armazenamentoExternoDisponivel || armazenamentoExternoSomenteLeitura) {
            saveButton.isEnabled = false
        }

        saveButton.setOnClickListener(){

            arquivoExterno = File(getExternalFilesDir(caminhoDoArquivo), "MeuArquivo")
            try {
                val fileOutPutStream = FileOutputStream(arquivoExterno)
                val oos = ObjectOutputStream(fileOutPutStream)

                oos.writeObject(listBussiness)
                oos.close()
                Toast.makeText(applicationContext,"Texto salvo com sucesso!",Toast.LENGTH_SHORT).show()

            }catch ( e: IOException){
                e.printStackTrace()
            }


        }

        // O ArrayAdapter
        val arrayAdapter: ArrayAdapter<String> = ArrayAdapter(
            this, android.R.layout.simple_list_item_1, listBussiness
        )

        // Associando o ArrayAdapter à ListView
        SecondList.adapter = arrayAdapter


        removeButton.setOnClickListener(){
            listBussiness.removeAt(0)
            arrayAdapter.notifyDataSetChanged()
        }

        updateButton.setOnClickListener(){
            var indexOf = inputText.text.toString().toInt()

            val intent = Intent(this, ThirdScrenn::class.java)
            intent.putExtra("listBussiness", listBussiness)
            register.launch(intent)

            Toast.makeText(this, updatedBussiness, Toast.LENGTH_SHORT).show()
            if(updatedBussiness != ""){
                listBussiness[indexOf] = updatedBussiness
                arrayAdapter.notifyDataSetChanged()
            }


        }

        backToscreen.setOnClickListener() {
            val returnIntent = Intent()
            returnIntent.putStringArrayListExtra("updatedList", listBussiness)
            setResult(RESULT_OK, returnIntent)
            finish()
        }

    }
}