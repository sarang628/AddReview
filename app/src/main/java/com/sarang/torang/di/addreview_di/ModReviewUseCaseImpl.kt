package com.sarang.torang.di.addreview_di

import com.sarang.torang.addreview.uistate.Picture
import com.sarang.torang.addreview.usecase.ModReviewUseCase
import com.sarang.torang.repository.review.ReviewRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@InstallIn(SingletonComponent::class)
@Module
class ModReviewUseCaseImpl {
    @Provides
    fun provideModReviewUseCase(
        revoewRepository: ReviewRepository
    ): ModReviewUseCase {
        return object : ModReviewUseCase {
            override suspend fun invoke(
                reviewId: Int,
                contents: String,
                restaurantId: Int,
                rating: Float,
                files: List<Picture>?,
                uploadedImage: List<Int>?
            ) {
                revoewRepository.updateReview(
                    reviewId = reviewId,
                    contents = contents,
                    restaurantId = restaurantId,
                    rating = rating,
                    files = if (files != null)
                        files.filter { !it.isUploaded }.map { it.url }
                    else ArrayList(),
                    uploadedImage = uploadedImage ?: ArrayList()
                )
            }
        }
    }
}