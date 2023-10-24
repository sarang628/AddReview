package com.sryang.addreview

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.navigation.compose.rememberNavController
import com.sarang.instagralleryModule.gallery.GalleryScreen
import com.sryang.library.AddReviewScreen
import com.sryang.library.AddReviewViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val addReviewViewModel: AddReviewViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navController = rememberNavController()
            AddReviewScreen(
                navController = navController,
                galleryScreen = { color, onNext, onClose ->
                    GalleryScreen(color = color, onNext = onNext, onClose = onClose)
                },
                onRestaurant = {
                    navController.popBackStack()
                },
                onShared = {

                },
                onNext = {
                    navController.navigate("addReview")
                }
            )
        }
    }
}

