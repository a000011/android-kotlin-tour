package com.tours.myapplication

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.tours.client.RequestFactory
import com.tours.myapplication.databinding.FragmentLoginBinding
import com.tours.myapplication.databinding.FragmentProfilePageBinding

class ProfilePage : Fragment() {
    private lateinit var binding: FragmentProfilePageBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentProfilePageBinding.inflate(layoutInflater, container, false)

        val her: TextView = binding.her


        Thread {
            UserClient.getUserInfo(
                { user ->
                    activity?.runOnUiThread {
                        her.text = user.firstname
                        Toast.makeText(activity,  user.toString(), Toast.LENGTH_SHORT).show()
                    }
                },
                { _, normalMessage ->
                    activity?.runOnUiThread {
                        normalMessage.forEach { err ->
                            Toast.makeText(activity,  err, Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            )
        }.start()
        return binding.root
    }

}