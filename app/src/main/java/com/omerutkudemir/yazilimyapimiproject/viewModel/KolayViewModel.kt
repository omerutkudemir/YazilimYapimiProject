package com.omerutkudemir.yazilimyapimiproject.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.crashlytics.buildtools.reloc.com.google.common.reflect.TypeToken
import com.google.gson.Gson
import com.omerutkudemir.yazilimyapimiproject.model.EasyWords

import com.omerutkudemir.yazilimyapimiproject.model.UserKnownWords
import com.omerutkudemir.yazilimyapimiproject.transfer.dataTransfer
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import okhttp3.Request
import java.io.IOException

class KolayViewModel:ViewModel() {
    var EasyWordsList= MutableLiveData<List<EasyWords>>()
    var BilinenKolayKelimeler=MutableLiveData<List<UserKnownWords>>()

      fun veriCekme() {
        val client = OkHttpClient()
        val gson = Gson()


            CoroutineScope(Dispatchers.IO).launch {
                val request = Request.Builder()
                    .url("https://180b-188-119-52-89.ngrok-free.app/basicwords")
                    .build()

                client.newCall(request).execute().use { response ->
                    if (!response.isSuccessful) throw IOException("Unexpected code $response")

                    val responseBody = response.body!!.string()


                    // JSON yanıtını Product listesine dönüştür
                    val wordListType = object : TypeToken<List<EasyWords>>() {}.type
                    val words: List<EasyWords> = gson.fromJson(responseBody, wordListType)

                    // Ana iş parçacığına geçiş yap ve LiveData'yı güncelle
                    withContext(Dispatchers.Main) {
                        EasyWordsList.value = words

                    }
                }
            }



    }

        fun bilinenKelimeler()
        {
            val client = OkHttpClient()
            val gson = Gson()
            val kullaniciID=dataTransfer.id

            CoroutineScope(Dispatchers.IO).launch {
                val request = Request.Builder()
                    .url("https://180b-188-119-52-89.ngrok-free.app/kullanicilar/${kullaniciID}/bildikleri")
                    .build()

                client.newCall(request).execute().use { response ->
                    if (!response.isSuccessful) throw IOException("Unexpected code $response")

                    val responseBody = response.body!!.string()


                    // JSON yanıtını Product listesine dönüştür
                    val wordListType = object : TypeToken<List<UserKnownWords>>() {}.type
                    val words: List<UserKnownWords> = gson.fromJson(responseBody, wordListType)

                    // Ana iş parçacığına geçiş yap ve LiveData'yı güncelle
                    withContext(Dispatchers.Main) {
                        BilinenKolayKelimeler.value = words

                    }
                }
            }
        }
}