package com.example.deliveryusr.src.fragment.ui.gallery

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.deliveryusr.R
import com.example.deliveryusr.api.Json_Phs
import com.example.deliveryusr.model.OrderDetail
import com.example.deliveryusr.model.OrderDetailResponse
import com.example.deliveryusr.repositories.RetrofitRepositories
import com.example.deliveryusr.src.adapter.OrderDetailAdapter
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FragmentOrderDetail : Fragment() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: OrderDetailAdapter
    private lateinit var progressBar: ProgressBar

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_order_detail, container, false)

        recyclerView = view.findViewById(R.id.rcvOrderDetails)
        progressBar = view.findViewById(R.id.progressBar)

        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        adapter = OrderDetailAdapter(mutableListOf()) { orderDetail ->
            // Navegar al detalle del producto
            val fragment = ProductDetailFragment().apply {
                arguments = Bundle().apply {
                    putInt("product_id", orderDetail.productId) // Pasar el ID del producto
                }
            }
            parentFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, fragment) // Cambia el ID según tu diseño
                .addToBackStack(null) // Añadir a la pila para volver atrás
                .commit()
        }
        recyclerView.adapter = adapter


        val orderId = arguments?.getInt("order_id") ?: 0
        fetchOrderDetails(orderId)

        return view
    }

    private fun fetchOrderDetails(orderId: Int) {
        progressBar.visibility = View.VISIBLE

        val retrofit = RetrofitRepositories.getRetrofinInstance(requireContext())
        val api = retrofit.create(Json_Phs::class.java)

        api.getOrderDetails(orderId).enqueue(object : Callback<OrderDetailResponse> {
            override fun onResponse(call: Call<OrderDetailResponse>, response: Response<OrderDetailResponse>) {
                progressBar.visibility = View.GONE

                if (response.isSuccessful && response.body() != null) {
                    val orderDetails = response.body()!!.orderDetails
                    adapter.updateData(orderDetails)
                } else {
                    Toast.makeText(
                        requireContext(),
                        "Error al obtener detalles del pedido. Código: ${response.code()}",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }

            override fun onFailure(call: Call<OrderDetailResponse>, t: Throwable) {
                progressBar.visibility = View.GONE
                Toast.makeText(
                    requireContext(),
                    "Error de conexión: ${t.message}",
                    Toast.LENGTH_SHORT
                ).show()
            }
        })
    }

}