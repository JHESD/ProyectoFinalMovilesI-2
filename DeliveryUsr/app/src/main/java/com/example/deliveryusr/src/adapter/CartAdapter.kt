package com.example.deliveryusr.src.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.deliveryusr.R
import com.example.deliveryusr.model.CartItem

class CartAdapter(private val cartItems: List<CartItem>) :
    RecyclerView.Adapter<CartAdapter.CartViewHolder>() {

    inner class CartViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val nameTextView: TextView = view.findViewById(R.id.txtNameProd)
        val quantityTextView: TextView = view.findViewById(R.id.txtCantProd)
        val priceTextView: TextView = view.findViewById(R.id.txtPriceProd)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_list_orderpay, parent, false)
        return CartViewHolder(view)
    }

    override fun onBindViewHolder(holder: CartViewHolder, position: Int) {
        val cartItem = cartItems[position]
        holder.nameTextView.text = cartItem.product.name
        holder.quantityTextView.text = "${cartItem.quantity}"
        holder.priceTextView.text = "Precio: ${cartItem.product.price * cartItem.quantity} Bs."
    }

    override fun getItemCount(): Int = cartItems.size
}