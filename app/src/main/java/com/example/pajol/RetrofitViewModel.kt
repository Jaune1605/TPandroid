package com.example.pajol

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
                categories.value = retrofitService.getCategories()
            } catch (e: Exception) {
                // Gérer l'erreur
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
