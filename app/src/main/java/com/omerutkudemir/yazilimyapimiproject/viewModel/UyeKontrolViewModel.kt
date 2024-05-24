package com.omerutkudemir.yazilimyapimiproject.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.crashlytics.buildtools.reloc.com.google.common.reflect.TypeToken
import com.google.gson.Gson
import com.omerutkudemir.yazilimyapimiproject.model.UserKontrol
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.IOException

class UyeKontrolViewModel():ViewModel() {
    var Uyeler= MutableLiveData<List<UserKontrol>>()

    suspend fun kontrolEt()
    {
        val client = OkHttpClient()
        val gson = Gson()

        fun run() {
            CoroutineScope(Dispatchers.IO).launch {
                val request = Request.Builder()
                    .url("https://180b-188-119-52-89.ngrok-free.app/kullanicilar")
                    .build()

                client.newCall(request).execute().use { response ->
                    if (!response.isSuccessful) throw IOException("Unexpected code $response")

                    val responseBody = response.body!!.string()


                    // JSON yanıtını Product listesine dönüştür
                    val uyeListType = object : TypeToken<List<UserKontrol>>() {}.type
                    val uyeler: List<UserKontrol> = gson.fromJson(responseBody, uyeListType)

                    // Ana iş parçacığına geçiş yap ve LiveData'yı güncelle
                    withContext(Dispatchers.Main) {
                        Uyeler.value = uyeler


                    }
                }
            }
        }

        run()
    }



}