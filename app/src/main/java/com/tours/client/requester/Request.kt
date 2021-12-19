package com.tours.client.requester

import com.tours.client.Method
import com.tours.client.RequestFactory
import com.tours.client.Requester
import com.tours.client.RequesterWithResponse

class Request {
    companion object {
        fun <ResultEntity> post(
            uri: String,
            fromJson: Class<ResultEntity>,
        ): RequesterWithResponse<ResultEntity> = RequestFactory.build(Method.POST, uri, fromJson)

        fun post(uri: String): Requester = RequestFactory.build(Method.POST, uri)


        fun <ResultEntity> get(
            uri: String,
            fromJson: Class<ResultEntity>,
        ): RequesterWithResponse<ResultEntity> = RequestFactory.build(Method.GET, uri, fromJson)

        fun get(uri: String): Requester = RequestFactory.build(Method.GET, uri)
    }
}