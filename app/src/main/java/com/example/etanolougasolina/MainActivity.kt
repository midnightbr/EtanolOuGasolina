package com.example.etanolougasolina

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout

class MainActivity : AppCompatActivity() {

    private lateinit var textInputPriceGas: TextInputLayout
    private lateinit var editPriceGas: TextInputEditText
    private lateinit var textInputKmlGas: TextInputLayout
    private lateinit var editKmlGas: TextInputEditText

    private lateinit var textInputPriceEtanol: TextInputLayout
    private lateinit var editPriceEtanol: TextInputEditText
    private lateinit var textInputKmlEtanol: TextInputLayout
    private lateinit var editKmlEtanol: TextInputEditText

    private lateinit var btnCalcular: Button
    private lateinit var textResult: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        initialInterfaceComponents()
        btnCalcular.setOnClickListener {
            calculateBestPrice()
        }
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    private fun calculateBestPrice() {
        val priceGas = editPriceGas.text.toString()
        val kmlGas = editKmlGas.text.toString()

        val priceEtanol = editPriceEtanol.text.toString()
        val kmlEtanol = editKmlEtanol.text.toString()
        val resultValidate = validateField(priceGas, kmlGas, priceEtanol, kmlEtanol)

        if (resultValidate) {
            val resultGas = priceGas.toDouble() / kmlGas.toDouble()
            val resultEtanol = priceEtanol.toDouble() / kmlEtanol.toDouble()
            if (resultGas < resultEtanol) {
                val percentage = ((resultEtanol / resultGas) * 100) - 100
                textResult.text = "Abasteça com gasolina, a diferença foi de ${String.format("%.2f", percentage)}%"
            } else {
                val percentage = ((resultGas / resultEtanol) * 100) - 100
                textResult.text = "Abasteça com etanol, a diferença foi de ${String.format("%.2f", percentage)}%"
            }
        }
    }

    private fun validateField(
        priceGas: String,
        kmlGas: String,
        priceEtanol: String,
        kmlEtanol: String
    ): Boolean {
        textInputPriceGas.error = null
        textInputKmlGas.error = null
        textInputPriceEtanol.error = null
        textInputKmlEtanol.error = null

        if (priceGas.isNotEmpty() || kmlGas.isNotEmpty() || priceEtanol.isNotEmpty() || kmlEtanol.isNotEmpty()) {
            return true
        } else {
            if (priceGas.isEmpty()) {
                textInputPriceGas.error = "Digite o preço da gasolina!"
                return false
            }
            if (kmlGas.isEmpty()) {
                textInputKmlGas.error = "Digite o km/l da gasolina!"
                return false
            }
            if (priceEtanol.isEmpty()) {
                textInputPriceEtanol.error = "Digite o preço do etanol!"
                return false
            }
            if (kmlEtanol.isEmpty()) {
                textInputKmlEtanol.error = "Digite o km/l do etanol!"
                return false
            }
        }

        return true
    }

    private fun initialInterfaceComponents() {
        textInputPriceGas = findViewById(R.id.textInputPriceGas)
        editPriceGas = findViewById(R.id.editPriceGas)
        textInputKmlGas = findViewById(R.id.textInputKmlGas)
        editKmlGas = findViewById(R.id.editKmlGas)

        textInputPriceEtanol = findViewById(R.id.textInputPriceEtanol)
        editPriceEtanol = findViewById(R.id.editPriceEtanol)
        textInputKmlEtanol = findViewById(R.id.textInputKmlEtanol)
        editKmlEtanol = findViewById(R.id.editKmlEtanol)

        btnCalcular = findViewById(R.id.btnCalcular)
        textResult = findViewById(R.id.textResult)
    }
}