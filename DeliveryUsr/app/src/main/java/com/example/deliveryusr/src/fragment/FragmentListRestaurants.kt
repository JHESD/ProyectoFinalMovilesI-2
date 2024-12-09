package com.example.deliveryusr.src.fragment

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.TextView
import android.widget.Toast
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.navigation.NavigationView
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import androidx.drawerlayout.widget.DrawerLayout
import androidx.appcompat.app.AppCompatActivity
import com.example.deliveryusr.R
import com.example.deliveryusr.api.Json_Phs
import com.example.deliveryusr.databinding.ActivityFragmentListRestaurantsBinding
import com.example.deliveryusr.model.User
import com.example.deliveryusr.repositories.RetrofitRepositories
import com.example.deliveryusr.repositories.retrofit.PreferenceRepository
import com.example.deliveryusr.src.main.MainActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FragmentListRestaurants : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityFragmentListRestaurantsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Inflar el diseño
        binding = ActivityFragmentListRestaurantsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.appBarFragmentListRestaurants.toolbar)

        // Configurar el botón flotante
        binding.appBarFragmentListRestaurants.fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null)
                .setAnchorView(R.id.fab).show()
        }

        // Configuración de navegación
        val drawerLayout: DrawerLayout = binding.drawerLayout
        val navView: NavigationView = binding.navView
        val navController =
            findNavController(R.id.nav_host_fragment_content_fragment_list_restaurants)

        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.nav_home, R.id.nav_gallery
            ), drawerLayout
        )

        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)

        // Llamada para obtener los detalles del usuario
        fetchUserDetails()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.fragment_list_restaurants, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_settings -> { // Este es el ID del menú "Cerrar sesión"
                logout() // Llama a la función de cerrar sesión
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }


    private fun logout() {
        // Eliminar el token
        PreferenceRepository.clearToken(this)

        // Redirigir al usuario al LoginActivity
        val intent = Intent(this, MainActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)

        // Finalizar la actividad actual
        finish()
    }


    override fun onSupportNavigateUp(): Boolean {
        val navController =
            findNavController(R.id.nav_host_fragment_content_fragment_list_restaurants)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }

    private fun fetchUserDetails() {
        // Usar el contexto de la actividad
        val apiService = RetrofitRepositories.getRetrofinInstance(this).create(Json_Phs::class.java)
        val call = apiService.getUserDetails()

        call.enqueue(object : Callback<User> {
            override fun onResponse(call: Call<User>, response: Response<User>) {
                if (response.isSuccessful) {
                    val user = response.body()
                    if (user != null) {
                        updateUI(user)
                    }
                } else {
                    Log.e("API_ERROR", "Error fetching user details: ${response.code()}")
                }
            }

            override fun onFailure(call: Call<User>, t: Throwable) {
                Log.e("API_ERROR", "Failed to fetch user details: ${t.message}")
            }
        })
    }

    private fun updateUI(user: User) {
        // Usar findViewById para acceder a las vistas
        val nameTextView: TextView = findViewById(R.id.txtNameUser)
        val emailTextView: TextView = findViewById(R.id.txtEmailUser)

        // Actualizar los valores
        nameTextView.text = user.name
        emailTextView.text = user.email
    }
}