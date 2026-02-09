package com.sarang.torang

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.sarang.torang.addreview.compose.SelectRestaurant
import com.sarang.torang.repository.LoginRepository
import com.sarang.torang.repository.test.LoginRepositoryTest
import com.sryang.torang.ui.TorangTheme
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @Inject lateinit var loginRepository: LoginRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TorangTheme {
                Surface(Modifier.fillMaxSize().background(MaterialTheme.colorScheme.background)) {
                    val navController = rememberNavController()
                    Box(modifier = Modifier.height(830.dp))
                    {
                        NavHost(navController = navController, startDestination = "Menu"){
                            composable("Menu") {
                                Menu(navController)
                            }
                            composable("AddReview") {
                                AddReview(rememberNavController())
                            }
                            composable("SelectRestaurant"){
                                SelectRestaurant()
                            }
                            composable("ModReview") {
                                ModReview(rememberNavController())
                            }
                            composable("LoginRepositoryTest") {
                                LoginRepositoryTest(loginRepository = loginRepository)
                            }
                        }
                    }
                }
            }
        }
    }

    @Preview
    @Composable
    fun Menu(navController : NavHostController = rememberNavController()){
        Column {
            Button({navController.navigate("AddReview")}) {
                Text("AddReview")
            }
            Button({navController.navigate("SelectRestaurant")}) {
                Text("SelectRestaurant")
            }
            Button({navController.navigate("LoginRepositoryTest")}) {
                Text("LoginRepositoryTest")
            }
        }
    }
}

