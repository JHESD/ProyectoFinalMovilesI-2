package com.example.deliveryusr.repositories.retrofit

import android.content.Context
import com.example.deliveryusr.api.Json_Phs
import com.example.deliveryusr.model.User
import com.example.deliveryusr.repositories.RetrofitRepositories
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

object RegisterRetro {
    fun doRegister(
        context: Context,
        user: User,
        success: () -> Unit,
        failure: (Throwable) -> Unit
    ) {
        val retrofit = RetrofitRepositories.getRetrofinInstance(context)
        val service: Json_Phs = retrofit.create(Json_Phs::class.java)

        service.registerUser(user).enqueue(object : Callback<User> {
            override fun onResponse(call: Call<User>, response: Response<User>) {
                if (response.isSuccessful) {
                    success()
                } else {
                    failure(Throwable("Error del servidor: ${response.code()}"))
                }
            }

            override fun onFailure(call: Call<User>, t: Throwable) {
                failure(t)
            }
        })
    }
}