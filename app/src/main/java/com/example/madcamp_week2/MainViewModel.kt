package com.example.madcamp_week2

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.madcamp_week2.remote.Video
import com.example.madcamp_week2.remote.apiService
import com.example.madcamp_week2.ui.Screen
import com.example.madcamp_week2.ui.VideoData
import kotlinx.coroutines.launch

class MainViewModel : ViewModel(){

    private val _currentScreen: MutableState<Screen> = mutableStateOf(Screen.BottomScreen.Home)

    val currentScreen: MutableState<Screen>
        get() = _currentScreen

    fun setCurrentScreen(screen: Screen){
        _currentScreen.value = screen
    }

    private val _videoState = mutableStateOf(VideoState())
    val videoState: State<VideoState> = _videoState

    init {
        fetchCategories()
    }

    private fun fetchCategories(limit: Int = 1000) {
        viewModelScope.launch { // suspend 함수가 실행될 수 있게 해줌
            val politicalKeywords = listOf("정치", "대통령", "국회", "정부", "선거", "의회", "행정", "법안", "민주", "보수", "진보", "법원", "윤석열","탄핵", "관저", "뉴스", "국민의 힘","계엄", "체포","총리", "위원")

            try{
                val response = apiService.getTrendingVideos(limit)
                val koreanVideos = response.filter { video ->
                    !politicalKeywords.any { keyword -> video.title.contains(keyword, ignoreCase = true) } && // Exclude political keywords
                            video.title.contains(Regex("[가-힣]")) // Ensure title has Korean characters
                }
                _videoState.value = _videoState.value.copy(
                    loading = false,
                    data = koreanVideos,
                    error = null
                )
            }catch (e: Exception){
                _videoState.value = _videoState.value.copy(
                    loading = false,
                    error = "Error fetching Categories : ${e.message}"
                )
            }
        }
    }

    data class VideoState(
        val loading:Boolean = true,
        val data: List<Video> = emptyList(),
        val error: String? = null
    )
}