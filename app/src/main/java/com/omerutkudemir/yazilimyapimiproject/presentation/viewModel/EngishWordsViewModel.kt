import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.omerutkudemir.yazilimyapimiproject.repository.words.GetKnownWordsUseCase
import com.omerutkudemir.yazilimyapimiproject.repository.words.GetWordsUseCase
import com.omerutkudemir.yazilimyapimiproject.repository.dataTransfer
import com.omerutkudemir.yazilimyapimiproject.model.UserKnownWords
import com.omerutkudemir.yazilimyapimiproject.model.Words
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class EngishWordsViewModel(
    private val getWordsUseCase: GetWordsUseCase,
    private val getKnownWordsUseCase: GetKnownWordsUseCase
) : ViewModel() {

    var WordList = MutableLiveData<List<Words>>()
    var KnownWordList = MutableLiveData<List<UserKnownWords>>()

    fun pullWords() {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val words = getWordsUseCase()
                withContext(Dispatchers.Main) {
                    WordList.value = words
                }
            } catch (e: Exception) {
                // Hata yönetimi
            }
        }
    }

    fun knownWords() {
        val userId = dataTransfer.id
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val knownWords = getKnownWordsUseCase(userId.toString())
                withContext(Dispatchers.Main) {
                    KnownWordList.value = knownWords
                }
            } catch (e: Exception) {
                println(e.message)
            }
        }
    }
}
