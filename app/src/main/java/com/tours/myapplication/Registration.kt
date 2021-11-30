package com.tours.myapplication

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.tours.myapplication.databinding.FragmentRegistrationBinding

class Registration : Fragment() {
    private lateinit var binding: FragmentRegistrationBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentRegistrationBinding.inflate(layoutInflater, container, false)

        binding.toLoginButton.setOnClickListener{
            this.findNavController().navigate(R.id.login)
        }

        binding.registrationButton.setOnClickListener{
            if (binding.newPasswordInput.text.toString() == binding.newPasswordConfirmInput.text.toString()){
                registrate()
            }
        }
        return binding.root
    }

    fun registrate(){
        val firstname: String = binding.firstname.text.toString()
        val secondname: String = binding.secondname.text.toString()
        val login: String = binding.newLoginInput.text.toString()
        val password: String = binding.newPasswordInput.text.toString()

        val auth = UserClient()
        Thread {
            auth.registrate(
                RegistrationCredentials(
                    firstname,
                    secondname,
                    login,
                    password,
                ),
                { status ->
                    println(status)
                    activity?.runOnUiThread{
                        this.findNavController().navigate(R.id.login)
                    }
                },
                { error ->
                    println("err")

                    println(error)
                }
            )
        }.start()
    }
}