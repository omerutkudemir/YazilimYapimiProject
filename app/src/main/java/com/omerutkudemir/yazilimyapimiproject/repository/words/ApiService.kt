package com.omerutkudemir.yazilimyapimiproject.repository.words

import com.google.common.reflect.TypeToken
import com.google.gson.Gson
import com.omerutkudemir.yazilimyapimiproject.model.UserKnownWords
import com.omerutkudemir.yazilimyapimiproject.model.Words
import com.omerutkudemir.yazilimyapimiproject.model.WordsForAdd
import okhttp3.OkHttpClient
import okhttp3.Request
import java.io.IOException

class ApiService(private val client: OkHttpClient, private val gson: Gson) {

    suspend fun fetchWords(): List<Words> {
        val request = Request.Builder()
            .url("https://9349-31-223-42-68.ngrok-free.app/basicwords")
            .build()

        val response = client.newCall(request).execute()
        if (!response.isSuccessful) throw IOException("Unexpected code $response")

        val responseBody = response.body!!.string()
        val wordListType = object : TypeToken<List<WordsForAdd>>() {}.type
        return gson.fromJson(responseBody, wordListType)
    }

    suspend fun fetchKnownWords(userId: String): List<UserKnownWords> {
        val request = Request.Builder()
            .url("https://9349-31-223-42-68.ngrok-free.app/kullanicilar/$userId/bildikleri")
            .build()

        val response = client.newCall(request).execute()
        if (!response.isSuccessful) throw IOException("Unexpected code $response")

        val responseBody = response.body!!.string()
        val wordListType = object : TypeToken<List<UserKnownWords>>() {}.type
        return gson.fromJson(responseBody, wordListType)
    }
}
