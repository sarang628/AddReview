package com.sarang.torang.addreview.compose

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
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
fun ModReviewScreen(
    reviewId: Int,
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
    onNotSelected: () -> Unit,                                              // 식당 선택하지 않음
) {
    val uiState: AddReviewUiState by addReviewViewModel.uiState.collectAsState()
    val isLogin by addReviewViewModel.isLogin.collectAsState(false)

    LaunchedEffect(key1 = reviewId, block = {
        addReviewViewModel.load(reviewId)
    })

    Box {
        NavHost(
            navController = navController, startDestination = if (isLogin) "modReview" else "login",
            modifier = Modifier
                .fillMaxSize()
        ) {
            composable("gallery") {
                galleryScreen.invoke(
                    color = 0xFFFFFFFF,
                    onNext = {
                        addReviewViewModel.selectPicturesInMod(it)
                        onNext.invoke()
                    },
                    onClose = { onClose.invoke() }
                )
            }

            composable("modReview") {
                if (uiState.isLoading || uiState.retry) {
                    Box(modifier = Modifier.fillMaxSize())
                    {
                        if (uiState.retry) {
                            Button(onClick = {
                                addReviewViewModel.load(reviewId)
                            }, Modifier.align(Alignment.Center)) {
                                Text(text = "retry")
                            }
                        } else if (uiState.isLoading) {
                            CircularProgressIndicator(Modifier.align(Alignment.Center))
                        }

                    }
                    return@composable
                }
                WriteReview(
                    isModify = true,
                    uiState = uiState,
                    onShare = {
                        addReviewViewModel.onModify(onShared = onShared)
                    },
                    onBack = {
                        addReviewViewModel.deleteRestaurantAndContents()
                        onClose.invoke()
                    },
                    onRestaurant = { navController.navigate("selectRestaurant") },
                    isShareAble = uiState.isShareAble,
                    onTextChange = { addReviewViewModel.inputText(it) },
                    onDeletePicture = { addReviewViewModel.onDeletePicture(it) },
                    onAddPicture = {
                        navController.navigate("gallery")
                    },
                    onChangeRating = {
                        addReviewViewModel.changeRating(it)
                    }
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