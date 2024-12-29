package com.omerutkudemir.yazilimyapimiproject.repository.words

import com.omerutkudemir.yazilimyapimiproject.model.UserKnownWords
import com.omerutkudemir.yazilimyapimiproject.model.Words

interface WordRepository {
    suspend fun getWords(): List<Words>
    suspend fun getKnownWords(userId: String): List<UserKnownWords>
}



