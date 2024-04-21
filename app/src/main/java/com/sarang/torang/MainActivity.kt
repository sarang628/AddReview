package com.sarang.torang

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.rememberNavController
import com.google.samples.apps.sunflower.ui.TorangTheme
import com.sarang.instagralleryModule.GalleryNavHost
import com.sarang.torang.addreview.compose.AddReviewScreen
import com.sarang.torang.addreview.compose.ModReviewScreen
import com.sarang.torang.addreview.viewmodels.AddReviewViewModel
import com.sarang.torang.repository.LoginRepository
import com.sarang.torang.repository.LoginRepositoryTest
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var loginRepository: LoginRepository

    val viewModel: AddReviewViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TorangTheme {
                Surface(
                    Modifier
                        .fillMaxSize()
                        .background(MaterialTheme.colorScheme.background)
                ) {
                    val navController = rememberNavController()
                    Column(Modifier.verticalScroll(rememberScrollState())) {
                        Box(modifier = Modifier.height(830.dp))
                        {
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

                            /*ModReviewScreen(
                                reviewId = 117,
                                onRestaurant = { navController.popBackStack() },
                                galleryScreen = { color, onNext, onClose ->
                                    GalleryNavHost(
                                        onNext = onNext,
                                        onClose = { onClose.invoke(null) })
                                },
                                navController = navController,
                                onClose = { },
                                onNext = { navController.popBackStack() },
                                onShared = {}
                            )*/


                        }
                        LoginRepositoryTest(loginRepository = loginRepository)
                    }
                }
            }
        }
    }
}

