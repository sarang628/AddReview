package com.sryang.addreview.data

data class AddReviewData(
    val pictures: List<String>,
    val contents: String,
    val restaurantId: Int
)
