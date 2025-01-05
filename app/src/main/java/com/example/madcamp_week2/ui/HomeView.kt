package com.example.madcamp_week2.ui

import android.annotation.SuppressLint
import android.app.Activity
import android.content.pm.ActivityInfo
import android.media.Image
import android.view.View
import android.view.ViewGroup
import android.webkit.WebView
import android.widget.FrameLayout
import android.widget.VideoView
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.compose.LocalLifecycleOwner
import com.example.madcamp_week2.HomeViewModel
import com.example.madcamp_week2.MainViewModel
import com.example.madcamp_week2.R
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.VerticalPager
import com.google.accompanist.pager.rememberPagerState
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.options.IFramePlayerOptions
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
            youTubePlayerView.addYouTubePlayerListener(object : AbstractYouTubePlayerListener() {
                override fun onReady(youTubePlayer: YouTubePlayer) {
                    // 기본 UI를 감추기 위해 setCustomPlayerUi 호출
                    youTubePlayerView.setCustomPlayerUi(View(context)) // 빈 View로 대체

                    // Shorts videoId 로드
                    youTubePlayer.loadVideo(videoId, 0f)
                }
            })

            youTubePlayerView
        }
    )
}


@OptIn(ExperimentalPagerApi::class)
@Composable
fun VideoPager(videoIds: List<String>) {
    val pagerState = rememberPagerState()

    VerticalPager(
        count = videoIds.size,
        state = pagerState,
        modifier = Modifier.fillMaxSize()
    ) { page ->
        // 현재 페이지에 해당하는 동영상을 표시
        ShortsPlayer(videoId = videoIds[page])
    }
}