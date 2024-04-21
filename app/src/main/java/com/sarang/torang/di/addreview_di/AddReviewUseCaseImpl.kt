package com.sarang.torang.di.addreview_di

import com.sarang.torang.addreview.usecase.AddReviewUseCase
import com.sarang.torang.repository.review.ReviewRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@InstallIn(SingletonComponent::class)
@Module
class AddReviewUseCaseImpl {
    @Provides
    fun provideReviewService(
        reviewRepository: ReviewRepository
    ): AddReviewUseCase {
        return object : AddReviewUseCase {
            override suspend fun invoke(
                contents: String,
                restaurantId: Int?,
                rating: Float,
                files: List<String>
            ) {
                reviewRepository.addReview(
                    contents = contents,
                    restaurantId = restaurantId,
                    rating = rating,
                    files = files
                )
            }
        }
    }
}