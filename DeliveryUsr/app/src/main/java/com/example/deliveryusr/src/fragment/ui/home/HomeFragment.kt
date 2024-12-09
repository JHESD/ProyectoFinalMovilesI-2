package com.example.deliveryusr.src.fragment.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.deliveryusr.R
import com.example.deliveryusr.api.Json_Phs
import com.example.deliveryusr.model.Restaurant
import com.example.deliveryusr.repositories.RetrofitRepositories
import com.example.deliveryusr.src.adapter.RestaurantAdapter
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeFragment : Fragment() {
    private lateinit var adapter: RestaurantAdapter
    private lateinit var recyclerView: RecyclerView
    private lateinit var progressBar: ProgressBar

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_home, container, false)

        // Inicializa RecyclerView y ProgressBar
        recyclerView = view.findViewById(R.id.rcvListRest)
        progressBar = view.findViewById(R.id.progressBar)

        // Configuración inicial del RecyclerView
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        adapter = RestaurantAdapter(mutableListOf()) // Lista vacía inicial
        recyclerView.adapter = adapter

        fetchRestaurants() // Cargar datos desde la API
        return view
    }

    private fun fetchRestaurants() {
        progressBar.visibility = View.VISIBLE // Mostrar barra de progreso

        val retrofit = RetrofitRepositories.getRetrofinInstance(requireContext())
        val api = retrofit.create(Json_Phs::class.java)

        api.getRestaurantsList().enqueue(object : Callback<List<Restaurant>> {
            override fun onResponse(
                call: Call<List<Restaurant>>,
                response: Response<List<Restaurant>>
            ) {
                progressBar.visibility = View.GONE // Ocultar barra de progreso

                if (response.isSuccessful && response.body() != null) {
                    val restaurantList = response.body()!!
                    adapter.updateData(restaurantList) // Actualizar adaptador con nuevos datos
                } else {
                    Toast.makeText(
                        requireContext(),
                        "Error al obtener los datos. Código: ${response.code()}",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }

            override fun onFailure(call: Call<List<Restaurant>>, t: Throwable) {
                progressBar.visibility = View.GONE // Ocultar barra de progreso
                Toast.makeText(
                    requireContext(),
                    "Fallo en la conexión: ${t.message}",
                    Toast.LENGTH_SHORT
                ).show()
            }
        })
    }
}