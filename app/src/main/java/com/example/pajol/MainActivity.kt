package com.example.pajol

import RetrofitViewModelFactory
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.example.pajol.databinding.ActivityMainBinding
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
class MainActivity : AppCompatActivity() {
    private lateinit var viewModel: RetrofitViewModel
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val apiService = Retrofit.Builder()
            .baseUrl("https://fakestoreapi.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiService::class.java)

        viewModel = ViewModelProvider(
            this,
            RetrofitViewModelFactory(apiService)
        ).get(RetrofitViewModel::class.java)

        // Configuration du RecyclerView
        binding.recyclerView.layoutManager = GridLayoutManager(this, 2)

        viewModel.data.observe(this, { products ->
            val adapter = ProductAdapter(products)
            binding.recyclerView.adapter = adapter
        })

        // Charger les catégories
        viewModel.categories.observe(this, { categories ->
            val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, categories)
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            binding.categorySpinner.adapter = adapter
        })

        binding.categorySpinner.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
                    val selectedCategory = parent.getItemAtPosition(position).toString()
                    viewModel.getProductsByCategory(selectedCategory)
                }

                override fun onNothingSelected(parent: AdapterView<*>) {
                    // Optionnel : gérer aucun choix sélectionné
                }
            }
    }
}

