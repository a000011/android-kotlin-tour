package com.tours.client

import com.github.kittinunf.fuel.core.extensions.authentication
import com.github.kittinunf.fuel.httpGet
import com.tours.entities.Error
import com.github.kittinunf.fuel.httpPost
import com.github.kittinunf.result.Result
import com.google.gson.Gson
import com.tours.utils.ErrorMaper
import java.lang.Exception

typealias  OnErrorCallback = (errorMessage: Error, normalMessage: List<String>) -> Unit

typealias  Requester <ResultEntity> = (
    body: List<Pair<String, Any?>>,
    onSuccess: (token: ResultEntity) -> Unit,
    onError: OnErrorCallback?
) -> Unit

class RequestFactory {
    companion object {
        private lateinit var bearerToken: String

        fun setAuth(token: String){
            bearerToken = token
        }

        fun <ResultEntity> post(
            uri: String,
            toJson: Class<ResultEntity>,
        ): Requester<ResultEntity> {
            val requester: Requester<ResultEntity> = { body, onSuccess, onError ->
                val gson = Gson()

                val (_, response, result) = with (uri.httpPost(body)){
                    if (::bearerToken.isInitialized){
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
                            onSuccess(gson.fromJson(result.get(), toJson))
                        }
                    }
                }catch (e: Exception){
                    println(e)
                }

            }

            return requester
        }

        fun <ResultEntity> get(
            uri: String,
            fromJson: Class<ResultEntity>,
        ): Requester<ResultEntity> {
            val requester: Requester<ResultEntity> = { body, onSuccess, onError ->
                val gson = Gson()

                val (_, response, result) = with (uri.httpGet(body)){
                    if (::bearerToken.isInitialized){
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
                            onSuccess(gson.fromJson(result.get(), fromJson))
                        }
                    }
                }catch (e: Exception){
                    println(e)
                }
            }

            return requester
        }
    }

}
