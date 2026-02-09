package com.sarang.torang.addreview.compose

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun SelectRestaurantLabel(modifier              : Modifier      = Modifier,
                          selectedRestaurantName: String        = "",
                          onRestaurant          : () -> Unit    = {}) {
    Row(modifier = modifier.height(50.dp),
        verticalAlignment = Alignment.CenterVertically) {
        TextButton(onClick = onRestaurant) {
            Text(text = selectedRestaurantName.ifEmpty { "Add restaurant" })
        }
    }
}

@Preview
@Composable
fun PreviewSelectRestaurantLabel() {
    SelectRestaurantLabel(selectedRestaurantName = "selectedRestaurantName")
}