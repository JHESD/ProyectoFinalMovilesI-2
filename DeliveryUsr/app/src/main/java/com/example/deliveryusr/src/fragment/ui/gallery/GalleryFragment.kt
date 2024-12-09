package com.example.deliveryusr.src.fragment.ui.gallery

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
import com.example.deliveryusr.model.Order
import com.example.deliveryusr.repositories.RetrofitRepositories
import com.example.deliveryusr.src.adapter.OrderAdapter
import com.example.deliveryusr.src.fragment.ui.gallery.FragmentOrderDetail
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class GalleryFragment : Fragment() {
    private lateinit var adapter: OrderAdapter
    private lateinit var recyclerView: RecyclerView
    private lateinit var progressBar: ProgressBar

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_gallery, container, false)

        recyclerView = view.findViewById(R.id.rcvListOrders)
        progressBar = view.findViewById(R.id.progressBar)

        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        adapter = OrderAdapter(mutableListOf()) { order ->
            navigateToOrderDetail(order.id)
        }
        recyclerView.adapter = adapter

        fetchOrders()
        return view
    }

    private fun fetchOrders() {
        progressBar.visibility = View.VISIBLE

        val retrofit = RetrofitRepositories.getRetrofinInstance(requireContext())
        val api = retrofit.create(Json_Phs::class.java)

        api.getUserOrders().enqueue(object : Callback<List<Order>> {
            override fun onResponse(call: Call<List<Order>>, response: Response<List<Order>>) {
                progressBar.visibility = View.GONE

                if (response.isSuccessful && response.body() != null) {
                    val orderList = response.body()!!
                    adapter.updateData(orderList)
                } else {
                    Toast.makeText(
                        requireContext(),
                        "Error al obtener los pedidos. Código: ${response.code()}",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }

            override fun onFailure(call: Call<List<Order>>, t: Throwable) {
                progressBar.visibility = View.GONE
                Toast.makeText(
                    requireContext(),
                    "Fallo en la conexión: ${t.message}",
                    Toast.LENGTH_SHORT
                ).show()
            }
        })
    }

    private fun navigateToOrderDetail(orderId: Int) {
        val fragment = FragmentOrderDetail().apply {
            arguments = Bundle().apply {
                putInt("order_id", orderId)
            }
        }

        parentFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, fragment) // Cambia `fragment_container` según tu layout principal
            .addToBackStack(null)
            .commit()
    }
}
