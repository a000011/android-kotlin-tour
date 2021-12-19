package com.tours.myapplication.pages.singleTour.views

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.tours.myapplication.databinding.FragmentAddCommentFormBinding
import com.tours.myapplication.databinding.FragmentSingleTourPageBinding

class AddCommentForm : Fragment() {
    private lateinit var binding: FragmentAddCommentFormBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAddCommentFormBinding.inflate(layoutInflater, container, false)

        return binding.root
    }
}