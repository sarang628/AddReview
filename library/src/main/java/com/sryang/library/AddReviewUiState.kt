package com.sryang.library

import com.sryang.library.selectrestaurant.SelectRestaurantData

data class AddReviewUiState(
    val isProgress: Boolean = false,
    val list: List<String>? = null,
    val contents: String = "",
    val selectedRestaurant: SelectRestaurantData? = null
)

val AddReviewUiState.isShareAble: Boolean get() = selectedRestaurant != null && contents.isNotEmpty()
