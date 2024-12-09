package com.example.deliveryusr.api

import com.example.deliveryusr.model.LoginRequest
import com.example.deliveryusr.model.Order
import com.example.deliveryusr.model.OrderDetail
import com.example.deliveryusr.model.OrderDetailResponse
import com.example.deliveryusr.model.Restaurant
import com.example.deliveryusr.model.Token
import com.example.deliveryusr.model.User
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface Json_Phs {
    // |--- Usuarios ---|
    @POST("users/login")
    fun loginUser(
        @Body credentials: LoginRequest
    ): Call<Token>

    @GET("me")
    fun getUserDetails(): Call<User>

    @POST("users")
    fun registerUser(@Body user: User): Call<User>


    // |--- Restaurantes ---|
    @GET("restaurants/{id}")
    fun getRestaurantDetails(@Path("id") id: Int): Call<Restaurant>

    @GET("restaurants")
    fun getRestaurantsList(): Call<List<Restaurant>>


    // |--- Pedidos ---|
    @POST("orders")
    fun createOrder(@Body order: Order): Call<Order>

    @GET("orders/{id}")
    fun getOrderDetails(@Path("id") id: Int): Call<OrderDetailResponse>

    @GET("orders")
    fun getUserOrders(): Call<List<Order>>
}