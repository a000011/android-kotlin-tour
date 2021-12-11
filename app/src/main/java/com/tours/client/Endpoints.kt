package com.tours.client

class Endpoints {
    companion object{
        val HOST = "http://10.0.2.2:8000/api"

        val LOGIN = "${HOST}/login"
        val REG = "${HOST}/registration"
        val TOURS = "${HOST}/tour"
        val ME = "${HOST}/me"
    }
}