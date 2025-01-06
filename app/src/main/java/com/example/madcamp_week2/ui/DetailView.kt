package com.example.madcamp_week2.ui

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavHostController

@Composable
fun DetailView(navHostController: NavHostController, backStackEntry: NavBackStackEntry){

    val videoId = backStackEntry.arguments?.getString("videoId") ?: ""
    Text(videoId)
    ShortsPlayer(videoId)

}