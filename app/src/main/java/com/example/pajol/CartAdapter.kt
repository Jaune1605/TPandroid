package com.example.pajol

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import android.widget.ImageView
import com.bumptech.glide.Glide

class CartAdapter(private val cartItems: List<Product>) : RecyclerView.Adapter<CartAdapter.CartViewHolder>() {

    class CartViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val productName: TextView = view.findViewById(R.id.cartItemTitle)
        val productPrice: TextView = view.findViewById(R.id.cartItemPrice)
        val productImage: ImageView = view.findViewById(R.id.cartItemImage)

        fun bind(product: Product) {
            productName.text = product.title
            productPrice.text = "€${product.price}"
            // Utilisez Glide ou une autre bibliothèque pour charger l'image
            Glide.with(itemView.context)
                .load(product.image)
                .into(productImage)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_cart, parent, false)
        return CartViewHolder(view)
    }

    override fun onBindViewHolder(holder: CartViewHolder, position: Int) {
        holder.bind(cartItems[position])
    }

    override fun getItemCount() = cartItems.size
}
