package com.sryang.addreview.usecase

interface AddReviewUseCase {
    suspend fun addReview(
        contents: String,
        restaurantId: Int,
        rating: Float,
        files: List<String>
    )
}