package com.tours.myapplication

import android.annotation.SuppressLint
import android.os.Bundle
import android.os.Parcelable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.tours.myapplication.databinding.FragmentCommentBinding
import com.tours.utils.BaseFragment
import kotlinx.parcelize.Parcelize

@Parcelize
data class CommentArgs(
//    val avatar: String,
    val firstname: String,
    val lastname: String,
    val content: String
) : Parcelable

class Comment : BaseFragment<CommentArgs>() {
    private lateinit var binding: FragmentCommentBinding

    companion object {
        val newInstance = getInitializer { Comment() }
    }

    @SuppressLint("SetTextI18n")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCommentBinding.inflate(layoutInflater, container, false)

        binding.userName.text = entity.firstname + " " + entity.lastname
        binding.content.text = entity.content

        return binding.root
    }
}