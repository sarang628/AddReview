package com.sryang.library


import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.sarang.instagralleryModule.GalleryScreen
import id.zelory.compressor.Compressor
import java.io.File

@Composable
fun SelectPictureAndAddReview(onShare: (AddReviewData) -> Unit, color: Long = 0xFF0000) {
    val context = LocalContext.current
    val navController = rememberNavController()
    val list = remember { mutableStateListOf<String>() }
    NavHost(
        navController = navController, startDestination = "gallery",
        modifier = Modifier.fillMaxSize().background(Color(color))
    ) {
        composable("gallery") {
            GalleryScreen(color = 0xFF0000, onNext = {
                list.removeAll(list)
                list.addAll(it)
                navController.navigate("addReview")
            }, onClose = {

            })
        }

        composable("addReview") {
            AddReviewScreen(list, onShare = onShare, onBack = { navController.popBackStack() })
        }
    }
}