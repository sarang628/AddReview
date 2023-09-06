package com.sryang.library

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.sarang.instagralleryModule.GalleryScreen

@Preview
@Composable
fun AddReview(color: Long = 0xFF0000) {
    GalleryScreen(color = color, onClose = {}, onNext = {})
}