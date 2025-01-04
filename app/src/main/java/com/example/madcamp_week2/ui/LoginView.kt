package com.example.madcamp_week2.ui

import android.annotation.SuppressLint
import android.app.Activity
import android.content.pm.ActivityInfo
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
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
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.navigation.NavHostController
import com.example.madcamp_week2.R
import com.example.madcamp_week2.remote.apiService
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.options.IFramePlayerOptions
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


@Composable
fun LoginView(navHostController: NavHostController){

    val activity = LocalContext.current as ComponentActivity

    val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
        .requestIdToken("839534423473-gr7ketdfqujrem8ur9tjtgu1h4p8728s.apps.googleusercontent.com") // Google Cloud Console의 Web Client ID를 입력
        .requestEmail()
        .build()

    val googleSignInClient = GoogleSignIn.getClient(activity, gso)

    val launcher = rememberLauncherForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        val task = GoogleSignIn.getSignedInAccountFromIntent(result.data)
        try {
            val account = task.getResult(ApiException::class.java)
            val idToken = account?.idToken
            if (idToken != null) {
                Log.d("GoogleSignIn", "ID Token: $idToken")
                // 여기에 백엔드로 토큰을 보내는 로직 추가
                sendTokenToBackend(idToken, navHostController)
            }
        } catch (e: ApiException) {
            Log.e("GoogleSignIn", "Sign-in failed", e)
        }
    }

    Box() {

        FullScreenShortsPlayer("TOdd_wdKfgM")


        Column(
            modifier = Modifier.padding(bottom = 32.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Spacer(Modifier.weight(1f))

            Text("ASSA에 오신 것을", color = Color.White)
            Text("환영합니다", color = Color.White)
            Text("로그인하여 최신 트렌드를 확인하세요", color = Color.White)
            Text("친구들과 트렌드 성적으로 경쟁해 보세요", color = Color.White)

            Column(
                modifier = Modifier.padding(32.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                Button(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(8.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFFD13739)
                    ),
                    onClick = {
                        navHostController.navigate(Screen.OtherScreens.Main.oRoute)
                    }
                ) {
                    Text("로그인")
                }

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {

                    Button(
                        modifier = Modifier
                            .fillMaxWidth(),
                        shape = RoundedCornerShape(8.dp),
                        contentPadding = PaddingValues(0.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color.White
                        ),
                        onClick = {

                            val signInIntent = googleSignInClient.signInIntent
                            launcher.launch(signInIntent)

                        }
                    )
                    {
                        Icon(
                            painter = painterResource(id = R.drawable.google_icon),
                            contentDescription = "Google Icon", // 아이콘 색상 (필요에 따라 변경 가능
                            tint = Color.Unspecified,
                            modifier = Modifier.size(24.dp)
                        )
                    }
//
//                Button(
//                    modifier = Modifier
//                        .size(40.dp), onClick = {}, shape = RoundedCornerShape(8.dp),
//                    contentPadding = PaddingValues(0.dp),
//                    colors = ButtonDefaults.buttonColors(
//                        containerColor = Color(0xFF1877F2)
//                    )
//                ) {
//                    Icon(
//                        painter = painterResource(id = R.drawable.facebook_logo),
//                        contentDescription = "Facebook Icon", // 아이콘 색상 (필요에 따라 변경 가능)
//                        tint = Color.Unspecified,
//                        modifier = Modifier.size(24.dp)
//                    )
//                }
//
//
//
//                Button(
//                    modifier = Modifier
//                        .size(40.dp), onClick = {}, shape = RoundedCornerShape(8.dp),
//                    contentPadding = PaddingValues(0.dp),
//                    colors = ButtonDefaults.buttonColors(
//                        containerColor = Color.Transparent
//                    )
//                ) {
//
//                    Icon(
//                        painter = painterResource(id = R.drawable.instragram_logo),
//                        contentDescription = "Instagram Icon", // 아이콘 색상 (필요에 따라 변경 가능)
//                        tint = Color.Unspecified
//                    )
//                }
//                Button(
//                    modifier = Modifier
//                        .size(40.dp), onClick = {}, shape = RoundedCornerShape(8.dp),
//                    contentPadding = PaddingValues(0.dp),
//                    colors = ButtonDefaults.buttonColors(
//                        containerColor = Color(0xFF02C75A)
//                    )
//                ) {
//                    Icon(
//                        painter = painterResource(id = R.drawable.naver_logo),
//                        contentDescription = "Naver Icon", // 아이콘 색상 (필요에 따라 변경 가능)
//                        tint = Color.Unspecified,
//                        modifier = Modifier.size(24.dp)
//                    )
//                }
//                Button(
//                    modifier = Modifier
//                        .size(40.dp), onClick = {}, shape = RoundedCornerShape(8.dp),
//                    contentPadding = PaddingValues(0.dp),
//                    colors = ButtonDefaults.buttonColors(
//                        containerColor = Color(0xFFFEE500)
//                    )) {
//                    Icon(
//                        painter = painterResource(id = R.drawable.kakao_logo),
//                        contentDescription = "Kakao Icon", // 아이콘 색상 (필요에 따라 변경 가능)
//                        tint = Color.Unspecified,
//                        modifier = Modifier.size(24.dp)
//                    )
//                }
                }

            }
        }
    }
}

