package com.sryang.addreview.usecase

import com.sryang.addreview.data.AddReviewData
import com.sryang.addreview.uistate.AddReviewUiState

interface GetReviewUseCase {
    suspend fun invoke(reviewId: Int) : AddReviewUiState
}