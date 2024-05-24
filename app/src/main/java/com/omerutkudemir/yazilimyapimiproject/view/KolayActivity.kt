package com.omerutkudemir.yazilimyapimiproject.view

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.omerutkudemir.yazilimyapimiproject.databinding.ActivityKolayBinding
import com.omerutkudemir.yazilimyapimiproject.model.UserKnownWords
import com.omerutkudemir.yazilimyapimiproject.model.WordPost
import com.omerutkudemir.yazilimyapimiproject.servis.TamamenBilinenKelimeler
import com.omerutkudemir.yazilimyapimiproject.servis.UserWordPost

import com.omerutkudemir.yazilimyapimiproject.viewModel.KolayViewModel
import com.omerutkudemir.yazilimyapimiproject.transfer.dataTransfer
import com.omerutkudemir.yazilimyapimiproject.viewModel.UyeKontrolViewModel
import com.squareup.picasso.Picasso
import kotlinx.coroutines.launch
import java.time.Duration
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class KolayActivity : AppCompatActivity() {
    val bilnmeTarihi = LocalDateTime.now().format(DateTimeFormatter.ISO_DATE_TIME)
    private var index: Int = 0
    var soruSayisi:Int=10
    var bilinenler:Int? = 0
    var sorulmaSayisi:Int=0
    var isBilinenListFinished: Boolean = false

    private lateinit var viewmodelEasy: KolayViewModel
    private lateinit var viewModelKontrol: UyeKontrolViewModel
    private lateinit var binding: ActivityKolayBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityKolayBinding.inflate(layoutInflater)
        setContentView(binding.root)



        viewmodelEasy = ViewModelProvider(this).get(KolayViewModel::class.java)
        viewmodelEasy.veriCekme()

        viewModelKontrol = ViewModelProvider(this).get(UyeKontrolViewModel::class.java)
        println(dataTransfer.id.toString())

        viewmodelEasy.bilinenKelimeler()
        binding.sorulacakKelimeSyisiID.setOnClickListener()
        {
            val intent=Intent(applicationContext,AyarlarActivity::class.java)
            startActivity(intent)
        }
        if(dataTransfer.sorusayisi!=null)
        {
            soruSayisi= dataTransfer.sorusayisi!!.toInt()
        }


        setupObservers()
    }


    private fun setupObservers() {
        viewmodelEasy.BilinenKolayKelimeler.observe(this, Observer { bilinenKelimeList ->
            if (!isBilinenListFinished) {
                if (bilinenKelimeList.isNotEmpty() && index < bilinenKelimeList.size) {
                    // Bilinen kelimeler var, ve index hala bilinen listesinin boyutundan küçük
                    val kelime = bilinenKelimeList[index]
                    val bilinenKelimeTarihi = LocalDateTime.parse(kelime.tarih, DateTimeFormatter.ISO_DATE_TIME)
                    val farkSaatler = Duration.between(bilinenKelimeTarihi, LocalDateTime.now()).toHours()
                    val image=kelime.imageUri.toString()
                    if(kelime.sayi=="0")
                    {
                        bilinenler = bilinenler!! + 1
                    }

                    val gerekenSaat: Long = when (kelime.sayi) {
                        "0"->0L
                        "1" -> 24L
                        "2" -> 7 * 24L
                        "3" -> 4 * 7 * 24L
                        "4" -> 3 * 4 * 7 * 24L
                        "5" -> 6 * 4 * 7 * 24L
                        "6"->12*4*7*24L
                        else -> 0L
                    }

                    if (farkSaatler >= gerekenSaat) {
                        // Gereken saat kadar beklenmiş, bu kelimeyi göster ve dinleme işlemini yap
                        binding.SorulanKelimeID.text = kelime.en
                        Picasso.get().load(kelime.imageUri).into(binding.imageView)
                        binding.SonrakiID.setOnClickListener {
                            kontrolEtBilinen(kelime.tr, index, binding.cevapID.text.toString(),bilnmeTarihi,image)
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
                    isBilinenListFinished = true
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

        viewmodelEasy.EasyWordsList.observe(this, Observer { kelimeList ->
            if (kelimeList.isNotEmpty() && index < kelimeList.size) {
                // Easy word listesindeki kelime var ve index hala boyuttan küçük
                val kelime = kelimeList[index]
                binding.SorulanKelimeID.text = kelime.en
                Picasso.get().load(kelime.imageUri).into(binding.imageView)

                binding.SonrakiID.setOnClickListener {
                val image=kelime.imageUri.toString()
                    if(sorulmaSayisi<soruSayisi)
                    {
                        kontrolEt(kelime.tr, index, binding.cevapID.text.toString(),image)
                        index++
                        setupObservers()
                        sorulmaSayisi++
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

    private fun kontrolEtBilinen(
        tr: String?,
        index: Int,
        cevap: String,
        bilnmeTarihi: String,
        image: String
    ) {
        viewmodelEasy.BilinenKolayKelimeler.value?.let { bilinenKelimeList ->
            val currentWord = bilinenKelimeList.getOrNull(index)
            if (currentWord != null && tr?.lowercase()?.trim() == cevap.lowercase().trim()) {
                Toast.makeText(this, "Tebrikler doğru cevap", Toast.LENGTH_SHORT).show()
                val yeniSayi = (currentWord.sayi?.toInt() ?: 0) + 1
                val yeniTarih = LocalDateTime.now().format(DateTimeFormatter.ISO_DATE_TIME)

                if(yeniSayi<7)
                {
                    val post = WordPost(dataTransfer.id!!.toInt(), currentWord.en.toString(), currentWord.tr.toString(), yeniSayi.toString(), bilnmeTarihi,image)
                    lifecycleScope.launch { UserWordPost(post).Update() }
                }
                else{
                    val post = UserKnownWords(dataTransfer.id!!.toInt(), currentWord.en.toString(), currentWord.tr.toString(), "7", bilnmeTarihi,image)
                    lifecycleScope.launch { TamamenBilinenKelimeler(post).BilinenPost() }
                }
            } else {
                Toast.makeText(this, "Yanlış cevap", Toast.LENGTH_SHORT).show()

                val post = WordPost(dataTransfer.id!!.toInt(), currentWord!!.en.toString(), currentWord.tr.toString(), "0", "0001-01-01T00:00:00",image)
                lifecycleScope.launch { UserWordPost(post).Update() }
            }
        }
    }


    private fun kontrolEt(tr: String?, index: Int, cevap: String, image: String) {
        viewmodelEasy.EasyWordsList.value?.let { bilinenKelimeList ->
            val currentWord = bilinenKelimeList.getOrNull(index)
            if (currentWord != null && tr?.lowercase()?.trim() == cevap.lowercase().trim()) {
                Toast.makeText(this, "Tebrikler doğru cevap", Toast.LENGTH_SHORT).show()
                val yeniSayi = (currentWord.sayi?.toInt() ?: 0) + 1
                val post = WordPost(dataTransfer.id!!.toInt(), currentWord.en.toString(), currentWord.tr.toString(), yeniSayi.toString(), bilnmeTarihi,image)
                lifecycleScope.launch { UserWordPost(post).post() }
            } else {
                Toast.makeText(this, "Yanlış cevap", Toast.LENGTH_SHORT).show()
                val post = WordPost(dataTransfer.id!!.toInt(), currentWord!!.en.toString(), currentWord.tr.toString(), "0","0001-01-01T00:00:00",image)
                lifecycleScope.launch { UserWordPost(post).post() }
            }
        }
    }
}
