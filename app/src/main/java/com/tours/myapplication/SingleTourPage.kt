package com.tours.myapplication

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.squareup.picasso.Picasso
import com.tours.client.TourClient
import com.tours.entities.ShortTour
import com.tours.myapplication.databinding.FragmentSingleTourPageBinding
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
                        val img = Picasso.get().load(tour.img).fit().placeholder(R.drawable.placeholder)
                            .into(binding.imageView)

                        binding.tourTitle.text = tour.title
                        binding.tourDescription.text = tour.description

                        val comments = parseToursIntoFragmets(tour.comments)
                        addCommentFragmentToList(*comments)
                    }
                }
            )
        }.start()

        return binding.root
    }

    fun parseToursIntoFragmets(comments: Array<CommentEntity>): Array<Comment>{
        return comments.map { comment ->
            return@map Comment.newInstance(
                CommentArgs(
//                    comment.user.avatar,
                    comment.user.firstname,
                    comment.user.lastname,
                    comment.content,
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
}



