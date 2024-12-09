package com.example.deliverychofer.src.adapter

import com.example.deliverychofer.R
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.deliverychofer.model.Order


class OrderAdapter(private val orders: List<Order>) :
    RecyclerView.Adapter<OrderAdapter.OrderViewHolder>() {

    class OrderViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val addressTextView: TextView = view.findViewById(R.id.textViewAddress)
        val totalTextView: TextView = view.findViewById(R.id.textViewTotal)
        val dateTextView: TextView = view.findViewById(R.id.textViewDate)
        val deliveryImageView: ImageView = view.findViewById(R.id.imageViewDelivery)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrderViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_pedidos_libres, parent, false)
        return OrderViewHolder(view)
    }

    override fun onBindViewHolder(holder: OrderViewHolder, position: Int) {
        val order = orders[position]
        holder.addressTextView.text = order.address
        holder.totalTextView.text = "${order.total} Bs."
        holder.dateTextView.text = order.created_at
        Glide.with(holder.itemView.context)
            .load(order.delivery_proof)
            .into(holder.deliveryImageView)
    }

    override fun getItemCount() = orders.size
}