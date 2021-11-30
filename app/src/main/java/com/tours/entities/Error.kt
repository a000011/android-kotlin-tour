package com.tours.entities

data class Error(
    val code: Int,
    val errors: Map<String, String>
)