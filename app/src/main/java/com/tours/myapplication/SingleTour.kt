package com.tours.myapplication

import android.content.Context
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
    val title: String,
    val description: String,
    val img: String
) : Parcelable

class SingleTour : BaseFragment<SingleTourArgs>() {
    private lateinit var binding: FragmentSingleTourBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentSingleTourBinding.inflate(layoutInflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val args = getArgs()
        Thread{
            val img = Picasso.get().load(args.img)

            activity?.runOnUiThread{
                img.into(binding.imageView)
            }

        }.start()

        binding.tourTitle.text = args.title
        binding.tourDescription.text = args.description.take(100) + "..."
    }


    companion object {
        fun newInstance(args: SingleTourArgs): SingleTour{
            val her = SingleTour()
            val bundle = Bundle()
            bundle.putParcelable(ARGS_KEY, args)
            her.arguments = bundle
            return her
        }
    }
}