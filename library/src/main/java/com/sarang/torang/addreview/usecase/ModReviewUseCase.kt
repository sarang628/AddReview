package com.sarang.torang.addreview.usecase

import com.sarang.torang.addreview.uistate.Picture

interface ModReviewUseCase {
    suspend fun invoke(
        reviewId : Int,
        contents: String,
        restaurantId: Int,
        rating: Float,
        files: List<Picture>? = null,
        uploadedImage: List<Int>? = null
    )
}