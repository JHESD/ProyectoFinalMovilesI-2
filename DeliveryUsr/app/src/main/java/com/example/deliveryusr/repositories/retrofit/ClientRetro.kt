package com.example.deliveryusr.repositories.retrofit

import android.content.Context
import com.example.deliveryusr.api.Json_Phs
import com.example.deliveryusr.model.LoginRequest
import com.example.deliveryusr.model.Token
import com.example.deliveryusr.repositories.RetrofitRepositories
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

object ClientRetro{
    fun doLogin(
        context: Context,
        email: String,
        password: String,
        success: (Token?) -> Unit,
        failure: (Throwable) -> Unit
    ) {
        val retrofit = RetrofitRepositories.getRetrofinInstance(context)
        val service: Json_Phs = retrofit.create(Json_Phs::class.java)

        android.util.Log.d("ClientRetro", "Iniciando login con email: $email y password: $password")

        service.loginUser(LoginRequest(email, password))
            .enqueue(object : Callback<Token> {
                override fun onResponse(
                    res: Call<Token>,
                    response: Response<Token>
                ) {
                    android.util.Log.d("ClientRetro", "Respuesta recibida: ${response.code()}")

                    if (response.code() == 401) {
                        android.util.Log.d("ClientRetro", "Credenciales incorrectas")
                        success(null)
                        return
                    }

                    val loginResponse = response.body()
                    if (loginResponse != null) {
                        android.util.Log.d("ClientRetro", "Login exitoso: ${loginResponse.access_token}")
                    } else {
                        android.util.Log.d("ClientRetro", "Respuesta sin cuerpo (null)")
                    }
                    success(loginResponse)
                }

                override fun onFailure(res: Call<Token>, t: Throwable) {
                    failure(t)
                    android.util.Log.e("ClientRetro", "Fallo en el login", t)
                }
            })
    }
}