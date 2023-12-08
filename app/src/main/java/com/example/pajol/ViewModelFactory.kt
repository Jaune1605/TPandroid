import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.pajol.ApiService
import com.example.pajol.RetrofitViewModel


class RetrofitViewModelFactory(private val apiService: ApiService) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(RetrofitViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return RetrofitViewModel(apiService) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
