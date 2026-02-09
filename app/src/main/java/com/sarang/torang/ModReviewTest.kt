package com.sarang.torang

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import com.example.instagramgallery.di.Instagramgallery_di.GalleryWithPhotoPicker
import com.sarang.torang.addreview.compose.ModReviewScreen

@Composable
fun ModReview(navController: NavHostController) {
    ModReviewScreen(
        reviewId = 365,
        onRestaurant = { navController.popBackStack() },
        galleryScreen = { color, onNext, onClose -> GalleryWithPhotoPicker(onNext = onNext, onClose = { onClose.invoke(null) }) },
        navController = navController,
        onClose = { },
        onNext = { navController.popBackStack() },
        onShared = {},
        onNotSelected = {}
    )
}