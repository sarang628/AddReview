package com.sarang.torang.addreview.uistate

import com.sarang.torang.addreview.data.SelectRestaurantData

data class SelectRestaurantUiState(
    val keyword: String = "",
    val isLoading: Boolean = false,
    val restaurantList: List<SelectRestaurantData> = ArrayList()
)
