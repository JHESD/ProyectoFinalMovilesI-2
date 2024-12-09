package com.example.deliverychofer.src.main

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.deliverychofer.R
import com.example.deliverychofer.api.Json_Phs
import com.example.deliverychofer.model.Order
import com.example.deliverychofer.repositories.RetrofitRepositories
import com.example.deliverychofer.src.adapter.OrderAdapter
import okhttp3.Request
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FragmentOrderFree : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var orderAdapter: OrderAdapter
    private val orders = mutableListOf<Order>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_order_free, container, false)
        recyclerView = view.findViewById(R.id.rcvPedidosLibres)
        recyclerView.layoutManager = LinearLayoutManager(context)
        orderAdapter = OrderAdapter(orders)
        recyclerView.adapter = orderAdapter

        fetchFreeOrders()

        return view
    }

    private fun fetchFreeOrders() {
        val retrofit = RetrofitRepositories.getRetrofinInstance(context)
        val orderApi = retrofit.create(Json_Phs::class.java)

        orderApi.getFreeOrders().enqueue(object : Callback<List<Order>> {
            override fun onResponse(call: Call<List<Order>>, response: Response<List<Order>>) {
                if (response.isSuccessful) {
                    orders.clear()
                    response.body()?.let { orders.addAll(it) }
                    orderAdapter.notifyDataSetChanged()
                } else {
                    Log.e("FreeOrdersFragment", "Failed: ${response.errorBody()?.string()}")
                    Toast.makeText(context, "Error fetching orders", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<List<Order>>, t: Throwable) {
                Log.e("FreeOrdersFragment", "Error: ${t.message}")
                Toast.makeText(context, "Failed to fetch orders", Toast.LENGTH_SHORT).show()
            }
        })
    }
}