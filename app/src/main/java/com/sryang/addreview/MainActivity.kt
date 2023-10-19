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
                galleryScreen = {
                    GalleryScreen(color = 0xFFFFFBE6, onNext = {
                        //addReviewViewModel.selectPictures(it)
                        navController.navigate("addReview")
                    }, onClose = {})
                },
                onRestaurant = {
                    //addReviewViewModel.selectRestaurant(it)
                    navController.popBackStack()
                },
                onShared = {

                }
            )
        }
    }
}

