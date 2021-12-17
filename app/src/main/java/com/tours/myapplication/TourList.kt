package com.tours.myapplication

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.tours.client.TourClient
import com.tours.entities.ShortTour
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
                { tours ->
                    val toursFragments = parseToursIntoFragments(tours)
                    addTourFragmentToList(*toursFragments)
                    attachOnClickListeners(toursFragments)
                }
            )
        }.start()
    }

    private fun addTourFragmentToList(vararg fragments: SingleTour) {
        fragments.forEach { fragment ->
            parentFragmentManager.beginTransaction()
                .add(binding.toursList.id, fragment)
                .commit()
        }
    }

    private fun parseToursIntoFragments(tours: Array<ShortTour>): Array<SingleTour> {
        return tours.map { tour ->
            return@map SingleTour.newInstance(
                SingleTourArgs(
                    tour.id,
                    tour.title,
                    tour.description,
                    tour.img,
                    tour.mark
                )
            )
        }.toTypedArray()
    }

    private fun attachOnClickListeners(tours: Array<SingleTour>): Unit {
        tours.forEach { tour ->
            tour.setOnClickListener {
                gotoTourById(tour.entity.tourId.toInt())
            }
        }
    }

    private fun gotoTourById(tourId: Int) {
        activity?.runOnUiThread {
            val action = TourListDirections.actionTourListToSingleTourPage(tourId)
            this.findNavController().navigate(action)
        }
    }
}