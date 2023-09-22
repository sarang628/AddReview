package com.sryang.library


import android.Manifest
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.PermissionState
import com.google.accompanist.permissions.rememberPermissionState
import com.sarang.instagralleryModule.GalleryScreen

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun SelectPictureAndAddReview(onShare: (AddReviewData) -> Unit,
                              color: Long = 0xFF0000,
                              navController: NavHostController,
                              permissionState: PermissionState
                              ) {
    val list = remember { mutableStateListOf<String>() }
    NavHost(
        navController = navController, startDestination = "gallery",
        modifier = Modifier
            .fillMaxSize()
            .background(Color(color))
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

        composable("askPermission"){
            AskPermission(){
                permissionState.launchPermissionRequest()
            }
        }

        composable("permissionDenied"){

        }
    }
}