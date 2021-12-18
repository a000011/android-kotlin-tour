package com.tours.myapplication

import android.os.Bundle
import android.os.Parcelable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.tours.myapplication.databinding.FragmentMarkStarBinding
import com.tours.utils.BaseFragment
import kotlinx.parcelize.Parcelize

@Parcelize
data class StarArgs(
    val isVisible: Boolean,
) : Parcelable


class MarkStar : BaseFragment<StarArgs>() {
    private lateinit var binding: FragmentMarkStarBinding

    companion object {
        val newInstance = getInitializer { MarkStar() }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMarkStarBinding.inflate(layoutInflater, container, false)

        binding.starView.visibility = if (entity.isVisible) View.VISIBLE else View.INVISIBLE

        return binding.root
    }
}