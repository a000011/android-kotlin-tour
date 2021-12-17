package com.tours.myapplication

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.tours.myapplication.databinding.FragmentMarkStarBinding


class MarkStar : Fragment() {
    private lateinit var binding: FragmentMarkStarBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMarkStarBinding.inflate(layoutInflater, container, false)

        return binding.root
    }
}