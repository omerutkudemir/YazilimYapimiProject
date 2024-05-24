package com.omerutkudemir.yazilimyapimiproject.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

import com.omerutkudemir.yazilimyapimiproject.R
import com.omerutkudemir.yazilimyapimiproject.databinding.ActivityAnaEkranBinding
import com.omerutkudemir.yazilimyapimiproject.databinding.ActivityKelimeEkleBinding
import com.omerutkudemir.yazilimyapimiproject.databinding.ActivityMainBinding

class AnaEkranActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAnaEkranBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAnaEkranBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.kelimeEkleID.setOnClickListener()
        {
            val intent=Intent(applicationContext,KelimeEkleActivity::class.java)
            startActivity(intent)
        }

        binding.cikisYapID.setOnClickListener()
        {

            val intent=Intent(applicationContext,MainActivity::class.java)
            startActivity(intent)
        }

        binding.kolayID.setOnClickListener()
        {
            kolaySeviyeyeGit()
        }

        binding.istatistikID.setOnClickListener()
        {
            istatistik()
        }


    }

    private fun istatistik() {
        val intent=Intent(applicationContext,IstatistikActivity::class.java)
        startActivity(intent)
    }

    override fun onBackPressed() {


         //super.onBackPressed() // Bu satırı yorum satırına alarak geri butonunun işlevsiz olmasını sağlar
    }
    fun kolaySeviyeyeGit()
    {
        val intent=Intent(applicationContext,KolayActivity::class.java)
        startActivity(intent)

    }




}