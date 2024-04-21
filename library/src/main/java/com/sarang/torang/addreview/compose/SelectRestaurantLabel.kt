package com.sarang.torang.addreview.compose

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun SelectRestaurantLabel(selectedRestaurantName: String?, onRestaurant: () -> Unit) {
    Row(
        Modifier
            .height(50.dp)
            .padding(start = 18.dp)
            .clickable { onRestaurant.invoke() },
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(text = selectedRestaurantName ?: "Add restaurant")
    }
}