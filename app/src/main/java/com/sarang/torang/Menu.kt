package com.sarang.torang

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

fun NavGraphBuilder.menu(navController : NavHostController){
    composable("Menu") {
        Menu(navController)
    }
}

@Preview
@Composable
internal fun Menu(navController : NavHostController = rememberNavController()){
    Column {
        Button({navController.navigate("AddReview")}) {
            Text("AddReview")
        }
        Button({navController.navigate("WriteReview")}) {
            Text("WriteReview")
        }
        Button({navController.navigate("SelectRestaurant")}) {
            Text("SelectRestaurant")
        }
        Button({navController.navigate("LoginRepositoryTest")}) {
            Text("LoginRepositoryTest")
        }
    }
}