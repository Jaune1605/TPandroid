package com.example.pajol

import CartManager
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import android.view.MenuItem
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.LinearLayoutManager
import android.content.Intent
import android.util.Log
import android.widget.Button
import android.widget.TextView


class CartActivity : AppCompatActivity() {
    private lateinit var cartRecyclerView: RecyclerView
    private lateinit var cartAdapter: CartAdapter
    private lateinit var cartManager: CartManager
    private lateinit var totalAmountTextView: TextView
    private lateinit var checkoutButton: Button


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cart)

        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        supportActionBar?.title = ""
        cartRecyclerView = findViewById(R.id.cartRecyclerView)


        totalAmountTextView = findViewById(R.id.totalAmount) // Assurez-vous que cette ligne est avant updateTotalAmount()

        cartManager = CartManager(this)
        val cartItems = cartManager.getCartItems().toMutableList()
        cartAdapter = CartAdapter(cartItems, cartManager) {
            updateTotalAmount()
            updateTotalProductCount()
        }
        cartRecyclerView.adapter = cartAdapter
        cartRecyclerView.layoutManager = LinearLayoutManager(this)

        updateTotalAmount()

        checkoutButton = findViewById(R.id.btnCheckout)
        updateCheckoutButton()
    }

    private fun updateTotalProductCount() {
        val totalProducts = cartManager.getCartItems().sumOf { it.quantity }
        val checkoutButton = findViewById<Button>(R.id.btnCheckout)
        checkoutButton.text = "Checkout ($totalProducts articles)"
    }


    private fun updateCheckoutButton() {
        val totalItems = cartManager.getCartItems().sumOf { it.quantity }
        checkoutButton.text = "Checkout ($totalItems articles)"
    }
    private fun updateTotalAmount() {
        val totalAmount = cartManager.getCartItems().sumOf { it.price * it.quantity }
        totalAmountTextView.text = "Total: ${totalAmount}€"
        Log.d("CartActivity", "Montant total mis à jour : ${totalAmount}€")
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            onBackPressed()
            return true
        }
        return super.onOptionsItemSelected(item)
    }
}

