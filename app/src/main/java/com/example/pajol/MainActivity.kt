package com.example.pajol

import RetrofitViewModelFactory
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.pajol.databinding.ActivityMainBinding
import com.google.android.material.floatingactionbutton.FloatingActionButton
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() {
    private lateinit var viewModel: RetrofitViewModel
    private lateinit var binding: ActivityMainBinding
    private var selectedCategoryButton: Button? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val apiService = Retrofit.Builder()
            .baseUrl("https://fakestoreapi.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiService::class.java)


        viewModel = ViewModelProvider(this, RetrofitViewModelFactory(apiService)).get(RetrofitViewModel::class.java)

        val fabCart = findViewById<FloatingActionButton>(R.id.fab_cart)
        fabCart.setOnClickListener {
            val intent = Intent(this, CartActivity::class.java)
            startActivity(intent)
        }

        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
        recyclerView.layoutManager = GridLayoutManager(this, 2)


        // Configuration du RecyclerView
        binding.recyclerView.layoutManager = GridLayoutManager(this, 2)

        viewModel.data.observe(this, { products ->
            val adapter = ProductAdapter(products)
            binding.recyclerView.adapter = adapter
        })


        // Charger les catégories
        viewModel.categories.observe(this) { categories ->
            val allCategories = if ("All" in categories) categories else listOf("All") + categories
            val layoutInflater = LayoutInflater.from(this)
            allCategories.forEach { category ->
                val categoryButton = layoutInflater.inflate(R.layout.category_button, binding.categoriesLayout, false) as Button
                categoryButton.text = category
                categoryButton.setOnClickListener {
                    // Gérer le clic sur la catégorie
                    if (category == "All") {
                        viewModel.reloadProducts() // Chargez tous les produits si "All" est sélectionné
                    } else {
                        viewModel.getProductsByCategory(category)
                    }
                    highlightSelectedCategory(categoryButton)
                }

                binding.categoriesLayout.addView(categoryButton)
                // Sélectionner le bouton "All" par défaut
                if (category == "All") {
                    highlightSelectedCategory(categoryButton)
                } else {
                    categoryButton.setBackgroundResource(R.drawable.unselected_category_background)
                }


            }
        }



    }

    private fun highlightSelectedCategory(selectedButton: Button) {
        selectedCategoryButton?.setBackgroundResource(R.drawable.unselected_category_background)
        selectedButton.setBackgroundResource(R.drawable.selected_category_background)
        selectedCategoryButton = selectedButton
    }





}

