package com.example.deliverychofer.src.main

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.deliverychofer.R
import com.example.deliverychofer.databinding.ActivityMainBinding
import com.example.deliverychofer.repositories.retrofit.PreferenceRepository
import com.example.deliverychofer.src.viewmodel.ActivityVM

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val model: ActivityVM by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val token = PreferenceRepository.getToken(this)
        if (!token.isNullOrEmpty()) {
            Log.d("PreferenceRepository", "Token válido encontrado: $token")
            navigateToWelcomeScreen()
        } else {
            Log.d("PreferenceRepository", "No se encontró un token. Mostrando pantalla de login.")
            setupEventListener()
        }

        setupViewModelObservers()
    }

    private fun setupViewModelObservers() {
        model.errorMessage.observe(this) {
            if (it.isNotEmpty()) {
                Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun setupEventListener() {
        binding.bttLogin.setOnClickListener {
            val email = binding.txtInsEmail.text.toString()
            val password = binding.txtInsPassword.text.toString()

            if (email.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Por favor ingrese todos los campos.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            model.login(email, password, this)
        }

        binding.bttIrRegistro.setOnClickListener {
            navigateToRegister()
        }

        model.errorMessage.observe(this) { errorMessage ->
            if (errorMessage.isNullOrEmpty()) {
                Toast.makeText(this, "Login exitoso", Toast.LENGTH_SHORT).show()
                navigateToWelcomeScreen()
            } else {
                Toast.makeText(this, errorMessage, Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun navigateToWelcomeScreen() {
        val intent = Intent(this, FragmentOrderFree::class.java)
        startActivity(intent)
        //finish()
    }

    private fun navigateToRegister() {
        val register = Intent(this, MainRegister::class.java)
        startActivity(register)
    }
}