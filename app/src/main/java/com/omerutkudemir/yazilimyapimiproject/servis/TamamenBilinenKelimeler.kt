package com.omerutkudemir.yazilimyapimiproject.servis

import com.google.gson.Gson
import com.omerutkudemir.yazilimyapimiproject.model.UserKnownWords
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.IOException

class TamamenBilinenKelimeler(post1: UserKnownWords) {

    private val client = OkHttpClient()
    val gson = Gson()
    val json = gson.toJson(post1)

    suspend fun BilinenPost()
    {
        println(json)

        // JSON verisini bir HTTP POST isteği ile sunucuya gönder
        val url = "https://180b-188-119-52-89.ngrok-free.app/kullanicilar/bildikleri"
        val mediaType = "application/json".toMediaType()
        val requestBody = json.toRequestBody(mediaType)
        val request = Request.Builder()
            .url(url)
            .post(requestBody)
            .build()

        // İsteği gönder ve cevabı al
        withContext(Dispatchers.IO) {
            try {
                val response = client.newCall(request).execute()

                if (response.isSuccessful) {
                    val responseBody = response.body?.string()
                    println("İstek başarılı, yanıt: $responseBody")
                } else {
                    println("İstek başarısız, yanıt kodu: ${response.code}")
                }
            } catch (e: IOException) {
                println("Ağ hatası: ${e.message}")
            }
        }
    }




}