package com.sarang.torang

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.instagramgallery.di.Instagramgallery_di.GalleryWithPhotoPicker
import com.sarang.torang.addreview.compose.AddReviewScreen
import com.sarang.torang.addreview.compose.ModReviewScreen
import com.sarang.torang.repository.LoginRepository
import com.sarang.torang.repository.test.LoginRepositoryTest
import com.sryang.torang.ui.TorangTheme
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @Inject lateinit var loginRepository: LoginRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TorangTheme {
                Surface(Modifier.fillMaxSize().background(MaterialTheme.colorScheme.background)) {
                    val navController = rememberNavController()
                    Box(modifier = Modifier.height(830.dp))
                    {
                        NavHost(navController = navController, startDestination = "Menu"){
                            composable("Menu") {
                                Column {
                                    Button({navController.navigate("AddReview")}) {
                                        Text("AddReview")
                                    }
                                    Button({navController.navigate("LoginRepositoryTest")}) {
                                        Text("LoginRepositoryTest")
                                    }
                                }
                            }
                            composable("AddReview") {
                                AddReview(rememberNavController())
                            }
                            composable("ModReview") {
                                ModReview(rememberNavController())
                            }
                            composable("LoginRepositoryTest") {
                                LoginRepositoryTest(loginRepository = loginRepository)
                            }
                        }
                    }
                }
            }
        }
    }

    @Composable
    fun AddReview(navController: NavHostController) {
        AddReviewScreen(
            navController = navController,
            galleryScreen = { GalleryWithPhotoPicker(onNext = it.onNext, onClose = { it.onClose.invoke() }) },
            onRestaurant = { navController.popBackStack() },
            onShared = {},
            onNext = { navController.navigate("addReview") },
            onClose = { navController.popBackStack() },
            onNotSelected = {},
            onBack = {},
            onLogin = {}
        )
    }

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
}

