package com.omerutkudemir.yazilimyapimiproject.presentation.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle


import com.omerutkudemir.yazilimyapimiproject.databinding.ActivityMainScreenBinding

class MainScreenActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainScreenBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.kelimeEkleID.setOnClickListener()
        {
            val intent=Intent(applicationContext, AddNewWord::class.java)
            startActivity(intent)
        }

        binding.cikisYapID.setOnClickListener()
        {

            val intent=Intent(applicationContext, MainActivity::class.java)
            startActivity(intent)
        }

        binding.kolayID.setOnClickListener()
        {
            goToWords()
        }

        binding.istatistikID.setOnClickListener()
        {
            statistics()
        }


    }

    private fun statistics() {
        val intent=Intent(applicationContext, Viewstatistics::class.java)
        startActivity(intent)
    }


    fun goToWords()
    {
        val intent=Intent(applicationContext, EnglishWordsActivity::class.java)
        startActivity(intent)

    }




}