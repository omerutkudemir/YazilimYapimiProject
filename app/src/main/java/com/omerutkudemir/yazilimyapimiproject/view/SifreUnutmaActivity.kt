package com.omerutkudemir.yazilimyapimiproject.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.omerutkudemir.yazilimyapimiproject.databinding.ActivitySifreUnutmaBinding
import com.omerutkudemir.yazilimyapimiproject.model.UserSifreYenile
import com.omerutkudemir.yazilimyapimiproject.servis.UyePost
import com.omerutkudemir.yazilimyapimiproject.viewModel.UyeKontrolViewModel
import kotlinx.coroutines.launch

class SifreUnutmaActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySifreUnutmaBinding
    private lateinit var viewModelKontrol: UyeKontrolViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySifreUnutmaBinding.inflate(layoutInflater)
        setContentView(binding.root)
        viewModelKontrol = ViewModelProvider(this).get(UyeKontrolViewModel::class.java)

        lifecycleScope.launch {
            viewModelKontrol.kontrolEt()
        }

        binding.yeniSifreID.setOnClickListener {
            uyeGozlemle()
        }
    }

    private fun uyeGozlemle() {
        viewModelKontrol.Uyeler.observe(this, Observer { uyeListesi ->
            uyeListesi?.let { liste ->
                val mail = binding.mailID.text.toString()
                val yeniSifre = binding.sifreID.text.toString()

                // Kullanıcıyı listede bul
                val uye = liste.find { it.mail == mail }
                if (uye != null) {
                    val yenilenenPost = UserSifreYenile(uye.ad, uye.mail, yeniSifre)
                    lifecycleScope.launch {
                        UyePost().Update(yenilenenPost)
                    }
                    Toast.makeText(this, "Şifreniz değiştirildi", Toast.LENGTH_LONG).show()
                } else {
                    Toast.makeText(this, "Böyle bir mail bulunamadı", Toast.LENGTH_LONG).show()
                }
            }
        })
    }
}
