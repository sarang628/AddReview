package com.sryang.library

import android.Manifest
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import com.sryang.library.selectrestaurant.SelectRestaurant
import com.sryang.library.selectrestaurant.SelectRestaurantData
import com.sryang.library.selectrestaurant.SelectRestaurantViewModel
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File
import kotlin.streams.toList

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun AddReviewScreen(
    addReviewViewModel: AddReviewViewModel,
    selectRestaurantViewModel: SelectRestaurantViewModel,
    color: Color = Color(0xFFFFFBE6),
    galleryScreen: @Composable () -> Unit,
    navController: NavHostController,
    onRestaurant: (SelectRestaurantData) -> Unit
) {
    val uiState: AddReviewUiState by addReviewViewModel.uiState.collectAsState()
    val request = rememberPermissionState(
        permission = Manifest.permission.READ_EXTERNAL_STORAGE,
        onPermissionResult = { navController.go("gallery") })
    Box {
        NavHost(
            navController = navController, startDestination =
            if (request.status.isGranted) "gallery" else "askPermission",
            modifier = Modifier
                .fillMaxSize()
                .background(color)
        ) {
            composable("gallery") {
                galleryScreen.invoke()
            }

            composable("addReview") {
                uiState.list?.let {
                    AddReview(
                        list = it,
                        onShare = { addReviewViewModel.onShare(it) },
                        onBack = { navController.popBackStack() },
                        onRestaurant = { navController.navigate("selectRestaurant") },
                        isShareAble = uiState.isShareAble,
                        onTextChange = { addReviewViewModel.inputText(it) },
                        content = uiState.contents
                    )
                }
            }

            composable("askPermission") {
                AskPermission { request.launchPermissionRequest() }
            }

            composable("permissionDenied") {

            }
            composable("selectRestaurant") {
                SelectRestaurant(
                    viewModel = selectRestaurantViewModel,
                    onRestaurant = onRestaurant,
                    onClose = { navController.popBackStack() },
                    onRefresh = {}
                )
            }
        }

        if (uiState.isProgress)
            CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
    }
}

fun filesToMultipart(file: List<String>): ArrayList<MultipartBody.Part> {
    val list = ArrayList<MultipartBody.Part>()
        .apply {
            addAll(
                file.stream().map {
                    val file = File(it)
                    MultipartBody.Part.createFormData(
                        name = "file",
                        filename = file.name,
                        body = file.asRequestBody()
                    )
                }.toList()
            )
        }
    return list
}


fun NavHostController.go(route: String) {
    navigate(route) {
        // Pop up to the start destination of the graph to
        // avoid building up a large stack of destinations
        // on the back stack as users select items
        popUpTo(graph.findStartDestination().id) {
            saveState = true
        }
        // Avoid multiple copies of the same destination when
        // reselecting the same item
        launchSingleTop = true
        // Restore state when reselecting a previously selected item
        restoreState = true
    }
}