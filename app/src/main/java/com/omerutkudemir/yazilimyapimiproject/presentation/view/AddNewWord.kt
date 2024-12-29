package com.omerutkudemir.yazilimyapimiproject.presentation.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.lifecycleScope
import com.omerutkudemir.yazilimyapimiproject.databinding.ActivityAddWordsBinding

import com.omerutkudemir.yazilimyapimiproject.model.WordsForAdd
import com.omerutkudemir.yazilimyapimiproject.servis.UserWordPostReq
import com.omerutkudemir.yazilimyapimiproject.repository.dataTransfer
import kotlinx.coroutines.launch

class AddNewWord : AppCompatActivity() {
    private lateinit var binding: ActivityAddWordsBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddWordsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.kolayId.setOnClickListener()
        {
            val ing=binding.ingilizceID.text.toString()
            val tur=binding.turkceID.text.toString()
            val image=binding.resimUriID.text.toString()

            val post=WordsForAdd(dataTransfer.id!!.toInt(),ing,tur,"0","0",image)
            lifecycleScope.launch { UserWordPostReq(post).add("basicwords") }




        }


    }
}