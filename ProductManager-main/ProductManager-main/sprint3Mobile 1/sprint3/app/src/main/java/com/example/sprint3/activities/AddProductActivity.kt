package com.example.sprint3.activities

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.sprint3.R
import com.example.sprint3.api.ApiService
import com.example.sprint3.api.RetrofitClient
import com.example.sprint3.models.Produto
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AddProductActivity : AppCompatActivity() {

    private lateinit var apiService: ApiService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_product)

        // Inicializa Retrofit e o serviço da API
        apiService = RetrofitClient.getInstance().create(ApiService::class.java)

        // Referências dos campos de input
        val productNameEditText = findViewById<EditText>(R.id.productNameEditText)
        val productDescriptionEditText = findViewById<EditText>(R.id.productDescriptionEditText)
        val productPriceEditText = findViewById<EditText>(R.id.productPriceEditText)
        val addProductButton = findViewById<Button>(R.id.addProductButton)

        addProductButton.setOnClickListener {
            val name = productNameEditText.text.toString().trim()
            val description = productDescriptionEditText.text.toString().trim()
            val priceString = productPriceEditText.text.toString().trim()

            // Verifica se os campos essenciais estão preenchidos
            if (name.isEmpty() || description.isEmpty() || priceString.isEmpty()) {
                Toast.makeText(this, "Por favor, preencha todos os campos", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Valida o preço
            val price = priceString.toDoubleOrNull()
            if (price == null || price <= 0) {
                Toast.makeText(this, "Preço inválido. Digite um valor válido", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Criando o objeto Produto
            val product = Produto(name = name, description = description, price = price)

            // Enviando o produto para a API
            apiService.addProduct(product).enqueue(object : Callback<Unit> {
                override fun onResponse(call: Call<Unit>, response: Response<Unit>) {
                    if (response.isSuccessful) {
                        Toast.makeText(this@AddProductActivity, "Produto cadastrado com sucesso!", Toast.LENGTH_SHORT).show()
                        finish() // Fecha a activity após sucesso
                    } else {
                        // Aqui, você pode exibir uma mensagem mais amigável ou detalhada sobre o erro
                        Toast.makeText(this@AddProductActivity, "Erro ao cadastrar produto. Tente novamente.", Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onFailure(call: Call<Unit>, t: Throwable) {
                    // Exibe uma mensagem de erro genérica
                    Toast.makeText(this@AddProductActivity, "Erro ao cadastrar produto: ${t.localizedMessage}", Toast.LENGTH_SHORT).show()
                }
            })
        }
    }
}
