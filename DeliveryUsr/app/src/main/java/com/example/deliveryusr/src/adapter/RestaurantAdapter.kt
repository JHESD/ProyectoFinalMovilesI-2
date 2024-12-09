package com.example.deliveryusr.src.adapter

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.deliveryusr.R
import com.example.deliveryusr.model.Restaurant

class RestaurantAdapter(private val restaurantList: MutableList<Restaurant>) :
    RecyclerView.Adapter<RestaurantAdapter.RestaurantViewHolder>() {

    inner class RestaurantViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val nameTextView: TextView = view.findViewById(R.id.txtRestName)
        val addressTextView: TextView = view.findViewById(R.id.txtRestAddres)
        val imageView: ImageView = view.findViewById(R.id.imgRestImage)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RestaurantViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_list_rest, parent, false)
        return RestaurantViewHolder(view)
    }

    override fun onBindViewHolder(holder: RestaurantViewHolder, position: Int) {
        val restaurant = restaurantList[position]
        holder.nameTextView.text = restaurant.name
        holder.addressTextView.text = restaurant.address
        Glide.with(holder.imageView.context) // Usa Glide para cargar imágenes
            .load(restaurant.logo)
            .placeholder(R.drawable.ic_launcher_foreground)
            .into(holder.imageView)

        holder.itemView.setOnClickListener {
            val bundle = Bundle().apply {
                putInt("restaurant_id", restaurant.id)
            }
            Navigation.findNavController(holder.itemView)
                .navigate(R.id.fragmentListProduct, bundle)
        }
    }

    override fun getItemCount(): Int = restaurantList.size

    fun updateData(newList: List<Restaurant>) {
        restaurantList.clear()
        restaurantList.addAll(newList)
        notifyDataSetChanged()
    }
}