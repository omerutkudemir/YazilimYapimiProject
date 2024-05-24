package com.omerutkudemir.yazilimyapimiproject.view

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController

import com.omerutkudemir.yazilimyapimiproject.databinding.FragmentGirisYapBinding
import com.omerutkudemir.yazilimyapimiproject.model.WordPost
import com.omerutkudemir.yazilimyapimiproject.servis.UserWordPost
import com.omerutkudemir.yazilimyapimiproject.transfer.dataTransfer
import com.omerutkudemir.yazilimyapimiproject.viewModel.UyeKontrolViewModel
import kotlinx.coroutines.launch


class GirisYapFragment : Fragment() {



    private lateinit var viewModel: UyeKontrolViewModel

    private lateinit var binding: FragmentGirisYapBinding

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentGirisYapBinding.inflate(inflater, container, false)
        return binding.root




    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this).get(UyeKontrolViewModel::class.java)
        binding.GirisYapBut.setOnClickListener()
        {
            val email = binding.EPOSTAA.text.toString()
            val password = binding.SFREDOGRULMA.text.toString()
            girisYap(email,password)
        }

        binding.UYEOLL.setOnClickListener()
        {
            val action=GirisYapFragmentDirections.actionGirisYapFragmentToUyeOlFragment()
            findNavController().navigate(action)
        }

        binding.sifreUnutmaID.setOnClickListener()
        {
            val intent = Intent(requireContext(), SifreUnutmaActivity::class.java)
            startActivity(intent)
        }


    }

    fun girisYap(email: String, password: String)
    {

        lifecycleScope.launch{
            viewModel.kontrolEt()

        }

        viewModel.Uyeler.observe(viewLifecycleOwner, Observer { UyeListesi ->
            UyeListesi?.let { liste ->
                var girisBasarili = false
                for (Uye in liste) {
                    if (Uye.mail == email && Uye.sifre == password) {

                        dataTransfer.id=Uye.id.toString()
                        Toast.makeText(requireContext(), "Giriş başarılı", Toast.LENGTH_SHORT).show()
                        val intent = Intent(requireContext(), AnaEkranActivity::class.java)
                        startActivity(intent)
                        girisBasarili = true
                        break
                    }
                }
                if (!girisBasarili) {
                    Toast.makeText(requireContext(), "Şifre veya Mail hatalı", Toast.LENGTH_SHORT).show()
                }
            }
        })


    }

}