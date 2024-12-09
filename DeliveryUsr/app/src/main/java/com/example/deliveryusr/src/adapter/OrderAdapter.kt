package com.example.deliveryusr.src.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.deliveryusr.R
import com.example.deliveryusr.model.Order

class OrderAdapter(
    private val orderList: MutableList<Order>,
    private val onItemClick: (Order) -> Unit
) : RecyclerView.Adapter<OrderAdapter.OrderViewHolder>() {

    inner class OrderViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val orderIdTextView: TextView = view.findViewById(R.id.txtOrderId)
        val restaurantNameTextView: TextView = view.findViewById(R.id.txtRestaurantName)
        val totalTextView: TextView = view.findViewById(R.id.txtOrderTotal)
        val statusTextView: TextView = view.findViewById(R.id.txtOrderStatus)
        val addressTextView: TextView = view.findViewById(R.id.txtDeliveryAddress)

        fun bind(order: Order) {
            orderIdTextView.text = "Pedido #${order.id}"
            restaurantNameTextView.text = "Restaurante ID: ${order.restaurantId}"
            totalTextView.text = "Total: ${order.total} Bs."
            statusTextView.text = getStatusText(order.status)
            addressTextView.text = "Dirección: ${order.deliveryAddress}"

            itemView.setOnClickListener { onItemClick(order) }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrderViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_orders_list, parent, false)
        return OrderViewHolder(view)
    }

    override fun onBindViewHolder(holder: OrderViewHolder, position: Int) {
        holder.bind(orderList[position])
    }

    override fun getItemCount(): Int = orderList.size

    fun updateData(newOrders: List<Order>) {
        orderList.clear()
        orderList.addAll(newOrders)
        notifyDataSetChanged()
    }

    private fun getStatusText(status: Int): String {
        return when (status) {
            1 -> "Solicitado"
            2 -> "Aceptado"
            3 -> "En camino"
            4 -> "Finalizado"
            else -> "Desconocido"
        }
    }
}