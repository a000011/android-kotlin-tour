package com.tours.myapplication.pages.login

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.tours.client.RequestFactory
import com.tours.myapplication.LoginCredentials
import com.tours.myapplication.R
import com.tours.myapplication.UserClient
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
        binding.toRegistrationButton.setOnClickListener {
            this.findNavController().navigate(R.id.registration)
        }
        return binding.root
    }

    fun login() {
        val login: String = binding.loginInput.text.toString()
        val password: String = binding.passwordInput.text.toString()

        Thread {
            UserClient.login(
                LoginCredentials(login, password),
                { res ->
                    RequestFactory.setAuth(res.token)
                    activity?.runOnUiThread {
                        this.findNavController().navigate(R.id.tourList)
                    }
                },
                { _, normalMessage ->
                    activity?.runOnUiThread {
                        normalMessage.forEach { err ->
                            Toast.makeText(activity, err, Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            )
        }.start()
    }
}