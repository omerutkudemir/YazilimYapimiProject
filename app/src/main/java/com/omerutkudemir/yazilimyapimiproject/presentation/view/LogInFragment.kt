package com.omerutkudemir.yazilimyapimiproject.presentation.view

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


import com.omerutkudemir.yazilimyapimiproject.databinding.FragmentLogInBinding
import com.omerutkudemir.yazilimyapimiproject.repository.dataTransfer
import com.omerutkudemir.yazilimyapimiproject.presentation.viewModel.UserCheckViewModel
import kotlinx.coroutines.launch


class LogInFragment : Fragment() {



    private lateinit var viewModel: UserCheckViewModel

    private lateinit var binding: FragmentLogInBinding

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentLogInBinding.inflate(inflater, container, false)
        return binding.root




    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this).get(UserCheckViewModel::class.java)
        binding.GirisYapBut.setOnClickListener()
        {
            val email = binding.EPOSTAA.text.toString()
            val password = binding.SFREDOGRULMA.text.toString()
            LogIn(email,password)
        }

        binding.UYEOLL.setOnClickListener()
        {
            val action=LogInFragmentDirections.actionLogInFragmentToSignUpFragment()
            findNavController().navigate(action)
        }

        binding.sifreUnutmaID.setOnClickListener()
        {
            val intent = Intent(requireContext(), ForgetPassword::class.java)
            startActivity(intent)
        }


    }

    fun LogIn(email: String, password: String)
    {

        lifecycleScope.launch{
            viewModel.check()

        }

        viewModel.Users.observe(viewLifecycleOwner, Observer { UserList ->
            UserList?.let { list ->
                var signInSucces = false
                for (user in list) {
                    if (user.mail == email && user.password == password) {

                        dataTransfer.id=user.id.toString()
                        Toast.makeText(requireContext(), "Giriş başarılı", Toast.LENGTH_SHORT).show()
                        val intent = Intent(requireContext(), MainScreenActivity::class.java)
                        startActivity(intent)
                        signInSucces = true
                        break
                    }
                }
                if (!signInSucces) {
                    Toast.makeText(requireContext(), "Şifre veya Mail hatalı", Toast.LENGTH_SHORT).show()
                }
            }
        })


    }

}