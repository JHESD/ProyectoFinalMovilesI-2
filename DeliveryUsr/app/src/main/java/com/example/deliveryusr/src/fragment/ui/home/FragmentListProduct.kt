package com.example.deliveryusr.src.fragment.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.deliveryusr.R
import com.example.deliveryusr.api.Json_Phs
import com.example.deliveryusr.model.CartItem
import com.example.deliveryusr.model.Product
import com.example.deliveryusr.model.Restaurant
import com.example.deliveryusr.repositories.RetrofitRepositories
import com.example.deliveryusr.src.adapter.ProductAdapter
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FragmentListProduct : Fragment(){
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: ProductAdapter
    private lateinit var placeOrderButton: Button
    private var cart: MutableList<CartItem> = mutableListOf()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_list_product, container, false)

        recyclerView = view.findViewById(R.id.rcvListProduct)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        placeOrderButton = view.findViewById(R.id.btnPlaceOrder)

        val restaurantId = arguments?.getInt("restaurant_id") ?: 0
        fetchRestaurantDetails(restaurantId)

        placeOrderButton.setOnClickListener {
            finalizeOrder(restaurantId)
        }

        return view
    }

    private fun fetchRestaurantDetails(restaurantId: Int) {
        val retrofit = RetrofitRepositories.getRetrofinInstance(requireContext())
        val api = retrofit.create(Json_Phs::class.java)

        api.getRestaurantDetails(restaurantId).enqueue(object : Callback<Restaurant> {
            override fun onResponse(call: Call<Restaurant>, response: Response<Restaurant>) {
                if (response.isSuccessful && response.body() != null) {
                    val restaurant = response.body()!!
                    setupRecyclerView(restaurant.products)
                } else {
                    Toast.makeText(
                        requireContext(),
                        "Error al obtener detalles del restaurante",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }

            override fun onFailure(call: Call<Restaurant>, t: Throwable) {
                Toast.makeText(
                    requireContext(),
                    "Error de conexión: ${t.message}",
                    Toast.LENGTH_SHORT
                ).show()
            }
        })
    }

    private fun setupRecyclerView(products: List<Product>) {
        adapter = ProductAdapter(products) { product ->
            addToCart(product)
        }
        recyclerView.adapter = adapter
    }

    private fun addToCart(product: Product) {
        val existingItem = cart.find { it.product.id == product.id }
        if (existingItem != null) {
            existingItem.quantity += 1
        } else {
            cart.add(CartItem(product, 1))
        }

        Toast.makeText(
            requireContext(),
            "${product.name} agregado al carrito",
            Toast.LENGTH_SHORT
        ).show()
    }

    private fun finalizeOrder(restaurantId: Int) {
        if (cart.isEmpty()) {
            Toast.makeText(requireContext(), "El carrito está vacío", Toast.LENGTH_SHORT).show()
            return
        }

        val bundle = Bundle()
        bundle.putParcelableArrayList("cart_items", ArrayList(cart)) // Empaqueta correctamente

        val fragment = FragmentOrderdetailConf()
        fragment.arguments = bundle

        parentFragmentManager.beginTransaction()
            .replace(R.id.fragmentOrderdetailConf, fragment)
            .addToBackStack(null)
            .commit()
    }
}