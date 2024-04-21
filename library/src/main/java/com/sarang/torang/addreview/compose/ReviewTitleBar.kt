package com.sarang.torang.addreview.compose

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.sarang.torang.R

@Composable
fun ReviewTitleBar(
    title: String = "New post",
    onBack: () -> Unit,    // 뒤로가기 클릭
    onShare: () -> Unit,   // 공유 클릭
    isShareAble: Boolean,   // 업로드 가능 여부
    sendTitle: String = "share"
) {
    Row(
        modifier = Modifier
            .height(50.dp)
            .fillMaxWidth()
            .padding(start = 8.dp, end = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painter =
            painterResource(id = R.drawable.ic_back),
            contentDescription = "",
            modifier = Modifier
                .height(30.dp)
                .clickable {
                    onBack()
                }
        )
        Spacer(modifier = Modifier.width(20.dp))
        Text(text = title, fontWeight = FontWeight.Bold, fontSize = 18.sp)
        Row(Modifier.fillMaxWidth(), Arrangement.End) {
            Text(text = sendTitle,
                color = Color(if (isShareAble) 0xFF4193EF else 0xFFAEAEAE),
                modifier = Modifier.clickable(isShareAble) {
                    onShare()
                }
            )
        }
    }
}

@Preview
@Composable
fun PreviewReviewTitleBar() {
    ReviewTitleBar(onBack = {}, onShare = {}, isShareAble = true)
}