package com.sryang.addreview.usecase

interface AddReviewUseCase {
    suspend fun invoke(
        contents: String,
        restaurantId: Int,
        rating: Float,
        files: List<String>
    )
}