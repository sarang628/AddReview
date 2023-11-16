package com.sryang.addreview.compose

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.sryang.addreview.data.SelectRestaurantData
import com.sryang.addreview.uistate.AddReviewUiState
import com.sryang.addreview.uistate.isShareAble
import com.sryang.addreview.viewmodels.AddReviewViewModel
import com.sryang.addreview.viewmodels.SelectRestaurantViewModel

@Composable
fun AddReviewScreen(
    addReviewViewModel: AddReviewViewModel = hiltViewModel(),               // 리뷰 추가 뷰모델
    selectRestaurantViewModel: SelectRestaurantViewModel = hiltViewModel(), // 음식점 선택 뷰모델
    galleryScreen: @Composable (                                            // 갤러리 컴포즈
        color: Long,
        onNext: (List<String>) -> Unit,
        onClose: (Void?) -> Unit
    ) -> Unit,
    navController: NavHostController,                                       // 네비게이션 컨트롤러
    onRestaurant: (SelectRestaurantData) -> Unit,                           // 음식점 클릭
    onShared: () -> Unit,                                                   // 업로드 완료시
    onNext: () -> Unit,                                                     // 사진 선택 완료시
    onClose: () -> Unit,                                                    // 닫기 클릭
) {
    val uiState: AddReviewUiState by addReviewViewModel.uiState.collectAsState()
    val context = LocalContext.current
    Box {
        NavHost(
            navController = navController, startDestination = "gallery",
            modifier = Modifier
                .fillMaxSize()
        ) {
            composable("gallery") {
                galleryScreen.invoke(
                    color = 0xFFFFFFFF,
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
                        addReviewViewModel.onShare(onShared = onShared)
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