import com.omerutkudemir.yazilimyapimiproject.model.UserCheck

interface UserRepository {
    suspend fun getUsers(): List<UserCheck>
}

class UserRepositoryImpl(private val apiService: ApiService) : UserRepository {
    override suspend fun getUsers(): List<UserCheck> {
        return apiService.fetchUsers()
    }
}
