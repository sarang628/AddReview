package com.sryang.addreview.data

data class SelectRestaurantData(
    val restaurantId: Int,
    val restaurantName: String,
    val address: String
)

fun testSelectRestaurantData(): SelectRestaurantData {
    return SelectRestaurantData(
        restaurantId = 1,
        restaurantName = "restaurantName",
        address = "address"
    )
}