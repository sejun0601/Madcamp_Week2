package com.example.madcamp_week2.ui

import android.provider.Settings.Global.getString
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.madcamp_week2.R
import com.example.madcamp_week2.remote.apiService
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

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

    Column(
        modifier = Modifier.padding(bottom = 32.dp),
        horizontalAlignment = Alignment.CenterHorizontally) {

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
                modifier = Modifier.fillMaxWidth().padding(8.dp),
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

