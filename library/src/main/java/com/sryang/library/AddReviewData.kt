package com.sryang.library

data class AddReviewData(
    val pictures: List<String>,
    val contents: String,
    val restaurantId: Int
)
