package com.tours.myapplication

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.tours.myapplication.databinding.FragmentCommentBinding
import com.tours.myapplication.databinding.FragmentHomePageBinding

class Comment : Fragment() {
    private lateinit var binding: FragmentCommentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCommentBinding.inflate(layoutInflater, container, false)

        return binding.root
    }
}