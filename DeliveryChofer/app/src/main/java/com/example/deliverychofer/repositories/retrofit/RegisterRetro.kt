package com.example.deliverychofer.repositories.retrofit

import android.content.Context
import com.example.deliverychofer.api.Json_Phs
import com.example.deliverychofer.model.User
import com.example.deliverychofer.repositories.RetrofitRepositories
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