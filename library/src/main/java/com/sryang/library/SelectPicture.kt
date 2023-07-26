package com.sryang.library

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Preview
@Composable
fun AddReview() {
    Column {
        Row {
            Image(
                painter = painterResource(id = android.R.drawable.ic_delete),
                contentDescription = ""
            )
            Row(modifier = Modifier.weight(1f), horizontalArrangement = Arrangement.Center) {
                Text(text = "새 게시물")
            }
            Text(text = "다음")
        }

        Image(
            painter = painterResource(id = android.R.drawable.star_big_on),
            contentDescription = "",
            modifier = Modifier
                .fillMaxWidth()
                .height(350.dp)
        )

        Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
            Text(modifier = Modifier.weight(1f), text = "최근항목")
            Image(
                painter = painterResource(id = android.R.drawable.ic_menu_agenda),
                contentDescription = ""
            )

            Image(
                painter = painterResource(id = android.R.drawable.ic_menu_add),
                contentDescription = ""
            )
        }

        LazyVerticalGrid(
            modifier = Modifier
                .fillMaxWidth(),
            columns = GridCells.Adaptive(minSize = 70.dp), content = {
                items(1000) {
                    Image(
                        modifier = Modifier.height(70.dp),
                        painter = painterResource(id = android.R.drawable.star_on),
                        contentDescription = ""
                    )
                }
            })
    }

}