package com.example.deliverychofer.src.viewmodel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.deliverychofer.model.User
import com.example.deliverychofer.repositories.retrofit.RegisterRetro

class RegisterVM: ViewModel() {
    private val _errorMessage: MutableLiveData<String> by lazy {
        MutableLiveData<String>("")
    }
    val errorMessage: LiveData<String> get() = _errorMessage

    private val _successMessage: MutableLiveData<String> by lazy {
        MutableLiveData<String>("")
    }
    val successMessage: LiveData<String> get() = _successMessage

    fun registerUser(name: String, email: String, password: String, context: Context) {
        val user = User(
            id = 0, // Este será generado por el servidor
            name = name,
            email = email,
            password = password,
            role = 2 // Por defecto
        )

        RegisterRetro.doRegister( context, user,
            success = {
                _successMessage.value = "Usuario registrado exitosamente"
                _errorMessage.value = ""
            },
            failure = {
                _errorMessage.value = "Error al registrar usuario: ${it.message}"
                it.printStackTrace()
            }
        )
    }
}