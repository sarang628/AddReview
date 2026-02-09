package com.sarang.torang.addreview.usecase

import android.content.Context

interface AddReviewUseCase {
    suspend fun invoke(
        contents: String,
        restaurantId: Int?,
        rating: Float,
        files: List<String>,
        context: Context
    )
}