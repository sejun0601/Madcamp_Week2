package com.example.madcamp_week2.ui

import android.media.Image
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import com.example.madcamp_week2.R

data class VideoData(
    val videoPath: String,
    val channelImage: Int,
    val channelId: String,
    val videoTitle: String,
    val numComment: Int,
)

@Composable
fun HomeView(videoData: VideoData){
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
    ) {
        // 상단 youtube 로고
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(19.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                painter = painterResource(id = R.drawable.youtube_icon),
                contentDescription = "youtube Icon",
                modifier = Modifier.size(24.dp),
                tint = Color.White
            )
            Spacer(modifier = Modifier.width(8.dp))
            Icon(
                painter = painterResource(id = R.drawable.shorts_typo),
                contentDescription = "shorts typo",
                modifier = Modifier.size(45.dp),
                tint = Color.White
            )
        }

        // 중앙 비디오 영역
        VideoBox(videoPath = videoData.videoPath, numComment = videoData.numComment)

        // 채널 정보
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(19.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(id = videoData.channelImage),
                contentDescription = "Channel Image",
                modifier = Modifier
                    .size(40.dp)
                    .clip(RoundedCornerShape(50))
            )
            Spacer(modifier = Modifier.width(12.dp))
            Text(
                text = "@${videoData.channelId}",
                fontSize = 16.sp,
                color = Color.White
            )
        }

        Text(
            text = videoData.videoTitle,
            fontSize = 18.sp,
            color = Color.White,
            modifier = Modifier.padding(start = 28.dp),
        )

    }
}
@Composable
fun VideoBox(videoPath: String, numComment: Int) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(600.dp)
            .background(Color.Black),
        contentAlignment = Alignment.Center
    ) {
        AndroidView(
            factory = { context ->
                VideoView(context).apply {
                    setVideoPath(videoPath)
                    start()
                }
            },
            modifier = Modifier.fillMaxSize()
        )

        // 오른쪽 아이콘
        Column(
            modifier = Modifier
                .align(Alignment.TopEnd) // 박스의 오른쪽 위 정렬
                .padding(top = 320.dp, end = 16.dp), // 여백 설정
            verticalArrangement = Arrangement.spacedBy(16.dp) // 아이콘 간 간격
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
                text = numComment.toString(),
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
}

@Preview
@Composable
fun HomePreView(){
    HomeView(
        videoData = VideoData(
            videoPath = "https://www.youtube.com/embed/sample_video",
            channelImage = R.drawable.facebook_logo,
            channelId = "NetflixKorea",
            videoTitle = "게임을 멈추려는 자, 성기훈 | 오징어 게임 시즌2",
            numComment = 300,
        )
    )
}

