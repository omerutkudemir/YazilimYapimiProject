package com.omerutkudemir.yazilimyapimiproject.repository.words

import com.omerutkudemir.yazilimyapimiproject.model.UserKnownWords
import com.omerutkudemir.yazilimyapimiproject.model.Words

class GetWordsUseCase(private val wordRepository: WordRepository) {
    suspend operator fun invoke(): List<Words> {
        return wordRepository.getWords()
    }
}

class GetKnownWordsUseCase(private val wordRepository: WordRepository) {
    suspend operator fun invoke(userId: String): List<UserKnownWords> {
        return wordRepository.getKnownWords(userId)
    }
}
