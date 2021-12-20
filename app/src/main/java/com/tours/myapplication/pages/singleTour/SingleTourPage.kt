package com.tours.myapplication.pages.singleTour

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.squareup.picasso.Picasso
import com.tours.client.API.TourClient
import com.tours.myapplication.*
import com.tours.myapplication.databinding.FragmentSingleTourPageBinding
import com.tours.myapplication.pages.tourList.TourListDirections
import com.tours.entities.ToursEntities.Comment as CommentEntity

class SingleTourPage : Fragment() {
    private val args: SingleTourPageArgs by navArgs()

    private lateinit var binding: FragmentSingleTourPageBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSingleTourPageBinding.inflate(layoutInflater, container, false)

        Thread {
            TourClient.getTourById(
                args.tourId,
                { tour ->
                    activity?.runOnUiThread {
                        Picasso.get().load(tour.img).fit().placeholder(R.drawable.placeholder)
                            .into(binding.imageView)

                        binding.tourTitle.text = tour.title
                        binding.tourDescription.text = tour.description

                        val comments = parseToursIntoFragmets(tour.comments)
                        addCommentFragmentToList(*comments)
                        renderStars(tour.mark)
                    }
                }
            )
        }.start()

        binding.addCommentButton.setOnClickListener {
            activity?.runOnUiThread {
                val action = SingleTourPageDirections.actionSingleTourPageToAddCommentForm(args.tourId)
                this.findNavController().navigate(action)
            }
        }

        return binding.root
    }

    fun parseToursIntoFragmets(comments: Array<CommentEntity>): Array<Comment> {
        return comments.map { comment ->
            return@map Comment.newInstance(
                CommentArgs(
                    comment.user.avatar,
                    comment.user.firstname,
                    comment.user.lastname,
                    comment.content,
                    comment.mark
                )
            )
        }.toTypedArray()
    }

    private fun addCommentFragmentToList(vararg fragments: Comment) {
        fragments.forEach { fragment ->
            parentFragmentManager.beginTransaction()
                .add(binding.commentList.id, fragment)
                .commit()
        }
    }

    fun renderStars(mark: Float) {
        childFragmentManager.beginTransaction()
            .add(
                binding.tourMarkContainer.id, MarksView.newInstance(
                    MarksArgs(mark.toInt())
                )
            )
            .commit()
    }
}



