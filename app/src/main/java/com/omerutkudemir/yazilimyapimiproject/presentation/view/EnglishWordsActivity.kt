package com.omerutkudemir.yazilimyapimiproject.presentation.view

import EngishWordsViewModel
import UserCheckViewModel
import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope

import com.omerutkudemir.yazilimyapimiproject.databinding.ActivityWordsBinding
import com.omerutkudemir.yazilimyapimiproject.model.UserKnownWords
import com.omerutkudemir.yazilimyapimiproject.model.WordsForAdd
import com.omerutkudemir.yazilimyapimiproject.servis.CompletelyKnownWorld
import com.omerutkudemir.yazilimyapimiproject.servis.UserWordPostReq


import com.omerutkudemir.yazilimyapimiproject.repository.dataTransfer

import com.squareup.picasso.Picasso
import kotlinx.coroutines.launch
import java.time.Duration
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class EnglishWordsActivity : AppCompatActivity() {
    val knownDate = LocalDateTime.now().format(DateTimeFormatter.ISO_DATE_TIME)
    private var index: Int = 0
    var questionCount:Int=10
    var known:Int? = 0
    var numberOfQuestion:Int=0
    var isKnownWordsFinished: Boolean = false

    private lateinit var viewmodelWords: EngishWordsViewModel
    private lateinit var viewModelCheck: UserCheckViewModel
    private lateinit var binding: ActivityWordsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWordsBinding.inflate(layoutInflater)
        setContentView(binding.root)



        viewmodelWords = ViewModelProvider(this).get(EngishWordsViewModel::class.java)
        viewmodelWords.pullWords()

        viewModelCheck = ViewModelProvider(this).get(UserCheckViewModel::class.java)
        println(dataTransfer.id.toString())

        viewmodelWords.knownWords()
        binding.sorulacakKelimeSyisiID.setOnClickListener()
        {
            val intent=Intent(applicationContext, SettingActivty::class.java)
            startActivity(intent)
        }
        if(dataTransfer.questionCount!=null)
        {
            questionCount= dataTransfer.questionCount!!.toInt()
        }


        setupObservers()
    }


    private fun setupObservers() {
        viewmodelWords.KnownWordList.observe(this, Observer { KnownWords ->
            if (!isKnownWordsFinished) {
                if (KnownWords.isNotEmpty() && index < KnownWords.size) {
                    // Bilinen kelimeler var, ve index hala bilinen listesinin boyutundan küçük
                    val words = KnownWords[index]
                    val dateOfKnownWords = LocalDateTime.parse(words.date, DateTimeFormatter.ISO_DATE_TIME)
                    val differenceOftime = Duration.between(dateOfKnownWords, LocalDateTime.now()).toHours()
                    val image=words.imageUri.toString()
                    if(words.count=="0")
                    {
                        known = known!! + 1
                    }

                    val timeRequired: Long = when (words.count) {
                        "0"->0L
                        "1" -> 24L
                        "2" -> 7 * 24L
                        "3" -> 4 * 7 * 24L
                        "4" -> 3 * 4 * 7 * 24L
                        "5" -> 6 * 4 * 7 * 24L
                        "6"->12*4*7*24L
                        else -> 0L
                    }

                    if (differenceOftime >= timeRequired) {
                        // Gereken saat kadar beklenmiş ise bu kelimeyi göster ve dinleme işlemini yap
                        binding.SorulanKelimeID.text = words.en
                        Picasso.get().load(words.imageUri).into(binding.imageView)
                        binding.SonrakiID.setOnClickListener {
                            checkKnownWords(words.tr, index, binding.cevapID.text.toString(),knownDate,image)
                            index++
                            setupObservers()
                        }
                    } else {
                        // Gereken saat kadar beklenmemiş, sonraki kelimeye geç
                        index++
                        setupObservers()
                    }
                } else {
                    // Bilinen kelimeler bitti, sıradaki kelimeleri göster
                    Toast.makeText(this, "daha önce bildikleriniz gösterildi yeni kelimelere geçildi ", Toast.LENGTH_LONG).show()
                    isKnownWordsFinished = true
                    setupObserversForRemainingWords()


                }
            } else {
                // Bilinen kelimeler tamamlandı, sıradaki kelimeleri göster
                Toast.makeText(this, "daha önce bildikleriniz gösterildi yeni kelimelere geçildi ", Toast.LENGTH_LONG).show()
                setupObserversForRemainingWords()


            }
        })
    }


    @SuppressLint("SuspiciousIndentation")
    private fun setupObserversForRemainingWords() {

        viewmodelWords.WordList.observe(this, Observer { wordList ->
            if (wordList.isNotEmpty() && index < wordList.size) {
                // Easy word listesindeki kelime var ve index hala boyuttan küçük
                val kelime = wordList[index]
                binding.SorulanKelimeID.text = kelime.en
                Picasso.get().load(kelime.imageUri).into(binding.imageView)

                binding.SonrakiID.setOnClickListener {
                val image=kelime.imageUri.toString()
                    if(numberOfQuestion<questionCount)
                    {
                        check(kelime.tr, index, binding.cevapID.text.toString(),image)
                        index++
                        setupObservers()
                        numberOfQuestion++
                    }
                    else{
                        Toast.makeText(this, "ayarlanan soru sayısına ulaşıldı ", Toast.LENGTH_LONG).show()
                    }

                }
            } else {
                // Tüm kelimeler gösterildi
                Toast.makeText(this, "Tüm kelimeler gösterildi", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun checkKnownWords(
        tr: String?,
        index: Int,
        cevap: String,
        bilnmeTarihi: String,
        image: String
    ) {
        viewmodelWords.KnownWordList.value?.let { knownWordList ->
            val currentWord = knownWordList.getOrNull(index)
            if (currentWord != null && tr?.lowercase()?.trim() == cevap.lowercase().trim()) {
                Toast.makeText(this, "Tebrikler doğru cevap", Toast.LENGTH_SHORT).show()
                val newCount = (currentWord.count?.toInt() ?: 0) + 1
                val newDate = LocalDateTime.now().format(DateTimeFormatter.ISO_DATE_TIME)

                if(newCount<7)
                {
                    val post = WordsForAdd(dataTransfer.id!!.toInt(), currentWord.en.toString(), currentWord.tr.toString(), newCount.toString(), bilnmeTarihi,image)
                    lifecycleScope.launch { UserWordPostReq(post).Update() }
                }
                else{
                    val post = UserKnownWords(dataTransfer.id!!.toInt(), currentWord.en.toString(), currentWord.tr.toString(), "7", bilnmeTarihi,image)
                    lifecycleScope.launch { CompletelyKnownWorld(post).KnownWordsAdd() }
                }
            } else {
                Toast.makeText(this, "Yanlış cevap", Toast.LENGTH_SHORT).show()

                val post = WordsForAdd(dataTransfer.id!!.toInt(), currentWord!!.en.toString(), currentWord.tr.toString(), "0", "0001-01-01T00:00:00",image)
                lifecycleScope.launch { UserWordPostReq(post).Update() }
            }
        }
    }


    private fun check(tr: String?, index: Int, answer: String, image: String) {
        viewmodelWords.WordList.value?.let { knownWordList ->
            val currentWord = knownWordList.getOrNull(index)
            if (currentWord != null && tr?.lowercase()?.trim() == answer.lowercase().trim()) {
                Toast.makeText(this, "Tebrikler doğru cevap", Toast.LENGTH_SHORT).show()
                val newCount = (currentWord.count?.toInt() ?: 0) + 1
                val post = WordsForAdd(dataTransfer.id!!.toInt(), currentWord.en.toString(), currentWord.tr.toString(), newCount.toString(), knownDate,image)
                lifecycleScope.launch { UserWordPostReq(post).post() }
            } else {
                Toast.makeText(this, "Yanlış cevap", Toast.LENGTH_SHORT).show()
                val post = WordsForAdd(dataTransfer.id!!.toInt(), currentWord!!.en.toString(), currentWord.tr.toString(), "0","0001-01-01T00:00:00",image)
                lifecycleScope.launch { UserWordPostReq(post).post() }
            }
        }
    }
}
