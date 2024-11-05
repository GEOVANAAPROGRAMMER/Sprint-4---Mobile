package com.example.sprint3.api

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object RetrofitClient {

    private const val BASE_URL = "https://datatech-42e25-default-rtdb.firebaseio.com/"

    // Timeout configurado para evitar falhas de rede demoradas
    private val okHttpClient by lazy {
        val logging = HttpLoggingInterceptor()
        logging.setLevel(HttpLoggingInterceptor.Level.BODY)

        OkHttpClient.Builder()
                .connectTimeout(30, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .addInterceptor(logging)  // Log das requisições HTTP
                .build()
    }

    // Configuração do Retrofit com OkHttpClient customizado
    private val retrofit by lazy {
        Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(okHttpClient)  // Adicionando o OkHttpClient customizado
                .addConverterFactory(GsonConverterFactory.create())
                .build()
    }

    // Instância do ApiService para chamadas à API
    val api: ApiService by lazy {
        retrofit.create(ApiService::class.java)
    }
}
