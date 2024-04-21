package com.sarang.torang.addreview.compose

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.sp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WriteCaption(input: String, onValueChange: (String) -> Unit) {

    OutlinedTextField(
        value = input,
        placeholder = {
            Text(text = "Write a caption", color = Color.Gray, fontSize = 14.sp)
        },
        onValueChange = {
            onValueChange.invoke(it)
        },
        modifier = Modifier
            .fillMaxWidth(),
        colors = TextFieldDefaults.outlinedTextFieldColors(
            focusedBorderColor = Color.Transparent,
            unfocusedBorderColor = Color.Transparent
        ),
    )
}
