package com.example.deliveryusr.model

import android.os.Parcel
import android.os.Parcelable

data class CartItem(
    val product: Product,
    var quantity: Int
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readParcelable(Product::class.java.classLoader)!!, // Leer Product del parcel
        parcel.readInt()
    )

    fun toOrderItem(): orderItem {
        return orderItem(
            productId = product.id,
            quantity = quantity
        )
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeParcelable(product, flags) // Escribir Product en el parcel
        parcel.writeInt(quantity)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<CartItem> {
        override fun createFromParcel(parcel: Parcel): CartItem {
            return CartItem(parcel)
        }

        override fun newArray(size: Int): Array<CartItem?> {
            return arrayOfNulls(size)
        }
    }
}
