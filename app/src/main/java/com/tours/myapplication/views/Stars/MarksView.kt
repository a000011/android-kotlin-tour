package com.tours.myapplication

import android.os.Bundle
import android.os.Parcelable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.tours.myapplication.databinding.FragmentMarksViewBinding
import com.tours.utils.BaseFragment
import kotlinx.parcelize.Parcelize

@Parcelize
data class MarksArgs(
    val starCount: Int
) : Parcelable

const val MAX_STARS = 5

class MarksView : BaseFragment<MarksArgs>() {
    private lateinit var binding: FragmentMarksViewBinding


    companion object {
        val newInstance = getInitializer { MarksView() }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMarksViewBinding.inflate(layoutInflater, container, false)
        renderStars()
        return binding.root
    }

    private fun renderStars() {
        for (i in 1..MAX_STARS) {
            childFragmentManager.beginTransaction()
                .add(
                    binding.tourMark.id, MarkStar.newInstance(
                        StarArgs(i in 1..entity.starCount)
                    )
                )
                .commit()
        }
    }
}