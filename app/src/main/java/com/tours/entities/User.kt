package com.tours.entities

data class User(
    val firstname: String,
    val lastname: String,
    val avatar: String,
    val comments: List<ShortComment>
)

