package com.tours.myapplication

import android.annotation.SuppressLint
import android.os.Bundle
import android.os.Parcelable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.squareup.picasso.Picasso
import com.tours.myapplication.databinding.FragmentSingleTourBinding
import com.tours.utils.BaseFragment
import kotlinx.parcelize.Parcelize

@Parcelize
data class SingleTourArgs(
    val tourId: Number,
    val title: String,
    val description: String,
    val img: String
) : Parcelable

class SingleTour : BaseFragment<SingleTourArgs>() {
    private lateinit var binding: FragmentSingleTourBinding
    private lateinit var onClickListener: View.OnClickListener

    companion object {
        val newInstance = getInitializer { SingleTour() }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSingleTourBinding.inflate(layoutInflater, container, false)
        if(::onClickListener.isInitialized) binding.root.setOnClickListener(onClickListener)
        return binding.root
    }

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val img = Picasso.get().load(entity.img).fit().placeholder(R.drawable.placeholder)
            .into(binding.imageView)

        binding.tourTitle.text = entity.title

        binding.tourDescription.text = "${entity.description.take(100)}..."

    }

    fun setOnClickListener(listener: View.OnClickListener) {
        if(::binding.isInitialized){
            binding.root.setOnClickListener(listener)
        }
        onClickListener = listener
    }
}
