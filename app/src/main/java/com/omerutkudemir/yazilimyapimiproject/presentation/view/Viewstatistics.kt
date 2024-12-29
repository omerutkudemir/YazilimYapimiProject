package com.omerutkudemir.yazilimyapimiproject.presentation.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider

import com.omerutkudemir.yazilimyapimiproject.databinding.ActivityStatisticBinding
import com.omerutkudemir.yazilimyapimiproject.presentation.viewModel.EngishWordsViewModel

class Viewstatistics : AppCompatActivity() {
    private var knownWordsCount: Int = 0

    private var knownRatio: Int = 0
    private lateinit var binding: ActivityStatisticBinding
    private lateinit var viewmodelEasy: EngishWordsViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityStatisticBinding.inflate(layoutInflater)
        setContentView(binding.root)
        viewmodelEasy = ViewModelProvider(this).get(EngishWordsViewModel::class.java)
        viewmodelEasy.KnownWordList
        oranGozlemle()
    }

    private fun oranGozlemle() {
        viewmodelEasy.KnownWordList.observe(this, Observer { knowns ->
            knownWordsCount = 0 // Her gözlemlemede sıfırlanmalı
            knowns.forEach { bilinen ->
                if (bilinen.count != "0") {
                    knownWordsCount++
                }
            }
            // Bilinme oranını hesapla
            knownRatio = if (knowns.isNotEmpty()) {
                (knownWordsCount * 100) / knowns.size
            } else {
                0
            }
            // UI güncelle
            binding.bilmeID.append(" " + knownWordsCount.toString())
            binding.oranID.append(" yüzde" + knownRatio.toString())

        })
    }
}