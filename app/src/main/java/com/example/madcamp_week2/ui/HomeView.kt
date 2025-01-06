package com.example.madcamp_week2.ui

import android.app.Activity
import android.content.pm.ActivityInfo
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.VerticalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.compose.LocalLifecycleOwner
import com.example.madcamp_week2.HomeViewModel
import com.example.madcamp_week2.MainViewModel
import com.example.madcamp_week2.R


import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView

data class VideoData(
    val videoId: String,
    val channelImage: Int,
    val channelId: String,
    val videoTitle: String,
    val numComment: Int,
)

@Composable
fun HomeView(videoData: MainViewModel.VideoState, homeViewModel: HomeViewModel){

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
    ) {

        if (videoData.loading) {
            Text(text = "Loading...", fontSize = 20.sp) // 로딩 메시지 표시
        } else if (videoData.error != null) {
            Text(text = videoData.error, color = Color.Red) // 에러 메시지 표시
        } else if (videoData.data.isEmpty()) {
            Text(text = "No videos available.") // 데이터가 없을 때 처리
        } else{

            VideoPager(videoIds = videoData.data.map { it.video_id })

        }

    }
}


@Composable
fun OverlayButtons() {
    Column(
        modifier = Modifier
            .padding(top = 320.dp, end = 16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(
            painter = painterResource(id = R.drawable.heart_icon),
            contentDescription = "Like Icon",
            modifier = Modifier.size(24.dp),
            tint = Color.White
        )
        /*
        Icon(
            painter = painterResource(id = R.drawable.comment_icon),
            contentDescription = "Comment Icon",
            modifier = Modifier.size(24.dp),
            tint = Color.White
        )
        Text(
            text = "4",
            fontSize = 10.sp,
            color = Color.White,
        )
        Icon(
            painter = painterResource(id = R.drawable.share_icon),
            contentDescription = "Share Icon",
            modifier = Modifier.size(24.dp),
            tint = Color.White
        )
        */
    }
}



@Composable
fun ShortsPlayer(videoId: String) {
    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current

    // (선택) 현재 Activity의 기존 orientation 상태 저장
    val activity = context as? Activity
    val previousOrientation = activity?.requestedOrientation

    // 화면이 이 컴포저블로 이동했을 때 -> 세로 고정
    // 컴포저블에서 벗어날 때 -> 원래 상태로 복귀
    DisposableEffect(Unit) {
        activity?.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_SENSOR_PORTRAIT

        onDispose {
            // 복귀 시 원래 orientation 복원
            activity?.requestedOrientation = previousOrientation ?: ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED
        }
    }

    Box (
        contentAlignment = Alignment.CenterEnd
    ){



        // 뷰를 꽉 채우기 위해 fillMaxSize
        AndroidView(
            modifier = Modifier
                .fillMaxWidth(),
            factory = { localContext ->
                val youTubePlayerView = YouTubePlayerView(localContext).apply {
                    // 자동 초기화 꺼두고, lifecycle 수동 연결하기
                    enableAutomaticInitialization = false
                    layoutParams = ViewGroup.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.MATCH_PARENT
                    )
                }

                // Lifecycle 연동
                lifecycleOwner.lifecycle.addObserver(youTubePlayerView)

                // Player 리스너 등록
                youTubePlayerView.addYouTubePlayerListener(object :
                    AbstractYouTubePlayerListener() {
                    override fun onReady(youTubePlayer: YouTubePlayer) {
                        // Shorts videoId 로드
                        youTubePlayer.loadVideo(videoId, 0f)
                    }
                })

                youTubePlayerView
            }
        )

        OverlayButtons()
    }
}

@Composable
fun VideoPager(videoIds: List<String>) {
    val pagerState = rememberPagerState(pageCount = { videoIds.size })

    VerticalPager(
        state = pagerState,
        modifier = Modifier.fillMaxSize()
    ) { page ->
        if (page == pagerState.currentPage) {
            // Load only the currently visible video
            ShortsPlayer(videoId = videoIds[page])
        }
    }
}
