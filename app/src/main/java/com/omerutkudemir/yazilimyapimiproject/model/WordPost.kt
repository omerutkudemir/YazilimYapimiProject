package com.omerutkudemir.yazilimyapimiproject.model

import kotlinx.serialization.Serializable

data class WordPost(
    val userID: Int,
    val en: String,
    val tr: String,
    val sayi: String,
    val tarih: String,
    val imageUri:String?
) {

}