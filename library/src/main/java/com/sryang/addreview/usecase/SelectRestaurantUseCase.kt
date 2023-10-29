package com.sryang.addreview.usecase

import com.sryang.addreview.data.SelectRestaurantData


interface SelectRestaurantUseCase {
    suspend fun getRestaurant(): List<SelectRestaurantData>
}