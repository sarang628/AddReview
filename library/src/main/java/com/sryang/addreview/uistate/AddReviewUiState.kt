package com.sryang.addreview.uistate

import com.sryang.addreview.data.SelectRestaurantData

data class AddReviewUiState(
    val reviewId: Int? = null,
    val isProgress: Boolean = false,
    val list: List<Picture>? = null,
    val contents: String = "",
    val rating: Float = 3.0f,
    val selectedRestaurant: SelectRestaurantData? = null,
    val errorMsg: String? = null,
    val isLoading: Boolean = true,
    val retry: Boolean = false
)

val AddReviewUiState.isShareAble: Boolean get() = selectedRestaurant != null && contents.isNotEmpty() && list?.isEmpty() != true

data class Picture(val pictureId: Int = 0, val url: String, val isUploaded: Boolean = false)