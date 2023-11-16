package com.sryang.addreview

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.google.samples.apps.sunflower.ui.TorangTheme
import com.sarang.instagralleryModule.GalleryNavHost
import com.sryang.addreview.compose.AddReviewScreen
import com.sryang.addreview.viewmodels.AddReviewViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val addReviewViewModel: AddReviewViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TorangTheme {
                Surface(
                    Modifier
                        .fillMaxSize()
                        .background(MaterialTheme.colorScheme.background)) {
                    val navController = rememberNavController()
                    AddReviewScreen(
                        navController = navController,
                        galleryScreen = { color, onNext, onClose ->
                            GalleryNavHost(
                                onNext = onNext,
                                onClose = { onClose.invoke(null) })
                        },
                        onRestaurant = {
                            navController.popBackStack()
                        },
                        onShared = {

                        },
                        onNext = {
                            navController.navigate("addReview")
                        }, onClose = {
                            navController.popBackStack()
                        }
                    )
                }
            }
        }
    }
}

