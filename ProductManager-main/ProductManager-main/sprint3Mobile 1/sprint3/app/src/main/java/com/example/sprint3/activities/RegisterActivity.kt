package com.example.sprint3.activities

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.sprint3.R
import com.example.sprint3.api.RetrofitClient
import com.example.sprint3.models.Register
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RegisterActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        val nameEditText = findViewById<EditText>(R.id.nameEditText)
        val emailEditText = findViewById<EditText>(R.id.emailEditText)
        val passwordEditText = findViewById<EditText>(R.id.passwordEditText)
        val registerButton = findViewById<Button>(R.id.registerButton)

        registerButton.setOnClickListener {
            // Captura os valores inseridos nos campos
            val name = nameEditText.text.toString().trim()
            val email = emailEditText.text.toString().trim()
            val password = passwordEditText.text.toString().trim()

            // Validação dos campos
            if (name.isEmpty()) {
                nameEditText.error = "Nome é obrigatório"
                nameEditText.requestFocus()
                return@setOnClickListener
            }

            if (email.isEmpty()) {
                emailEditText.error = "E-mail é obrigatório"
                emailEditText.requestFocus()
                return@setOnClickListener
            }

            if (!email.contains("@") || !email.contains(".")) {
                emailEditText.error = "E-mail inválido"
                emailEditText.requestFocus()
                return@setOnClickListener
            }

            if (password.isEmpty()) {
                passwordEditText.error = "Senha é obrigatória"
                passwordEditText.requestFocus()
                return@setOnClickListener
            }

            if (password.length < 6) {
                passwordEditText.error = "A senha deve ter no mínimo 6 caracteres"
                passwordEditText.requestFocus()
                return@setOnClickListener
            }

            // Criação do objeto de registro
            val registerRequest = Register(name, email, password)

            // Realiza a requisição de registro
            RetrofitClient.api.register(registerRequest).enqueue(object : Callback<String> {
                override fun onResponse(call: Call<String>, response: Response<String>) {
                    if (response.isSuccessful) {
                        val message = response.body() ?: "Registrado com sucesso"
                        Toast.makeText(this@RegisterActivity, message, Toast.LENGTH_SHORT).show()

                        // Redireciona para a tela de login após sucesso
                        startActivity(Intent(this@RegisterActivity, LoginActivity::class.java))
                        finish()  // Fecha a activity de registro
                    } else {
                        // Exibe o erro retornado pela API
                        Toast.makeText(this@RegisterActivity, "Erro ao registrar: ${response.message()}", Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onFailure(call: Call<String>, t: Throwable) {
                    // Caso haja falha na requisição
                    Toast.makeText(this@RegisterActivity, "Falha na conexão: ${t.message}", Toast.LENGTH_SHORT).show()
                }
            })
        }
    }
}
