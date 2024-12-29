package com.omerutkudemir.yazilimyapimiproject.servis

import com.google.gson.Gson
import com.omerutkudemir.yazilimyapimiproject.model.WordsForAdd
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.IOException

class UserWordPostReq(post: WordsForAdd) {


    private val client = OkHttpClient()
    val gson = Gson()
    val json = gson.toJson(post)

    suspend fun post()
    {


        println(json)

        // JSON verisini bir HTTP POST isteği ile sunucuya gönder
        val url = "https://9349-31-223-42-68.ngrok-free.app/kullanicilar/bildikleri"
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

    suspend fun Update()
    {
        println(json)


        val url = "https://9349-31-223-42-68.ngrok-free.app/kullanicilar/bildikleri"
        val mediaType = "application/json".toMediaType()
        val requestBody = json.toRequestBody(mediaType)
        val request = Request.Builder()
            .url(url)
            .put(requestBody)
            .build()

        // İsteği gönder ve cevabı al
        withContext(Dispatchers.IO) {
            try {
                val response = client.newCall(request).execute()

                if (response.isSuccessful) {
                    val responseBody = response.body?.string()
                    println(", yanıt: $responseBody")
                } else {
                    println("request is succesfuly, response code : ${response.code}")
                }
            } catch (e: IOException) {
                println("network error: ${e.message}")
            }
        }
    }

    suspend fun add(endPoint: String)
    {
        println(json)

        // JSON verisini bir HTTP POST isteği ile sunucuya gönder
        val url = "https://9349-31-223-42-68.ngrok-free.app/"+endPoint
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