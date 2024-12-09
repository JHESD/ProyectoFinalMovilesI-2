package com.example.deliveryusr.repositories.retrofit

import android.content.Context
import com.example.deliveryusr.api.Json_Phs
import com.example.deliveryusr.model.Order
import com.example.deliveryusr.repositories.RetrofitRepositories
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

object OrderRepository{
    fun createOrder(
        context: Context,
        order: Order,
        success: () -> Unit,
        failure: (Throwable) -> Unit
    ) {
        val retrofit = RetrofitRepositories.getRetrofinInstance(context)
        val service: Json_Phs = retrofit.create(Json_Phs::class.java)

        service.createOrder(order).enqueue(object : Callback<Order> {
            override fun onResponse(call: Call<Order>, response: Response<Order>) {
                if (response.isSuccessful) {
                    success()
                } else {
                    failure(Throwable("Error del servidor: ${response.code()}"))
                }
            }

            override fun onFailure(call: Call<Order>, t: Throwable) {
                failure(t)
            }
        })
    }
}