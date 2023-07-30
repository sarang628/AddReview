package com.sryang.library

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.modifier.modifierLocalConsumer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Preview
@Composable
fun AddReview() {

    //val testImage = [R.drawable.a, R.drawable.b]


    Column {
        Row(
            Modifier
                .height(40.dp)
                .padding(start = 8.dp, end = 8.dp), verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(id = R.drawable.ic_close),
                contentDescription = "",
                modifier = Modifier.height(27.dp)
            )
            Row(modifier = Modifier.weight(1f), horizontalArrangement = Arrangement.Center) {
                Text(text = "새 게시물", fontWeight = FontWeight.Bold)
            }
            Text(
                text = "다음",
                color = Color(0xFF7777FF),
                fontWeight = FontWeight.Bold
            )
        }

        Image(
            painter = painterResource(id = R.drawable.a),
            contentDescription = "",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxWidth()
                .height(350.dp)
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp)
                .padding(start = 8.dp, end = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(modifier = Modifier.weight(1f), text = "최근항목 >", fontWeight = FontWeight.Bold)
            Box(
                Modifier
                    .clip(CircleShape)
                    .background(Color.Gray)
                    .padding(6.dp)
                    .size(23.dp)
            ) {
                Image(
                    painter = painterResource(id = android.R.drawable.ic_menu_agenda),
                    contentDescription = ""
                )
            }

            Spacer(modifier = Modifier.width(10.dp))

            Box(
                Modifier
                    .clip(CircleShape)
                    .background(Color.Gray)
                    .padding(6.dp)
                    .size(23.dp)
            )
            {
                Image(
                    painter = painterResource(id = android.R.drawable.ic_menu_add),
                    contentDescription = ""
                )
            }
        }

        val testImage = arrayListOf<Int>(
            R.drawable.ic_a,
            R.drawable.ic_b,
            R.drawable.ic_c,
            R.drawable.ic_d,
            R.drawable.ic_e,
            R.drawable.ic_f,
            R.drawable.ic_g,
            R.drawable.ic_h,
            R.drawable.ic_i,
            R.drawable.ic_j,
            R.drawable.ic_k,
            R.drawable.ic_l,
            R.drawable.ic_m,
            R.drawable.ic_n,
            R.drawable.ic_o,
            R.drawable.ic_p,
            R.drawable.ic_q,
            R.drawable.ic_r,
            R.drawable.ic_s,
            R.drawable.ic_t,
            R.drawable.ic_u,
            R.drawable.ic_v,
            R.drawable.ic_w,
            R.drawable.ic_x,
            R.drawable.ic_y,
            R.drawable.ic_z
        )

        LazyVerticalGrid(
            modifier = Modifier
                .fillMaxWidth(),
            columns = GridCells.Adaptive(minSize = 80.dp), content = {
                items(1000) { it ->
                    Image(
                        modifier = Modifier.height(80.dp),
                        painter = painterResource(id = testImage[it % testImage.size]),
                        contentDescription = "",
                        contentScale = ContentScale.Crop
                    )
                }
            })
    }

}