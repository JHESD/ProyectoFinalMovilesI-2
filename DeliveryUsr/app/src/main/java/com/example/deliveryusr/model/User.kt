package com.example.deliveryusr.model

data class User(
    val id: Int,
    val name: String,
    val email: String,
    val password: String,
    val role: Int  // 1: cliente
)