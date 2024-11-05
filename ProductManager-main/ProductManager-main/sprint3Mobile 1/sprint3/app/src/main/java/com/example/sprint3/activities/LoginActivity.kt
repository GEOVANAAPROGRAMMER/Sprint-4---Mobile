package com.example.sprint3.activities

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.sprint3.R
import com.example.sprint3.api.RetrofitClient
import com.example.sprint3.models.Login
import android.content.SharedPreferences
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val emailEditText = findViewById<EditText>(R.id.emailEditText)
        val passwordEditText = findViewById<EditText>(R.id.passwordEditText)
        val loginButton = findViewById<Button>(R.id.loginButton)
        val registerTextView = findViewById<TextView>(R.id.registerTextView)

        loginButton.setOnClickListener {
            val email = emailEditText.text.toString().trim()
            val password = passwordEditText.text.toString().trim()

            // Validação básica para campos vazios
            if (email.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Por favor, preencha ambos os campos", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val loginRequest = Login(email, password)

            RetrofitClient.api.login(loginRequest).enqueue(object : Callback<String> {
                override fun onResponse(call: Call<String>, response: Response<String>) {
                    if (response.isSuccessful) {
                        val message = response.body() ?: "Login bem-sucedido"
                        Toast.makeText(this@LoginActivity, message, Toast.LENGTH_SHORT).show()

                        // Salvar o status de login nas SharedPreferences
                        val sharedPreferences: SharedPreferences = getSharedPreferences("UserPreferences", MODE_PRIVATE)
                        val editor = sharedPreferences.edit()
                        editor.putBoolean("isLoggedIn", true) // Marca o usuário como logado
                        editor.apply()

                        // Redireciona para a MainActivity após o login
                        startActivity(Intent(this@LoginActivity, MainActivity::class.java))
                        finish() // Finaliza a tela de login
                    } else {
                        Toast.makeText(this@LoginActivity, "Erro no login", Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onFailure(call: Call<String>, t: Throwable) {
                    Toast.makeText(this@LoginActivity, "Falha na conexão", Toast.LENGTH_SHORT).show()
                }
            })
        }

        registerTextView.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
        }
    }
}
