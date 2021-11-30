package com.tours.myapplication

import com.tours.client.RequestFactory
import com.tours.client.Endpoints
import com.tours.entities.Token
import com.tours.entities.Error

data class Credentials(
    val login: String,
    val password: String
)

class Auth {
    val sendLoginRequest = RequestFactory.post(Endpoints.LOGIN, Token::class.java)

    fun login(
        credentials: Credentials,
        onSuccess: (token: Token) -> Unit,
        onError: ((errorMessage: Error) -> Unit)? = null
    ) {

        sendLoginRequest(
            listOf(
                "login" to credentials.login,
                "password" to credentials.password,
            ),
            onSuccess,
            onError
        )
    }
}
