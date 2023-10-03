package com.sryang.library.selectrestaurant

data class SelectRestaurantUiState(
    val isLoading: Boolean = false,
    val restaurantList: List<SelectRestaurantData> = ArrayList()
)
