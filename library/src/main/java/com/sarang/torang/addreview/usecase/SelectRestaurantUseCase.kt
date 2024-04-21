package com.sarang.torang.addreview.usecase

import com.sarang.torang.addreview.data.SelectRestaurantData


interface SelectRestaurantUseCase {
    suspend fun invoke(keyword : String): List<SelectRestaurantData>
}