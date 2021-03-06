package com.tours.myapplication

import com.tours.client.Endpoints
import com.tours.client.Method
import com.tours.client.OnErrorCallback
import com.tours.client.RequestFactory
import com.tours.client.requester.Request
import com.tours.entities.Status
import com.tours.entities.Token
import com.tours.entities.User

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
    companion object {
        private val sendLoginRequest = Request.post(Endpoints.LOGIN, Token::class.java)
        private val sendRegistrationRequest = Request.post(Endpoints.REG, Status::class.java)
        private val GetUserInfoRequest = Request.get(Endpoints.ME, User::class.java)


        fun login(
            loginCredentials: LoginCredentials,
            onSuccess: (token: Token) -> Unit,
            onError: OnErrorCallback? = null
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
            onError: OnErrorCallback? = null
        ) {
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

        fun getUserInfo(
            onSuccess: (user: User) -> Unit,
            onError: OnErrorCallback? = null
        ) {
            GetUserInfoRequest(
                listOf(),
                onSuccess,
                onError
            )
        }
    }
}
