package com.sryang.addreview

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.navigation.compose.rememberNavController
import com.sarang.instagralleryModule.gallery.GalleryScreen
import com.sryang.library.AddReviewScreen
import com.sryang.library.AddReviewViewModel
import com.sryang.library.ReviewService
import com.sryang.library.selectrestaurant.SelectRestaurantViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var reviewService: ReviewService

    private val addReviewViewModel: AddReviewViewModel by viewModels()

    private val selectRestaurantViewModel: SelectRestaurantViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navController = rememberNavController()
            AddReviewScreen(
                addReviewViewModel = addReviewViewModel,
                navController = navController,
                selectRestaurantViewModel = selectRestaurantViewModel,
                galleryScreen = {
                    GalleryScreen(color = 0xFFFFFBE6, onNext = {
                        addReviewViewModel.selectPictures(it)
                        navController.navigate("addReview")
                    }, onClose = {})
                },
                onRestaurant = {}
            )
        }
    }
}

