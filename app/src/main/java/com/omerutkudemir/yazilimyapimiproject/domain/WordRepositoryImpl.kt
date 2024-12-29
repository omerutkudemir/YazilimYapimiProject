import com.omerutkudemir.yazilimyapimiproject.model.UserKnownWords
import com.omerutkudemir.yazilimyapimiproject.model.Words
import com.omerutkudemir.yazilimyapimiproject.repository.words.ApiService
import com.omerutkudemir.yazilimyapimiproject.repository.words.WordRepository

class WordRepositoryImpl(private val apiService: ApiService) : WordRepository {
    override suspend fun getWords(): List<Words> {
        return apiService.fetchWords()
    }

    override suspend fun getKnownWords(userId: String): List<UserKnownWords> {
        return apiService.fetchKnownWords(userId)
    }
}