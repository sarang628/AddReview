package com.sryang.library

data class AddReviewUiState(
    val isProgress: Boolean = false,
    val list: List<String>? = null,
    val restaurantId: Int? = null,
    val contents: String = ""
)

val AddReviewUiState.isShareAble: Boolean get() = restaurantId != null && contents.isNotEmpty()