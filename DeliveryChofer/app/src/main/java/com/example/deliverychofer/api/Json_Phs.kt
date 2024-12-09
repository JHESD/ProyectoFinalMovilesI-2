package com.example.deliverychofer.api

import com.example.deliverychofer.model.LoginRequest
import com.example.deliverychofer.model.Order
import com.example.deliverychofer.model.Token
import com.example.deliverychofer.model.User
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST


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

    @GET("/orders/free")
    fun getFreeOrders(): Call<List<Order>>

}