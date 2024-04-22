package com.sarang.torang.addreview.compose

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun SelectRestaurantLabel(modifier: Modifier = Modifier, selectedRestaurantName: String?, onRestaurant: () -> Unit) {
    Row(
        modifier
            .height(50.dp)
            .clickable { onRestaurant.invoke() },
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(text = selectedRestaurantName ?: "Add restaurant")
    }
}

@Preview
@Composable
fun PreviewSelectRestaurantLabel() {
    SelectRestaurantLabel(selectedRestaurantName = "selectedRestaurantName") {

    }
}