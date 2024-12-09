package com.example.deliveryusr.model

data class OrderDetail(
    val id: Int,
    val productId: Int,
    val orderId: Int,
    val quantity: Int,
    val productPrice: Double,
    val product: Product
)