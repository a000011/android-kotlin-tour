package com.tours.entities

import com.tours.entities.ToursEntities.Comment

data class Tour(
    val id: String,
    val title: String,
    val description: String,
    val img: String,
//    val updated_at: String,
//    val created_at: String,
    val comments: Array<Comment>
)