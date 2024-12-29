import com.omerutkudemir.yazilimyapimiproject.model.UserCheck

class GetUsersUseCase(private val userRepository: UserRepository) {
    suspend operator fun invoke(): List<UserCheck> {
        return userRepository.getUsers()
    }
}
