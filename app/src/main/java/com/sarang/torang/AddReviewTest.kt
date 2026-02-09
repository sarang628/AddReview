package com.sarang.torang

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import com.example.instagramgallery.di.Instagramgallery_di.GalleryWithPhotoPicker
import com.sarang.torang.addreview.compose.AddReviewScreen

@Composable
fun AddReview(navController: NavHostController) {
    AddReviewScreen(
        navController = navController,
        galleryScreen = { GalleryWithPhotoPicker(onNext = it.onNext, onClose = { it.onClose.invoke() }) },
        onRestaurant = { navController.popBackStack() },
        onShared = {},
        onNext = { navController.navigate("addReview") },
        onClose = { navController.popBackStack() },
        onNotSelected = {}
    )
}
