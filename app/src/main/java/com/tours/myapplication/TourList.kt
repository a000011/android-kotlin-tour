package com.tours.myapplication

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.tours.client.TourClient
import com.tours.myapplication.databinding.FragmentLoginBinding
import com.tours.myapplication.databinding.FragmentTourListBinding

class TourList : Fragment() {
    private lateinit var binding: FragmentTourListBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentTourListBinding.inflate(layoutInflater, container, false)

        Thread{
            TourClient.getTours({
                tours ->
                    tours.forEach {
                        herVoRTu -> println(herVoRTu.toString())
                    }
            })
        }.start()

        return binding.root
    }

}