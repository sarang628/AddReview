package com.sryang.addreview

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.sryang.library.AddReviewScreen
import com.sryang.library.ReviewService
import com.sryang.torang_repository.services.RemoteReviewService
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @Inject
    lateinit var remoteReviewService: RemoteReviewService

    @Inject
    lateinit var reviewService: ReviewService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AddReviewScreen(reviewService)
        }
    }
}

