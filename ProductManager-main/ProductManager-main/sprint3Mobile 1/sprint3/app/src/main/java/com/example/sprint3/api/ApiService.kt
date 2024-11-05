package com.example.sprint3.api

import com.example.sprint3.models.Produto
import com.example.sprint3.models.Login
import com.example.sprint3.models.Register
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.DELETE
import retrofit2.http.Path

interface ApiService {

    // Registro de novo usuário
    @POST("/api/register")
    fun register(@Body registerRequest: Register): Call<String>

    // Login do usuário
    @POST("/api/login")
    fun login(@Body loginRequest: Login): Call<String>

    // Adicionar um novo produto
    @POST("/api/products")
    fun addProduct(@Body productRequest: Produto): Call<Produto>  // Retorna o Produto criado (não apenas Unit)

    // Obter todos os produtos
    @GET("/api/products")
    fun getProducts(): Call<List<Produto>>

    // Atualizar um produto
    @PUT("/api/products/{id}")
    fun updateProduct(@Path("id") productId: Int, @Body productRequest: Produto): Call<Produto> // Retorna o Produto atualizado

    // Excluir um produto
    @DELETE("/api/products/{id}")
    fun deleteProduct(@Path("id") productId: Int): Call<Void>  // Pode retornar Void se não houver corpo na resposta
}
