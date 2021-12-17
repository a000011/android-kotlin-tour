package com.tours.entities.ToursEntities

data class Comment(
    val id: Int,
    val user_id: Int,
    val tour_id: Int,
    val avatar: String,
    val content: String,
    val mark: Float,
    val user: User,
)