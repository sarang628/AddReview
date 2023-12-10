package com.sryang.addreview.usecase

import com.sryang.addreview.uistate.Picture

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