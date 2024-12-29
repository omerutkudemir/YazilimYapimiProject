package com.omerutkudemir.yazilimyapimiproject.presentation.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.omerutkudemir.yazilimyapimiproject.databinding.FragmentSignUpBinding

import com.omerutkudemir.yazilimyapimiproject.servis.UserPasswordRefreshService
import com.omerutkudemir.yazilimyapimiproject.presentation.viewModel.UserCheckViewModel
import kotlinx.coroutines.launch


class SignUpFragment : Fragment() {
    private lateinit var viewModel: UserCheckViewModel


    private lateinit var binding: FragmentSignUpBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentSignUpBinding.inflate(inflater, container, false)
        viewModel = ViewModelProvider(this).get(UserCheckViewModel::class.java)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        binding.UYEOLL.setOnClickListener() {
            val name = binding.AD.text.toString()
            val surname = binding.SOYAD.text.toString()
            val password = binding.SIFRE1.text.toString()
            val mail = binding.EPOSTAA.text.toString()

            Toast.makeText(context,"uye oldunuz",Toast.LENGTH_LONG).show()
            lifecycleScope.launch {
                signUp(name, surname, password, mail)
            }

        }
    }


     suspend fun signUp(name: String, surname: String, mail: String, password: String) {

        UserPasswordRefreshService().post(name, surname, password, mail)


    }
}