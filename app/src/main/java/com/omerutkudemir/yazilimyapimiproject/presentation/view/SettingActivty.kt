package com.omerutkudemir.yazilimyapimiproject.presentation.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

import com.omerutkudemir.yazilimyapimiproject.databinding.ActivitySettingsBinding
import com.omerutkudemir.yazilimyapimiproject.repository.dataTransfer

class SettingActivty : AppCompatActivity() {
    private lateinit var binding: ActivitySettingsBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySettingsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val questionCount=binding.ayarlarID.text
        binding.onaylaID.setOnClickListener()
        {
            dataTransfer.questionCount=questionCount.toString()
        }


    }
}