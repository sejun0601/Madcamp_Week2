package com.example.madcamp_week2.ui

import android.graphics.BitmapFactory
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.madcamp_week2.MainViewModel
import com.example.madcamp_week2.R
import com.example.madcamp_week2.SearchViewModel
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.ByteArrayInputStream

@Composable
fun SearchView(videoData: MainViewModel.VideoState, searchViewModel: SearchViewModel, navHostController: NavHostController) {

    val query = remember { mutableStateOf("") }

    val systemUiController = rememberSystemUiController()
    val useDarkIcons = true  // 상태 표시줄 아이콘을 어둡게 사용할지 여부

    SideEffect {
        systemUiController.setStatusBarColor(
            color = Color.White,    // 원하는 상태 표시줄 색상
            darkIcons = useDarkIcons
        )
        systemUiController.setNavigationBarColor(
            color = Color.White,
            darkIcons = useDarkIcons
        )
    }

    // Filter videos: exclude videos with political keywords and match query
    val filteredVideos = remember(query.value) {
        videoData.data.filter { video ->
                    video.title.contains(query.value, ignoreCase = true)
        }
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(8.dp)
    ) {
        Spacer(modifier = Modifier.height(24.dp))
        SearchBar(query)
        Spacer(modifier = Modifier.height(24.dp))

        YoutubeIcon()
        Spacer(modifier = Modifier.height(16.dp))
        LazyVerticalGrid(
            columns = GridCells.Fixed(3),
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(4.dp),
            horizontalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            items(filteredVideos.size) { index ->
                ThumbnailImage(videoId = filteredVideos[index].video_id, navHostController)
            }
        }
    }

}
@Composable
fun SearchBar(query: androidx.compose.runtime.MutableState<String>) {

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(40.dp)
            .background(Color.LightGray, shape = RoundedCornerShape(8.dp)),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            painter = painterResource(id = R.drawable.search_icon), // 검색 아이콘 리소스
            contentDescription = "Search Icon",
            modifier = Modifier
                .padding(12.dp)
                .size(24.dp)
        )
        BasicTextField(
            value = query.value,
            onValueChange = { query.value = it },
            textStyle = TextStyle(fontSize = 16.sp),
            modifier = Modifier.fillMaxWidth(),
            decorationBox = { innerTextField ->
                if (query.value.isEmpty()) {
                    Text(
                        text = "검색", // 기본 텍스트
                        style = TextStyle(color = Color.Gray, fontSize = 16.sp)
                    )
                }
                innerTextField()
            }
        )
    }
}

@Composable
fun YoutubeIcon() {
    Icon(
        painter = painterResource(id = R.drawable.youtube_icon),
        contentDescription = "Youtube Icon",
        modifier = Modifier
            .size(26.dp)
            .padding(1.dp)
    )
}

@Composable
fun ThumbnailImage(videoId: String, navHostController: NavHostController) {
    val thumbnailUrl = "https://img.youtube.com/vi/$videoId/hqdefault.jpg"
    Box(
        modifier = Modifier.fillMaxSize().aspectRatio(1f).clickable { navHostController.navigate(Screen.OtherScreens.Detail.oRoute + "/${videoId}") },
        contentAlignment = Alignment.Center
    ) {
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(thumbnailUrl)
                .crossfade(true) // 페이드 애니메이션 적용
                .build(),
            contentDescription = "Thumbnail for video $videoId",
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )
    }
}

