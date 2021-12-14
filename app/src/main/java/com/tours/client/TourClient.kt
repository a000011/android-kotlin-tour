package com.tours.client

import com.tours.entities.ShortTour
import com.tours.entities.Tour

class TourClient {
    companion object {
        private val getToursRequest = RequestFactory.get(Endpoints.TOURS, Array<ShortTour>::class.java)

        fun getTours(
            onSuccess: (tours: Array<ShortTour>) -> Unit,
            onError: OnErrorCallback? = null
        ) {
            getToursRequest(
                listOf(),
                onSuccess,
                onError
            )
        }
        fun getTourById(
            tourId: Int,
            onSuccess: (tour: Tour) -> Unit,
            onError: OnErrorCallback? = null
        ){
            val getTourById = RequestFactory.get(Endpoints.TOURS + "/${tourId}", Tour::class.java)
            getTourById(
                listOf(),
                onSuccess,
                onError
            )
        }
    }
}