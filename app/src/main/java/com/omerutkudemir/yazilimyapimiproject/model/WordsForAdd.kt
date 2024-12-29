package com.omerutkudemir.yazilimyapimiproject.model

data class WordsForAdd(
    val userID: Int,
    val en: String,
    val tr: String,
    val count: String,
    val date: String,
    val imageUri:String?
) {

}