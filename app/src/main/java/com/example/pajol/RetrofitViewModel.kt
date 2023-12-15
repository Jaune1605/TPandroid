package com.example.pajol

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class RetrofitViewModel(
    private val retrofitService: ApiService
) : ViewModel() {
    private val _data = MutableLiveData<List<Product>>()
    val data: LiveData<List<Product>> = _data

    init {
        viewModelScope.launch {
            try {
                val products = retrofitService.getProducts()
                _data.value = products
                Log.i("Products","${products.map { it.title }}")
            } catch (e: Exception) {
                _data.value = listOf()
                Log.i("Products","${e}")
            }
        }
    }
}
