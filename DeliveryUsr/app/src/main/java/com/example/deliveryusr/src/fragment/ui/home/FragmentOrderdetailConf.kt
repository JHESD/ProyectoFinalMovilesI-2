package com.example.deliveryusr.src.fragment.ui.home

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.deliveryusr.R
import com.example.deliveryusr.model.CartItem
import com.example.deliveryusr.model.Order
import com.example.deliveryusr.repositories.retrofit.OrderRepository
import com.example.deliveryusr.src.adapter.CartAdapter
import com.example.deliveryusr.src.main.MainMapsOrderPay
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FragmentOrderdetailConf : Fragment() {

    private lateinit var tvTotal: TextView
    private lateinit var etAddress: EditText
    private lateinit var btnPickLocation: Button
    private lateinit var btnFinalizeOrder: Button
    private lateinit var recyclerView: RecyclerView

    private var cart: MutableList<CartItem> = mutableListOf()
    private var latitude: Double? = null
    private var longitude: Double? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_orderdetail_conf, container, false)

        tvTotal = view.findViewById(R.id.txtTotalPayProduct)
        etAddress = view.findViewById(R.id.edAddress)
        btnPickLocation = view.findViewById(R.id.btnPickLocation)
        btnFinalizeOrder = view.findViewById(R.id.btnFinalizeOrder)
        recyclerView = view.findViewById(R.id.rcvProductOrder)

        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        // Obtener datos del carrito desde argumentos
        cart = arguments?.getParcelableArrayList("cart_items") ?: mutableListOf()

        displayCartDetails()

        btnPickLocation.setOnClickListener { openMapPicker() }
        btnFinalizeOrder.setOnClickListener { finalizeOrder() }

        return view
    }

    private fun displayCartDetails() {
        recyclerView.adapter = CartAdapter(cart)
        val total = cart.sumOf { it.product.price * it.quantity }
        tvTotal.text = "Total: $total Bs."
    }

    private fun openMapPicker() {
        val intent = Intent(requireContext(), MainMapsOrderPay::class.java)
        startActivityForResult(intent, REQUEST_MAP_LOCATION)
    }

    private fun finalizeOrder() {
        val address = etAddress.text.toString().trim()
        when {
            address.isEmpty() -> {
                Toast.makeText(requireContext(), "Por favor ingresa una dirección.", Toast.LENGTH_SHORT).show()
            }
            latitude == null || longitude == null -> {
                Toast.makeText(requireContext(), "Selecciona una ubicación en el mapa.", Toast.LENGTH_SHORT).show()
            }
            cart.isEmpty() -> {
                Toast.makeText(requireContext(), "El carrito está vacío.", Toast.LENGTH_SHORT).show()
            }
            else -> {
                val order = Order(
                    restaurantId = cart.first().product.restaurantId,
                    deliveryAddress = address,
                    latitude = latitude!!,
                    longitude = longitude!!,
                    items = cart.map { it.toOrderItem() }
                )
                sendOrderToApi(order)
            }
        }
    }

    private fun sendOrderToApi(order: Order) {
        OrderRepository.createOrder(
            context = requireContext(),
            order = order,
            success = {
                Toast.makeText(requireContext(), "Pedido confirmado.", Toast.LENGTH_SHORT).show()
                parentFragmentManager.popBackStack()
            },
            failure = { throwable ->
                Toast.makeText(requireContext(), "Error: ${throwable.message}", Toast.LENGTH_SHORT).show()
            }
        )
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_MAP_LOCATION && resultCode == Activity.RESULT_OK) {
            latitude = data?.getDoubleExtra("latitude", 0.0)
            longitude = data?.getDoubleExtra("longitude", 0.0)

            if (latitude == 0.0 || longitude == 0.0) {
                Toast.makeText(requireContext(), "No se pudo obtener la ubicación.", Toast.LENGTH_SHORT).show()
                latitude = null
                longitude = null
            }
        }
    }

    companion object {
        private const val REQUEST_MAP_LOCATION = 100
    }
}