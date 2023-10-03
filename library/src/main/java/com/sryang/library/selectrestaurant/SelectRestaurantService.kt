package com.sryang.library.selectrestaurant


interface SelectRestaurantService {
    suspend fun getRestaurant(): List<SelectRestaurantData>
}