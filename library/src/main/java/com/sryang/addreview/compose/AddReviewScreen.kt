package com.sryang.addreview.compose

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
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.sryang.addreview.uistate.AddReviewUiState
import com.sryang.addreview.viewmodels.AddReviewViewModel
import com.sryang.addreview.data.SelectRestaurantData
import com.sryang.addreview.viewmodels.SelectRestaurantViewModel
import com.sryang.addreview.uistate.isShareAble
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File
import kotlin.streams.toList

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun AddReviewScreen(
    addReviewViewModel: AddReviewViewModel = hiltViewModel(),
    selectRestaurantViewModel: SelectRestaurantViewModel = hiltViewModel(),
    color: Color = Color(0xFFFFFBE6),
    galleryScreen: @Composable (
        color: Long,
        onNext: (List<String>) -> Unit,
        onClose: (Void?) -> Unit
    ) -> Unit,
    navController: NavHostController,
    onRestaurant: (SelectRestaurantData) -> Unit,
    onShared: () -> Unit,
    onNext: () -> Unit,
    onClose: () -> Unit,
    galleryColor: Long = 0xFFFFFBE6,
) {
    val uiState: AddReviewUiState by addReviewViewModel.uiState.collectAsState()
    val context = LocalContext.current
    Box {
        NavHost(
            navController = navController, startDestination = "gallery",
            modifier = Modifier
                .fillMaxSize()
                .background(color)
        ) {
            composable("gallery") {
                galleryScreen.invoke(
                    color = galleryColor,
                    onNext = {
                        addReviewViewModel.selectPictures(it)
                        onNext.invoke()
                    },
                    onClose = { onClose.invoke() }
                )
            }

            composable("addReview") {
                AddReview(
                    uiState = uiState,
                    onShare = {
                        addReviewViewModel.onShare(context = context, onShared = onShared)
                    },
                    onBack = {
                        addReviewViewModel.deleteRestaurantAndContents()
                        navController.popBackStack()
                    },
                    onRestaurant = { navController.navigate("selectRestaurant") },
                    isShareAble = uiState.isShareAble,
                    onTextChange = { addReviewViewModel.inputText(it) },
                    content = uiState.contents
                )
            }
            composable("selectRestaurant") {
                SelectRestaurant(
                    viewModel = selectRestaurantViewModel,
                    onRestaurant = {
                        addReviewViewModel.selectRestaurant(it)
                        onRestaurant.invoke(it)
                    },
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