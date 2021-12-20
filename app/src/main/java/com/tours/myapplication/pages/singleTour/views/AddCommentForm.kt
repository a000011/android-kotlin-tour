package com.tours.myapplication.pages.singleTour.views

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.tours.client.API.CommentData
import com.tours.client.API.TourClient
import com.tours.myapplication.databinding.FragmentAddCommentFormBinding
import com.tours.myapplication.pages.singleTour.SingleTourPageArgs
import com.tours.myapplication.pages.tourList.TourListDirections

class AddCommentForm : Fragment() {
    private lateinit var binding: FragmentAddCommentFormBinding

    private val args: SingleTourPageArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAddCommentFormBinding.inflate(layoutInflater, container, false)

        binding.addCommentButton.setOnClickListener {
            login()
        }

        return binding.root
    }

    fun login() {
        var mark: Int = 0
        if (!binding.mark.text.isBlank())
            mark = binding.mark.text.toString().toInt()

        val content: String = binding.content.text.toString()

        Thread {
            TourClient.commentTourById(
                args.tourId,
                CommentData(mark, content),
                {
                    activity?.runOnUiThread {
                        val action = AddCommentFormDirections.actionAddCommentFormToSingleTourPage(args.tourId)
                        this.findNavController().navigate(action)
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