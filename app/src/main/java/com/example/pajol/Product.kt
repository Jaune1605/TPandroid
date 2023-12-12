package com.example.pajol
import java.io.Serializable

data class Product(
    val id: Int,
    val title: String,
    val price: Double,
    val image: String
) : Serializable
