package com.example.deliveryusr.src.main

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.deliveryusr.R
import com.example.deliveryusr.api.Json_Phs
import com.example.deliveryusr.databinding.ActivityMainBinding
import com.example.deliveryusr.databinding.ActivityMainRegisterBinding
import com.example.deliveryusr.model.User
import com.example.deliveryusr.repositories.RetrofitRepositories
import com.example.deliveryusr.src.fragment.FragmentListRestaurants
import com.example.deliveryusr.src.viewmodel.RegisterVM
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainRegister : AppCompatActivity() {
    private lateinit var binding: ActivityMainRegisterBinding
    private  val model: RegisterVM by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        setupEventListener()
        setupViewModelObservers()
    }

    private fun setupViewModelObservers() {
        model.errorMessage.observe(this) { errorMessage ->
            if (errorMessage.isNotEmpty()) {
                Toast.makeText(this, errorMessage, Toast.LENGTH_SHORT).show()
            }
        }

        model.successMessage.observe(this) { successMessage ->
            if (successMessage.isNotEmpty()) {
                Toast.makeText(this, successMessage, Toast.LENGTH_SHORT).show()
                navigateToLogin()
                finish() // Cierra la actividad y regresa al login
            }
        }
    }

    private fun setupEventListener() {
        binding.bttRegister.setOnClickListener {
            val name = binding.txtEdtNameUser.text.toString().trim()
            val email = binding.txtEdtEmailUser.text.toString().trim()
            val password = binding.editTextTextPassword.text.toString().trim()

            if (name.isEmpty() || email.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Por favor, complete todos los campos", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                Toast.makeText(this, "Ingrese un correo electrónico válido", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            model.registerUser(name, email, password, this)
        }
    }


    private fun navigateToLogin() {
        val intent = Intent(this, ActivityMainBinding::class.java)
        startActivity(intent)
    }
}

