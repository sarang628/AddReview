package com.sryang.library

import android.Manifest
import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.File
import kotlin.streams.toList

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun AddReviewScreen(
    remoteReviewService: ReviewService
) {
    var isProgress by remember { mutableStateOf(false) }
    val coroutine = rememberCoroutineScope()
    val navController = rememberNavController()
    val request = rememberPermissionState(
        permission = Manifest.permission.READ_EXTERNAL_STORAGE,
        onPermissionResult = {
            navController.navigate("gallery")
        })
    Box {
        SelectPictureAndAddReview(onShare = {
            coroutine.launch {
                try {
                    isProgress = true
                    remoteReviewService.addReview(
                        params = HashMap<String, RequestBody>().apply {
                            put(
                                "contents",
                                it.contents.toRequestBody("text/plain".toMediaTypeOrNull())
                            )
                        },
                        file = filesToMultipart(it.pictures)
                    )
                    isProgress = false
                } catch (e: Exception) {
                    Log.d("MainActivity", e.toString())
                    isProgress = false
                }
            }
        }, color = 0xFFFFFFFF, navController = navController, permissionState = request)
        if (!request.status.isGranted) {
            navController.navigate("askPermission")
        }

        if (isProgress)
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
