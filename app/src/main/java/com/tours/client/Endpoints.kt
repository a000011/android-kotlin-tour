package com.tours.client

class Endpoints {
    companion object{
        const val HOST = "http://10.0.2.2:8000/api"

        const val LOGIN = "${HOST}/login"
        const val REG = "${HOST}/registration"
        const val TOURS = "${HOST}/tour"
        const val ME = "${HOST}/me"
    }
}