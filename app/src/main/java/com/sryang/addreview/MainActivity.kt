package com.sryang.addreview

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.ui.graphics.Color
import com.sryang.library.SelectPictureAndAddReview

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SelectPictureAndAddReview(onShare = {

            }, color = 0xFF000000)
        }
    }
}
