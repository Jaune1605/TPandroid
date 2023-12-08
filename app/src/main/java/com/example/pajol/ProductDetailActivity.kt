package com.example.pajol

import android.os.Bundle
import android.view.MenuItem
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.bumptech.glide.Glide

class ProductDetailActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_product_detail)

        // Activer la flèche de retour dans l'ActionBar
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)

        // Récupérer les données du produit
        val productName = intent.getStringExtra("PRODUCT_NAME")
        val productPrice = intent.getDoubleExtra("PRODUCT_PRICE", 0.0)
        val productImage = intent.getStringExtra("PRODUCT_IMAGE")

        // Afficher les données du produit
        findViewById<TextView>(R.id.productName).text = productName
        findViewById<TextView>(R.id.productPrice).text = "€$productPrice"
        Glide.with(this)
            .load(productImage)
            .into(findViewById(R.id.productImageView))
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Gérer le clic sur la flèche de retour
        if (item.itemId == android.R.id.home) {
            finish()
            return true
        }
        return super.onOptionsItemSelected(item)
    }
}
