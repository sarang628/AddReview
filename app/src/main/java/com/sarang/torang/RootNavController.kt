package com.sarang.torang

import androidx.navigation.NavHostController

class RootNavController(val navHostController: NavHostController? = null) {
    fun popBackStack() {
        navHostController?.popBackStack()
    }

    fun emailLogin() {
    }
}