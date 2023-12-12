import android.content.Context
import android.content.SharedPreferences
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.example.pajol.Product

class CartManager(context: Context) {
    private val prefs: SharedPreferences = context.getSharedPreferences("CartPrefs", Context.MODE_PRIVATE)
    private val gson = Gson()

    fun addToCart(product: Product) {
        val cart = getCartItems().toMutableList()
        cart.add(product)
        saveCart(cart)
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
