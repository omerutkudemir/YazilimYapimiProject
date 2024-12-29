package com.omerutkudemir.yazilimyapimiproject.presentation.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.omerutkudemir.yazilimyapimiproject.databinding.ActivityForgetPasswordBinding

import com.omerutkudemir.yazilimyapimiproject.model.userPasswordrRefresh
import com.omerutkudemir.yazilimyapimiproject.servis.UserPasswordRefreshService
import com.omerutkudemir.yazilimyapimiproject.presentation.viewModel.UserCheckViewModel
import kotlinx.coroutines.launch

class ForgetPassword : AppCompatActivity() {
    private lateinit var binding: ActivityForgetPasswordBinding
    private lateinit var viewModelKontrol: UserCheckViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityForgetPasswordBinding.inflate(layoutInflater)
        setContentView(binding.root)
        viewModelKontrol = ViewModelProvider(this).get(UserCheckViewModel::class.java)

        lifecycleScope.launch {
            viewModelKontrol.check()
        }

        binding.yeniSifreID.setOnClickListener {
            observeUser()
        }
    }

    private fun observeUser() {
        viewModelKontrol.Users.observe(this, Observer { uyeListesi ->
            uyeListesi?.let { liste ->
                val mail = binding.mailID.text.toString()
                val newPassword = binding.sifreID.text.toString()

                // Kullanıcıyı listede bul
                val user = liste.find { it.mail == mail }
                if (user != null) {
                    val refreshed = userPasswordrRefresh(user.name, user.mail, newPassword)
                    lifecycleScope.launch {
                        UserPasswordRefreshService().Update(refreshed)
                    }
                    Toast.makeText(this, "Şifreniz değiştirildi", Toast.LENGTH_LONG).show()
                } else {
                    Toast.makeText(this, "Böyle bir mail bulunamadı", Toast.LENGTH_LONG).show()
                }
            }
        })
    }
}
