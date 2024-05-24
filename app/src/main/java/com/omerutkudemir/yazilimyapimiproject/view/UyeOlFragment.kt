package com.omerutkudemir.yazilimyapimiproject.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.google.firebase.auth.FirebaseAuth
import com.omerutkudemir.yazilimyapimiproject.databinding.FragmentUyeOlBinding
import com.omerutkudemir.yazilimyapimiproject.servis.UyePost
import com.omerutkudemir.yazilimyapimiproject.viewModel.UyeKontrolViewModel
import kotlinx.coroutines.launch
import okhttp3.OkHttpClient


class UyeOlFragment : Fragment() {
    private lateinit var viewModel: UyeKontrolViewModel


    private lateinit var binding: FragmentUyeOlBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentUyeOlBinding.inflate(inflater, container, false)
        viewModel = ViewModelProvider(this).get(UyeKontrolViewModel::class.java)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        binding.UYEOLL.setOnClickListener() {
            val ad = binding.AD.text.toString()
            val soyad = binding.SOYAD.text.toString()
            val sifre = binding.SIFRE1.text.toString()
            val mail = binding.EPOSTAA.text.toString()

            Toast.makeText(context,"uye oldunuz",Toast.LENGTH_LONG).show()
            lifecycleScope.launch {
                kayitOl(ad, soyad, sifre, mail)
            }

        }
    }


     suspend fun kayitOl(ad: String, soyad: String, mail: String, sifre: String) {

        UyePost().post(ad, soyad, sifre, mail)


    }
}