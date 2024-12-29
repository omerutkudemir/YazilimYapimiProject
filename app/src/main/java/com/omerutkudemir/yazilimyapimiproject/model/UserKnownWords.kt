package com.omerutkudemir.yazilimyapimiproject.model

data class UserKnownWords(

    val userID: Int,
    val en:String?,
    val tr:String?,
    var count: String,
    val date:String,
    val imageUri: String?) {

}