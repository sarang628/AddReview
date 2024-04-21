package com.sarang.torang.addreview.uistate

import com.sarang.torang.addreview.data.SelectRestaurantData

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

val AddReviewUiState.isShareAble: Boolean get() = contents.isNotEmpty()
        && list?.isEmpty() != true
        // && selectedRestaurant != null // 레스토랑 선택 시 활성화

data class Picture(val pictureId: Int = 0, val url: String, val isUploaded: Boolean = false)