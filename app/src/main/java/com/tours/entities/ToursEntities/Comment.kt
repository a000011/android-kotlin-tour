package com.tours.entities.ToursEntities

data class Comment(
    val id: Int,
    val user_id: Int,
    val tour_id: Int,
    val content: String,
    val mark: Int,
    val user: User,
)