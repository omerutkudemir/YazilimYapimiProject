package com.omerutkudemir.yazilimyapimiproject.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.omerutkudemir.yazilimyapimiproject.R
import com.omerutkudemir.yazilimyapimiproject.databinding.ActivityAyarlarBinding
import com.omerutkudemir.yazilimyapimiproject.databinding.ActivityMainBinding
import com.omerutkudemir.yazilimyapimiproject.transfer.dataTransfer

class AyarlarActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAyarlarBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAyarlarBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val soruSyisi=binding.ayarlarID.text
        binding.onaylaID.setOnClickListener()
        {
            dataTransfer.sorusayisi=soruSyisi.toString()
        }


    }
}