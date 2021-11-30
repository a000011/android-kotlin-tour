package com.tours.myapplication

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.tours.myapplication.databinding.FragmentLoginBinding

class Login : Fragment() {
    private lateinit var binding: FragmentLoginBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentLoginBinding.inflate(layoutInflater, container, false)
        binding.authButton.setOnClickListener {
            login()
        }
        return binding.root
    }

    fun login() {
        val login: String = binding.loginInput.text.toString()
        val password: String = binding.passwordInput.text.toString()

        val auth = Auth()
        Thread {
            auth.login(
                Credentials(login, password),
                { token ->
                    activity?.runOnUiThread{
                        this.findNavController().navigate(R.id.homePage)
                    }
                },
                { error ->
                    println(error)
                }
            )
        }.start()
    }
}