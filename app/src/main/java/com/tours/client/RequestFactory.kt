package com.tours.client

import com.tours.entities.Error
import com.github.kittinunf.fuel.httpPost
import com.github.kittinunf.result.Result
import com.google.gson.Gson
import com.tours.utils.ErrorMaper

typealias  OnErrorCallback = (errorMessage: Error, normalMessage: List<String>) -> Unit

typealias  Requester <ResultEntity> = (
    body: List<Pair<String, Any?>>,
    onSuccess: (token: ResultEntity) -> Unit,
    onError: OnErrorCallback?
) -> Unit

class RequestFactory {
    companion object {
        fun <ResultEntity> post(
            uri: String,
            toJson: Class<ResultEntity>,
        ): Requester<ResultEntity> {
            val requester: Requester<ResultEntity> = { body, onSuccess, onError ->
                val gson = Gson()

                val (_, response, result) = uri.httpPost(body).responseString()

                when (result) {
                    is Result.Failure -> {
                        if (onError !== null) {
                            val err = gson.fromJson(String(response.data), Error::class.java)
                            onError(err, ErrorMaper.mapErrors(err.errors))
                        }
                    }
                    is Result.Success -> {
                        onSuccess(gson.fromJson(result.get(), toJson))
                    }
                }
            }

            return requester
        }
    }

}
