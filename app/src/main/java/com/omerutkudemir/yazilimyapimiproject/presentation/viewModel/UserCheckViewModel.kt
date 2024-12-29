import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.omerutkudemir.yazilimyapimiproject.model.UserCheck
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class UserCheckViewModel(
    private val getUsersUseCase: GetUsersUseCase
) : ViewModel() {

    val users = MutableLiveData<List<UserCheck>>()

    fun fetchUsers() {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val userList = getUsersUseCase()
                withContext(Dispatchers.Main) {
                    users.value = userList
                }
            } catch (e: Exception) {
                // Hata yönetimi
            }
        }
    }
}
