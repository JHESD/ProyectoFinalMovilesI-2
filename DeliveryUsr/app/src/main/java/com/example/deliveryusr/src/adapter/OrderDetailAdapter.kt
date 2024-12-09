package com.example.deliveryusr.src.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.deliveryusr.R
import com.example.deliveryusr.model.OrderDetail

class OrderDetailAdapter(
     private var orderDetails: MutableList<OrderDetail>,
     private val onItemClick: (OrderDetail) -> Unit // Callback de clic
) : RecyclerView.Adapter<OrderDetailAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_order_detail, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val orderDetail = orderDetails[position]

        holder.productName.text = orderDetail.product.name
        holder.productPrice.text = "Precio: ${orderDetail.productPrice} Bs."
        holder.productQuantity.text = "Cantidad: ${orderDetail.quantity}"

        Glide.with(holder.itemView.context)
            .load(orderDetail.product.image)
            .into(holder.productImage)

        // Configurar clic para cada elemento
        holder.itemView.setOnClickListener {
            onItemClick(orderDetail) // Llamar al callback con el detalle del pedido
        }
    }

    override fun getItemCount(): Int {
        return orderDetails.size
    }

    fun updateData(newOrderDetails: List<OrderDetail>) {
        orderDetails.clear()
        orderDetails.addAll(newOrderDetails)
        notifyDataSetChanged()
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val productName: TextView = itemView.findViewById(R.id.txtProductName)
        val productPrice: TextView = itemView.findViewById(R.id.txtProductPrice)
        val productQuantity: TextView = itemView.findViewById(R.id.txtProductQuantity)
        val productImage: ImageView = itemView.findViewById(R.id.imgProduct)
    }
}