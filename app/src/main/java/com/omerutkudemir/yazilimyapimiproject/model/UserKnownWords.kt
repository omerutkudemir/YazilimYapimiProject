package com.omerutkudemir.yazilimyapimiproject.model

data class UserKnownWords(

    val userID: Int,
    val en:String?,
    val tr:String?,
    var sayi: String,
    val tarih:String,
    val imageUri: String?) {

}