fun sendTokenToBackend(idToken: String, navHostController: NavHostController) {

    val call = apiService.loginWithGoogle(idToken)

    call.enqueue(object : Callback<Void> {
        override fun onResponse(call: Call<Void>, response: Response<Void>) {
            if (response.isSuccessful) {
                Log.d("GoogleSignIn", "Login successful")
                // 로그인 성공 시 메인 화면으로 이동
                navHostController.navigate(Screen.OtherScreens.Main.oRoute)
            } else {
                Log.e("GoogleSignIn", "Login failed: ${response.errorBody()?.string()}")
            }
        }

        override fun onFailure(call: Call<Void>, t: Throwable) {
            Log.e("GoogleSignIn", "Network error", t)
        }
    })
}

@SuppressLint("ClickableViewAccessibility")
@Composable
fun FullScreenShortsPlayer(videoId: String) {
    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current

    val activity = context as? Activity
    val previousOrientation = activity?.requestedOrientation

    // Lock orientation to portrait while this composable is visible
    DisposableEffect(Unit) {
        activity?.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_SENSOR_PORTRAIT
        onDispose {
            activity?.requestedOrientation = previousOrientation ?: ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED
        }
    }

    AndroidView(
        modifier = Modifier.fillMaxSize(),
        factory = { localContext ->
            // Create a new FrameLayout container
            val frameLayout = FrameLayout(localContext).apply {
                layoutParams = ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT
                )
            }

            val youTubePlayerView = YouTubePlayerView(localContext).apply {
                enableAutomaticInitialization = false
                layoutParams = ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT
                )
            }

            // Add YouTubePlayerView to the FrameLayout
            frameLayout.addView(youTubePlayerView)

            lifecycleOwner.lifecycle.addObserver(youTubePlayerView)

            val iFramePlayerOptions = IFramePlayerOptions.Builder().controls(0).build()

            youTubePlayerView.initialize(object : AbstractYouTubePlayerListener() {
                override fun onReady(youTubePlayer: YouTubePlayer) {
                    val emptyView = View(localContext)
                    youTubePlayerView.setCustomPlayerUi(emptyView)

                    youTubePlayer.loadVideo(videoId, 0f)

                    // Track playback progress to detect the end of the video
                    youTubePlayer.addListener(object : AbstractYouTubePlayerListener() {
                        private var videoDuration: Float = 0f

                        override fun onVideoDuration(youTubePlayer: YouTubePlayer, duration: Float) {
                            videoDuration = duration
                        }

                        override fun onCurrentSecond(youTubePlayer: YouTubePlayer, second: Float) {
                            if (videoDuration > 0 && second >= videoDuration - 0.5f) {
                                // Video is close to ending; restart playback
                                youTubePlayer.seekTo(0f)
                                youTubePlayer.play()
                            }
                        }
                    })
                }
            }, iFramePlayerOptions)

            // Add a touch-blocking layer on top
            val touchBlockerView = View(localContext).apply {
                layoutParams = ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT
                )
                setOnTouchListener { _, _ -> true } // Consume all touch events
            }

            // Add the touch blocker last to overlay the player
            frameLayout.addView(touchBlockerView)

            frameLayout
        },
        update = {
            // Ensure proper update handling
        }
    )
}





