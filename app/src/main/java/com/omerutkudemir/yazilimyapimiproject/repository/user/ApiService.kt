import com.google.common.reflect.TypeToken
import com.google.gson.Gson
import com.omerutkudemir.yazilimyapimiproject.model.UserCheck
import okhttp3.OkHttpClient
import okhttp3.Request
import java.io.IOException

class ApiService(private val client: OkHttpClient, private val gson: Gson) {

    suspend fun fetchUsers(): List<UserCheck> {
        val request = Request.Builder()
            .url("https://9349-31-223-42-68.ngrok-free.app/kullanicilar")
            .build()

        val response = client.newCall(request).execute()
        if (!response.isSuccessful) throw IOException("Unexpected code $response")

        val responseBody = response.body!!.string()

        // JSON yanıtını UserCheck listesine dönüştür
        val userListType = object : TypeToken<List<UserCheck>>() {}.type
        return gson.fromJson(responseBody, userListType)
    }
}
