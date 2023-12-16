package com.example.pajol

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class RetrofitViewModel(private val retrofitService: ApiService) : ViewModel() {
    private val _data = MutableLiveData<List<Product>>()
    val data: LiveData<List<Product>> = _data
    val categories = MutableLiveData<List<String>>()

    init {
        loadProducts()
        loadCategories()
    }
    fun reloadProducts() {
        loadProducts()
    }
    private fun loadProducts() {
        viewModelScope.launch {
            try {
                val products = retrofitService.getProducts()
                _data.value = products
            } catch (e: Exception) {
                _data.value = listOf()
            }
        }
    }

    private fun loadCategories() {
        viewModelScope.launch {
            try {
                val fetchedCategories = mutableListOf("All") // Ajouter "All" au début
                fetchedCategories.addAll(retrofitService.getCategories())
                categories.value = fetchedCategories
                Log.d("RetrofitViewModel", "Catégories chargées : $fetchedCategories")
            } catch (e: Exception) {
                Log.e("RetrofitViewModel", "Erreur lors du chargement des catégories", e)
            }
        }
    }

    fun getProductsByCategory(category: String) {
        viewModelScope.launch {
            try {
                val filteredProducts = retrofitService.getProductsByCategory(category)
                _data.value = filteredProducts
            } catch (e: Exception) {
                // Gérer l'erreur
            }
        }
    }
}
