package com.example.sprint3.models

data class Login(
    val email: String,
    val password: String,
    val userId: Long? = null,
    val token: String? = null,
    val message: String? = null
)