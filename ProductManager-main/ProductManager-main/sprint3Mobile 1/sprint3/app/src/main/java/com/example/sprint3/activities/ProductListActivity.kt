package com.example.sprint3.activities

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.sprint3.R
import com.example.sprint3.api.ApiService
import com.example.sprint3.api.RetrofitClient
import com.example.sprint3.models.Product
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ProductListActivity : AppCompatActivity() {

    private lateinit var productsListView: ListView
    private lateinit var apiService: ApiService
    private lateinit var products: List<Product>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_product_list)

        // Inicializa o Retrofit e o serviço da API
        apiService = RetrofitClient.getInstance().create(ApiService::class.java)

        // Referência do ListView
        productsListView = findViewById(R.id.productsListView)

        // Carregar produtos da API
        loadProducts()

        // Configurar o click de cada item da lista para editar o produto
        productsListView.setOnItemClickListener { _, _, position, _ ->
            val selectedProduct = products[position]
            // Passar para a tela de edição
            val intent = Intent(this, AddProductActivity::class.java)
            intent.putExtra("productId", selectedProduct.id)
            intent.putExtra("productName", selectedProduct.name)
            intent.putExtra("productDescription", selectedProduct.description)
            intent.putExtra("productPrice", selectedProduct.price)
            startActivity(intent)
        }

        // Configurar a exclusão do produto (botão de contexto ou similar)
        productsListView.setOnItemLongClickListener { _, _, position, _ ->
            val selectedProduct = products[position]
            deleteProduct(selectedProduct.id)
            true // Indica que a ação foi consumida
        }
    }

    // Função para carregar os produtos da API
    private fun loadProducts() {
        apiService.getProducts().enqueue(object : Callback<List<Product>> {
            override fun onResponse(call: Call<List<Product>>, response: Response<List<Product>>) {
                if (response.isSuccessful) {
                    products = response.body() ?: emptyList()
                    // Exibir os produtos na ListView
                    val productNames = products.map { it.name }
                    val adapter = ArrayAdapter(this@ProductListActivity, android.R.layout.simple_list_item_1, productNames)
                    productsListView.adapter = adapter
                } else {
                    Toast.makeText(this@ProductListActivity, "Falha ao carregar produtos", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<List<Product>>, t: Throwable) {
                Toast.makeText(this@ProductListActivity, "Erro ao carregar produtos: ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }

    // Função para excluir um produto
    private fun deleteProduct(productId: Int) {
        apiService.deleteProduct(productId).enqueue(object : Callback<Void> {
            override fun onResponse(call: Call<Void>, response: Response<Void>) {
                if (response.isSuccessful) {
                    Toast.makeText(this@ProductListActivity, "Produto excluído com sucesso!", Toast.LENGTH_SHORT).show()
                    loadProducts() // Atualiza a lista após exclusão
                } else {
                    Toast.makeText(this@ProductListActivity, "Falha ao excluir produto", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<Void>, t: Throwable) {
                Toast.makeText(this@ProductListActivity, "Erro ao excluir produto: ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }
}
