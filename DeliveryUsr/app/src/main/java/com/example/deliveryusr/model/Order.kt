package com.example.deliveryusr.model

data class Order(
    val id: Int = 0, // Valor predeterminado
    val userId: Int = 0, // Usuario por defecto (puedes asignar el usuario actual más tarde)
    val restaurantId: Int,
    val total: Double = 0.0, // Calcula el total después si es necesario
    val dateTime: String = "", // Puede ser una cadena vacía temporalmente
    val driverId: Int? = null, // Sin chofer asignado inicialmente
    val deliveryAddress: String,
    val latitude: Double,
    val longitude: Double,
    val status: Int = 1, // Estatus inicial (1: solicitado)
    val items: List<orderItem>
)