package com.tours.client

import com.github.kittinunf.fuel.core.Parameters
import com.github.kittinunf.fuel.core.Request
import com.github.kittinunf.fuel.core.extensions.authentication
import com.github.kittinunf.fuel.httpGet
import com.github.kittinunf.fuel.httpPost
import com.github.kittinunf.result.Result
import com.google.gson.Gson
import com.tours.entities.Error
import com.tours.utils.ErrorMaper

typealias  OnErrorCallback = (errorMessage: Error, normalMessage: List<String>) -> Unit

typealias  Requester <ResultEntity> = (
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

        fun <ResultEntity> post(
            uri: String,
            fromJson: Class<ResultEntity>? = null,
        ): Requester<ResultEntity> = build(Method.POST, uri, fromJson)

        fun <ResultEntity> get(
            uri: String,
            fromJson: Class<ResultEntity>? = null,
        ): Requester<ResultEntity> = build(Method.GET, uri, fromJson)

        private fun getHttpMethod(uri: String, method: Method): (params: Parameters) -> Request {
            return when (method) {
                Method.GET -> uri::httpGet
                Method.POST -> uri::httpPost
            }
        }

        fun <ResultEntity> build(
            method: Method,
            uri: String,
            toJson: Class<ResultEntity>? = null,
        ): Requester<ResultEntity> {
            val requester: Requester<ResultEntity> = { body, onSuccess, onError ->
                val gson = Gson()

                val (_, response, result) = with(getHttpMethod(uri, method)(body)) {
                    if (::bearerToken.isInitialized) {
                        return@with authentication().bearer(bearerToken).responseString()
                    }
                    return@with responseString()
                }

                try {
                    when (result) {
                        is Result.Failure -> {
                            if (onError !== null) {
                                val err = gson.fromJson(String(response.data), Error::class.java)
                                onError(err, ErrorMaper.mapErrors(err.errors))
                            }
                        }
                        is Result.Success -> {
                            if(toJson === null)
//                                onSuccess() TODO
                            else
                                onSuccess(gson.fromJson(result.get(), toJson))

                        }
                    }
                } catch (e: Exception) {
                    println(e)
                }
            }

            return requester
        }


    }

}
