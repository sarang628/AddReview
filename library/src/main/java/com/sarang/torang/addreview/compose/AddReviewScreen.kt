package com.sarang.torang.addreview.compose

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.sarang.torang.addreview.data.SelectRestaurantData
import com.sarang.torang.addreview.uistate.AddReviewUiState
import com.sarang.torang.addreview.uistate.isShareAble
import com.sarang.torang.addreview.viewmodels.AddReviewViewModel

@Composable
fun AddReviewScreen(
    addReviewViewModel: AddReviewViewModel = hiltViewModel(),               // 리뷰 추가 뷰모델
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
    onNotSelected: () -> Unit,                                                    // 닫기 클릭
) {
    val uiState: AddReviewUiState by addReviewViewModel.uiState.collectAsState()
    val isLogin by addReviewViewModel.isLogin.collectAsState(false)
    Box {
        NavHost(
            navController = navController, startDestination = if (isLogin) "gallery" else "login",
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
                WriteReview(
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
                    onDeletePicture = { addReviewViewModel.onDeletePicture(it) },
                    onChangeRating = { addReviewViewModel.changeRating(it) }
                )
            }
            composable("selectRestaurant") {
                SelectRestaurant(
                    onRestaurant = {
                        addReviewViewModel.selectRestaurant(it)
                        onRestaurant.invoke(it)
                    },
                    onClose = { navController.popBackStack() },
                    onNotSelected = {
                        addReviewViewModel.notSelectRestaurant()
                        onNotSelected()
                    }
                )
            }
            composable("login") {
                Box(Modifier.fillMaxSize()) {
                    Text(text = "로그인을 해주세요.", Modifier.align(Alignment.Center), fontSize = 17.sp)
                }
            }
        }

        if (uiState.isProgress)
            CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
    }
}