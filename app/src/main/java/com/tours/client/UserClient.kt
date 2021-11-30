package com.tours.myapplication

import com.tours.client.RequestFactory
import com.tours.client.Endpoints
import com.tours.entities.Token
import com.tours.entities.Error
import com.tours.entities.Status

data class LoginCredentials(
    val login: String,
    val password: String
)

data class RegistrationCredentials(
    val firstname: String,
    val lastname: String,
    val login: String,
    val password: String
)

class UserClient {
    val sendLoginRequest = RequestFactory.post(Endpoints.LOGIN, Token::class.java)
    val sendRegistrationRequest = RequestFactory.post(Endpoints.REG, Status::class.java)

    fun login(
        loginCredentials: LoginCredentials,
        onSuccess: (token: Token) -> Unit,
        onError: ((errorMessage: Error) -> Unit)? = null
    ) {
        sendLoginRequest(
            listOf(
                "login" to loginCredentials.login,
                "password" to loginCredentials.password,
            ),
            onSuccess,
            onError
        )
    }



    fun registrate(
        registrationCredentials: RegistrationCredentials,
        onSuccess: (status: Status) -> Unit,
        onError: ((errorMessage: Error) -> Unit)? = null
    ){
        sendRegistrationRequest(
            listOf(
                "firstname" to registrationCredentials.firstname,
                "lastname" to registrationCredentials.lastname,
                "login" to registrationCredentials.login,
                "password" to registrationCredentials.password,
            ),
            onSuccess,
            onError
        )
    }
}
