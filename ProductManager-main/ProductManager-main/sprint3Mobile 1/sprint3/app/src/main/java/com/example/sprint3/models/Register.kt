package com.example.sprint3.models

data class Register(
    val companyName: String,
    val email: String,
    val password: String,
    val userId: Long? = null,
    val message: String? = null
)