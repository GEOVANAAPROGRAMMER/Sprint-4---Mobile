package com.example.sprint3.activities

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.sprint3.R
import android.content.SharedPreferences

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Verifica se o usuário está logado
        val sharedPreferences: SharedPreferences = getSharedPreferences("UserPreferences", MODE_PRIVATE)
        val isLoggedIn = sharedPreferences.getBoolean("isLoggedIn", false)

        // Se o usuário não estiver logado, redireciona para a tela de login
        if (!isLoggedIn) {
            Toast.makeText(this, "Por favor, faça o login", Toast.LENGTH_SHORT).show()
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }

        val cadastrarProdutoButton = findViewById<Button>(R.id.cadastrarProdutoButton)
        val listarProdutosButton = findViewById<Button>(R.id.listarProdutosButton)

        // Ação do botão de cadastrar produto
        cadastrarProdutoButton.setOnClickListener {
            startActivity(Intent(this, AddProductActivity::class.java))
        }

        // Ação do botão de listar produtos
        listarProdutosButton.setOnClickListener {
            startActivity(Intent(this, ProductListActivity::class.java))
        }
    }
}
