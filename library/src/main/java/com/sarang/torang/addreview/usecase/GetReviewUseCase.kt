package com.sarang.torang.addreview.usecase

import com.sarang.torang.addreview.data.AddReviewData
import com.sarang.torang.addreview.uistate.AddReviewUiState

interface GetReviewUseCase {
    suspend fun invoke(reviewId: Int) : AddReviewUiState
}