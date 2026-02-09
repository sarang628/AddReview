package com.sarang.torang.addreview.compose

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.sarang.torang.addreview.data.SelectRestaurantData
import com.sarang.torang.addreview.uistate.AddReviewUiState
import com.sarang.torang.addreview.uistate.isShareAble
import com.sarang.torang.addreview.viewmodels.AddReviewViewModel

class GalleryScreenData(
    val color: Long,
    val onNext: (List<String>) -> Unit = {},
    val onClose: () -> Unit = {}
)

@Composable
fun AddReviewScreen(viewModel           : AddReviewViewModel                        = hiltViewModel(),
                    galleryScreen       : @Composable (GalleryScreenData) -> Unit   = {},
                    navController       : NavHostController                         = rememberNavController(),
                    onRestaurant        : (SelectRestaurantData) -> Unit            = {},
                    onShared            : () -> Unit                                = {},
                    onNext              : () -> Unit                                = {},
                    onClose             : () -> Unit                                = {},
                    onNotSelected       : () -> Unit                                = {},
                    loginScreen         : @Composable () -> Unit                    = {}) {
    val uiState: AddReviewUiState = viewModel.uiState
    val isLogin by viewModel.isLogin.collectAsState(false)
    val context = LocalContext.current
    Box {
        NavHost(modifier            = Modifier.fillMaxSize(),
                navController       = navController,
                startDestination    = if (isLogin) "gallery" else "login") {
            composable("gallery") {
                galleryScreen.invoke(GalleryScreenData(color = 0xFFFFFFFF,
                                                            onNext = { viewModel.selectPictures(it)
                                                                       onNext.invoke() },
                                                            onClose = { onClose.invoke() }))
            }
            composable("addReview") {
                WriteReview(
                    uiState = uiState,
                    onShare = { viewModel.onShare(onShared = onShared,
                                                  context = context) },
                    onBack = { viewModel.deleteRestaurantAndContents();navController.popBackStack() },
                    onRestaurant = { navController.navigate("selectRestaurant") },
                    isShareAble = uiState.isShareAble,
                    onTextChange = { viewModel.inputText(it) },
                    onDeletePicture = { viewModel.onDeletePicture(it) },
                    onChangeRating = { viewModel.changeRating(it) }
                )
            }
            composable("selectRestaurant") {
                SelectRestaurant(
                    onRestaurant = { viewModel.selectRestaurant(it);onRestaurant.invoke(it) },
                    onClose = { navController.popBackStack() },
                    onNotSelected = { viewModel.notSelectRestaurant();onNotSelected() }
                )
            }
            composable("login") {
                loginScreen.invoke()
            }
        }

        if (uiState.isProgress)
            CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Login(onBack: () -> Unit, onLogin: () -> Unit) {
    Scaffold(topBar = {
        TopAppBar(title = { /*TODO*/ },
            navigationIcon = { IconButton(onClick = onBack) { Icon(imageVector = Icons.AutoMirrored.Default.ArrowBack, contentDescription = "") } }) })
    {
        Box(Modifier.padding(it).fillMaxSize()) {
            Column(Modifier.align(Alignment.Center)) {
                Text(text = "로그인을 해주세요.", fontSize = 17.sp)
                Button(onClick = onLogin) { Text(text = "LOGIN WITH EMAIL") }
            }
        }
    }
}
