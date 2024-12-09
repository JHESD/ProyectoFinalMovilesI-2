package com.example.deliveryusr.src.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.deliveryusr.R
import com.example.deliveryusr.model.Product

class ProductAdapter (
    private val productList: List<Product>,
    private val onAddToCartClick: (Product) -> Unit
) : RecyclerView.Adapter<ProductAdapter.ProductViewHolder>() {

    inner class ProductViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val nameTextView: TextView = view.findViewById(R.id.txtProductName)
        val descriptionTextView: TextView = view.findViewById(R.id.txtProductDescription)
        val priceTextView: TextView = view.findViewById(R.id.txtProductPrice)
        val productImageView: ImageView = view.findViewById(R.id.imgProduct)
        val addToCartButton: Button = view.findViewById(R.id.btnAddToCart)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_list_product, parent, false)
        return ProductViewHolder(view)
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        val product = productList[position]
        holder.nameTextView.text = product.name
        holder.descriptionTextView.text = product.description
        holder.priceTextView.text = "Precio: ${product.price} Bs."
        Glide.with(holder.productImageView.context)
            .load(product.image)
            .placeholder(R.drawable.ic_launcher_foreground)
            .into(holder.productImageView)

        holder.addToCartButton.setOnClickListener {
            onAddToCartClick(product)
        }
    }

    override fun getItemCount(): Int = productList.size
}