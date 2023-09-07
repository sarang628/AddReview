package com.sryang.library


import android.widget.Toast
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.sarang.instagralleryModule.GalleryScreen

@Composable
fun SelectPictureAndAddReview(onShare: (Void?) -> Unit) {
    val context = LocalContext.current
    val navController = rememberNavController()
    val list = remember { mutableStateListOf<String>() }
    NavHost(
        navController = navController, startDestination = "gallery",
        modifier = Modifier.fillMaxSize()
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
            AddReviewScreen(list, onShare = {
                Toast.makeText(context, it.toString(), Toast.LENGTH_SHORT).show()
            }, onBack = { navController.popBackStack() })
        }
    }
}