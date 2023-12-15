package com.example.pajol

import CartManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import android.widget.ImageView
import com.bumptech.glide.Glide

class CartAdapter(
    private val cartItems: MutableList<Product>,
    private val cartManager: CartManager,
    private val onItemQuantityChanged: () -> Unit
) : RecyclerView.Adapter<CartAdapter.CartViewHolder>() {
    class CartViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val productName: TextView = view.findViewById(R.id.cartItemTitle)
        val productPrice: TextView = view.findViewById(R.id.cartItemPrice)
        val productImage: ImageView = view.findViewById(R.id.cartItemImage)
        val increaseQuantityButton: Button = view.findViewById(R.id.increaseQuantityButton)
        val decreaseQuantityButton: Button = view.findViewById(R.id.decreaseQuantityButton)
        val productQuantity: TextView = view.findViewById(R.id.cartItemQuantity)


        fun bind(product: Product) {
            productName.text = product.title
            productPrice.text = "€${product.price}"
            productQuantity.text = product.quantity.toString()

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
        val item = cartItems[position]
        holder.bind(item)

        holder.increaseQuantityButton.setOnClickListener {
            item.quantity++
            Log.d("CartAdapter", "Augmentation de la quantité : ${item.title}, Nouvelle quantité : ${item.quantity}")
            notifyItemChanged(position)
            cartManager.updateItem(item)
            onItemQuantityChanged()
        }

        holder.decreaseQuantityButton.setOnClickListener {
            if (item.quantity > 1) {
                item.quantity--
                Log.d("CartAdapter", "Diminution de la quantité : ${item.title}, Nouvelle quantité : ${item.quantity}")
                notifyItemChanged(position)
                cartManager.updateItem(item)
            } else {
                Log.d("CartAdapter", "Suppression de l'article : ${item.title}")
                cartItems.removeAt(position)
                notifyItemRemoved(position)
                cartManager.removeItem(item)
            }
            onItemQuantityChanged()
        }


    }

    override fun getItemCount() = cartItems.size
}
