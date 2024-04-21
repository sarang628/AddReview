package com.sarang.torang.di.addreview_di

import com.sarang.torang.BuildConfig
import com.sarang.torang.addreview.data.SelectRestaurantData
import com.sarang.torang.addreview.uistate.AddReviewUiState
import com.sarang.torang.addreview.uistate.Picture
import com.sarang.torang.data.remote.response.RemoteFeed
import com.sarang.torang.data.remote.response.RemoteRestaurant

fun RemoteRestaurant.toSelectRestaurantData(): SelectRestaurantData {
    return SelectRestaurantData(
        restaurantId = this.restaurantId,
        restaurantName = this.restaurantName,
        address = this.address
    )
}

fun RemoteFeed.toAddReviewUiSteate(): AddReviewUiState {
    return AddReviewUiState(
        reviewId = this.reviewId,
        list = this.pictures.map {
            Picture(
                pictureId = it.picture_id,
                url = BuildConfig.REVIEW_IMAGE_SERVER_URL + it.picture_url,
                isUploaded = true
            )
        },
        contents = this.contents,
        selectedRestaurant = SelectRestaurantData(
            restaurantId = this.restaurant.restaurantId,
            restaurantName = this.restaurant.restaurantName,
            address = this.restaurant.address
        ),
        isLoading = false
    )
}