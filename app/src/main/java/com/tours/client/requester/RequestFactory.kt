package com.tours.client

import com.github.kittinunf.fuel.core.Parameters
import com.github.kittinunf.fuel.core.Request
import com.github.kittinunf.fuel.core.ResponseResultOf
import com.github.kittinunf.fuel.core.extensions.authentication
import com.github.kittinunf.fuel.httpGet
import com.github.kittinunf.fuel.httpPost
import com.github.kittinunf.result.Result
import com.google.gson.Gson
import com.tours.entities.Error
import com.tours.utils.ErrorMaper

typealias  OnErrorCallback = (errorMessage: Error, normalMessage: List<String>) -> Unit

typealias  Requester = (
    body: Parameters,
    onSuccess: () -> Unit,
    onError: OnErrorCallback?
) -> Unit

typealias  RequesterWithResponse <ResultEntity> = (
    body: Parameters,
    onSuccess: (entity: ResultEntity) -> Unit,
    onError: OnErrorCallback?
) -> Unit


enum class Method {
    POST,
    GET
}

class RequestFactory {
    companion object {
        private lateinit var bearerToken: String

        fun setAuth(token: String) {
            bearerToken = token
        }

        private fun getHttpMethod(uri: String, method: Method): (params: Parameters) -> Request {
            return when (method) {
                Method.GET -> uri::httpGet
                Method.POST -> uri::httpPost
            }
        }

        fun build(
            method: Method,
            uri: String,
        ): Requester {
            val requester: Requester = { body, onSuccess, onError ->
                val gson = Gson()

                sendRequest(
                    uri, method, body, { (_, response) ->
                        if (onError !== null) {
                            val err = gson.fromJson(String(response.data), Error::class.java)
                            onError(err, ErrorMaper.mapErrors(err.errors))
                        }
                    },
                    { onSuccess() }
                )

            }

            return requester
        }

        fun <ResultEntity> build(
            method: Method,
            uri: String,
            toJson: Class<ResultEntity>,
        ): RequesterWithResponse<ResultEntity> {
            val requester: RequesterWithResponse<ResultEntity> = { body, onSuccess, onError ->
                val gson = Gson()

                sendRequest(uri, method, body, { (_, response) ->
                    if (onError !== null) {
                        val err = gson.fromJson(String(response.data), Error::class.java)
                        onError(err, ErrorMaper.mapErrors(err.errors))
                    }
                },
                    { (_, _, result) ->
                        onSuccess(gson.fromJson(result.get(), toJson))
                    }
                );

            }

            return requester
        }

        private fun sendRequest(
            uri: String,
            method: Method,
            body: Parameters,
            onFailure: (r: ResponseResultOf<String>) -> Unit,
            onSuccess: (r: ResponseResultOf<String>) -> Unit
        ) {
            val reqResult = with(getHttpMethod(uri, method)(body)) {
                if (::bearerToken.isInitialized) {
                    return@with authentication().bearer(bearerToken).responseString()
                }
                return@with responseString()
            }

            val (_, _, result) = reqResult;

            try {
                when (result) {
                    is Result.Failure -> onFailure(reqResult)
                    is Result.Success -> onSuccess(reqResult)
                }
            } catch (e: Exception) {
                println(e)
            }
        }


    }

}
