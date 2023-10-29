package com.sryang.addreview.uistate

import com.sryang.addreview.data.SelectRestaurantData

data class SelectRestaurantUiState(
    val isLoading: Boolean = false,
    val restaurantList: List<SelectRestaurantData> = ArrayList()
)
