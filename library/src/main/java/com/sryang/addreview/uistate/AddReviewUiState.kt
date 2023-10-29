package com.sryang.addreview.uistate

import com.sryang.addreview.data.SelectRestaurantData

data class AddReviewUiState(
    val isProgress: Boolean = false,
    val list: List<String>? = null,
    val contents: String = "",
    val selectedRestaurant: SelectRestaurantData? = null,
    val errorMsg: String? = null
)

val AddReviewUiState.isShareAble: Boolean get() = selectedRestaurant != null && contents.isNotEmpty()
