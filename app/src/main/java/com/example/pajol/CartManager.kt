import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.example.pajol.Product

class CartManager(context: Context) {
    private val prefs: SharedPreferences = context.getSharedPreferences("CartPrefs", Context.MODE_PRIVATE)
    private val gson = Gson()

    fun addToCart(newProduct: Product) {
        val cart = getCartItems().toMutableList()
        val existingProduct = cart.find { it.title == newProduct.title }

        if (existingProduct != null) {
            existingProduct.quantity += 1
        } else {
            newProduct.quantity = 1
            cart.add(newProduct)
        }

        saveCart(cart)
    }

    fun updateQuantity(product: Product, quantity: Int) {
        val cart = getCartItems().toMutableList()
        val existingProduct = cart.find { it.id == product.id }

        if (existingProduct != null && quantity > 0) {
            existingProduct.quantity = quantity
        } else if (existingProduct != null) {
            cart.remove(existingProduct)
        }

        saveCart(cart)
    }
    fun updateItem(updatedProduct: Product) {
        val cart = getCartItems().toMutableList()
        val index = cart.indexOfFirst { it.title == updatedProduct.title }
        if (index != -1) {
            cart[index] = updatedProduct
            saveCart(cart)
            Log.d("CartManager", "Article mis à jour : ${updatedProduct.title}, Quantité : ${updatedProduct.quantity}")
        }
    }

    fun removeItem(product: Product) {
        val cart = getCartItems().toMutableList()
        cart.removeAll { it.title == product.title }
        saveCart(cart)
        Log.d("CartManager", "Article supprimé : ${product.title}")
    }


    fun getCartItems(): List<Product> {
        val cartJson = prefs.getString("cart", null) ?: return emptyList()
        val type = object : TypeToken<List<Product>>() {}.type
        return gson.fromJson(cartJson, type)
    }

    private fun saveCart(cart: List<Product>) {
        val editor = prefs.edit()
        val cartJson = gson.toJson(cart)
        editor.putString("cart", cartJson)
        editor.apply()
    }

    // Ajoutez d'autres méthodes si nécessaire, comme removeItem, clearCart, etc.
}
