package com.example.pajol

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.pajol.databinding.ItemProductBinding


class ProductAdapter(private val products: List<Product>) : RecyclerView.Adapter<ProductAdapter.ProductViewHolder>() {

    class ProductViewHolder(private val binding: ItemProductBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(product: Product) {
            binding.productName.text = product.title
            binding.productPrice.text = "€${product.price}"
            Glide.with(binding.root.context)
                .load(product.image)
                .into(binding.productImageView)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val binding = ItemProductBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ProductViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        val product = products[position]
        holder.bind(product)

        holder.itemView.setOnClickListener {
            val context = holder.itemView.context
            val intent = Intent(context, ProductDetailActivity::class.java)
            intent.putExtra("PRODUCT_ID", product.id)
            intent.putExtra("PRODUCT_NAME", product.title)
            intent.putExtra("PRODUCT_PRICE", product.price)
            intent.putExtra("PRODUCT_IMAGE", product.image)
            intent.putExtra("PRODUCT_CATEGORY", product.category)
            intent.putExtra("PRODUCT_DESCRIPTION", product.description)
            context.startActivity(intent)
        }
    }

    override fun getItemCount() = products.size
}

