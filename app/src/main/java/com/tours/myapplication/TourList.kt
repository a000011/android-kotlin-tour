package com.tours.myapplication

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.tours.client.TourClient
import com.tours.entities.Tour
import com.tours.myapplication.databinding.FragmentTourListBinding

class TourList : Fragment() {
    private lateinit var binding: FragmentTourListBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentTourListBinding.inflate(layoutInflater, container, false)

        fetchAndRenderTours()

        return binding.root
    }

    private fun fetchAndRenderTours() {
        Thread {
            TourClient.getTours(
                { tours -> addTourFragmentToList(*parseToursIntoFragments(tours)) }
            )
        }.start()
    }

    private fun addTourFragmentToList(vararg fragments: Fragment) {

        fragments.forEach { fragment ->
            parentFragmentManager.beginTransaction()
                .add(binding.toursList.id, fragment)
                .commit()
        }

    }

    private fun parseToursIntoFragments(tours: Array<Tour>): Array<Fragment> {
        return tours.map { tour ->
            return@map SingleTour.newInstance(
                SingleTourArgs(
                    tour.title,
                    tour.description,
                    tour.img,
                )
            )
        }.toTypedArray()
    }

}