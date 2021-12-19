package com.tours.client.API

import com.tours.client.Endpoints
import com.tours.client.OnErrorCallback
import com.tours.client.requester.Request
import com.tours.entities.ShortTour
import com.tours.entities.Tour

class TourClient {
    companion object {
        private val getToursRequest =
            Request.get(Endpoints.TOURS, Array<ShortTour>::class.java)

        fun getTourById(tourId: Int) =
            Request.get(Endpoints.TOURS + "/${tourId}", Tour::class.java)

        //TODO empty response
        fun commentTour(tourId: Int) =
            Request.post(Endpoints.TOURS + "/${tourId}/addComment")

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
        ) {
            getTourById(tourId)(
                listOf(),
                onSuccess,
                onError
            )
        }

        fun commentTourById(
            tourId: Int,
            onSuccess: () -> Unit,
            onError: OnErrorCallback? = null
        ) {
            commentTour(tourId)(
                listOf(),
                onSuccess,
                onError
            )
        }
    }
}