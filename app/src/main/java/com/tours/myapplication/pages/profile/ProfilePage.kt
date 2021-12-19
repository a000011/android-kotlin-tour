package com.tours.myapplication.pages.profile

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.squareup.picasso.Picasso
import com.tours.entities.User
import com.tours.myapplication.CommentArgs
import com.tours.myapplication.R
import com.tours.myapplication.UserClient
import com.tours.myapplication.databinding.FragmentProfilePageBinding
import com.tours.myapplication.Comment

class ProfilePage : Fragment() {
    private lateinit var binding: FragmentProfilePageBinding

    @SuppressLint("SetTextI18n")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentProfilePageBinding.inflate(layoutInflater, container, false)

        Thread {
            UserClient.getUserInfo(
                { user ->
                    activity?.runOnUiThread {
                        Picasso.get().load(user.avatar).fit().placeholder(R.drawable.placeholder)
                            .into(binding.userAvatar)
                        binding.userName.text = "${user.firstname} ${user.lastname}"
                        val comments = parseUserCommentsIntoFragmets(user)
                        addCommentFragmentToList(*comments)
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

        return binding.root
    }

    fun parseUserCommentsIntoFragmets(user: User): Array<Comment> {
        return user.comments.map { comment ->
            return@map Comment.newInstance(
                CommentArgs(
                    user.avatar,
                    user.firstname,
                    user.lastname,
                    comment.content,
                    comment.mark.toFloat()
                )
            )
        }.toTypedArray()
    }

    private fun addCommentFragmentToList(vararg fragments: Comment) {
        fragments.forEach { fragment ->
            childFragmentManager.beginTransaction()
                .add(binding.commentList.id, fragment)
                .commit()
        }
    }
}
