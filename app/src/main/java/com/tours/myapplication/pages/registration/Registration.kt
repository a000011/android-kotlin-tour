package com.tours.myapplication.pages.registration

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.tours.myapplication.R
import com.tours.myapplication.RegistrationCredentials
import com.tours.myapplication.UserClient
import com.tours.myapplication.databinding.FragmentRegistrationBinding

class Registration : Fragment() {
    private lateinit var binding: FragmentRegistrationBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentRegistrationBinding.inflate(layoutInflater, container, false)

        binding.toLoginButton.setOnClickListener {
            this.findNavController().navigate(R.id.login)
        }

        binding.registrationButton.setOnClickListener {
            if (binding.newPasswordInput.text.toString() == binding.newPasswordConfirmInput.text.toString()) {
                registrate()
            }
        }
        return binding.root
    }

    fun registrate() {
        val firstname: String = binding.firstname.text.toString()
        val secondname: String = binding.secondname.text.toString()
        val login: String = binding.newLoginInput.text.toString()
        val password: String = binding.newPasswordInput.text.toString()

        Thread {
            UserClient.registrate(
                RegistrationCredentials(
                    firstname,
                    secondname,
                    login,
                    password,
                ),
                {
                    activity?.runOnUiThread {
                        Toast.makeText(
                            activity,
                            "Вы успешно зарегестрировались",
                            Toast.LENGTH_SHORT
                        ).show()
                        this.findNavController().navigate(R.id.login)
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