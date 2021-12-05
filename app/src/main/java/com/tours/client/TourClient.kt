package com.tours.client

import com.tours.entities.Token
import com.tours.entities.Tour

class TourClient {
    companion object {
        private val getToursRequest = RequestFactory.get(Endpoints.TOURS, Array<Tour>::class.java)

        fun getTours(
            onSuccess: (tours: Array<Tour>) -> Unit,
            onError: OnErrorCallback? = null
        ) {
            getToursRequest(
                listOf(),
                onSuccess,
                onError
            )
        }
    }
}