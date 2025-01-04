package com.example.madcamp_week2

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.madcamp_week2.ui.Screen
import com.example.madcamp_week2.ui.VideoData

class MainViewModel : ViewModel(){

    private val _currentScreen: MutableState<Screen> = mutableStateOf(Screen.BottomScreen.Home)

    val currentScreen: MutableState<Screen>
        get() = _currentScreen

    fun setCurrentScreen(screen: Screen){
        _currentScreen.value = screen
    }

    private val _videoData: MutableState<VideoData?> = mutableStateOf(null)
    val videoData: MutableState<VideoData?> get() = _videoData

    // test
    fun loadVideoData(videoId: String) {
        // Mock data based on the videoId
        val mockData = VideoData(
            videoId = videoId, // Example video path
            channelImage = R.drawable.facebook_logo, // Replace with an actual drawable resource
            channelId = "NetflixKorea", // Example channel ID
            videoTitle = "오징어 게임 시즌2", // Example video title
            numComment = 300 // Example number of comments
        )
        _videoData.value = mockData // Update the state with the mock data
    }
}