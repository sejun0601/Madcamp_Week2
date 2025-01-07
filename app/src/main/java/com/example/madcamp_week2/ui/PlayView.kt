package com.example.madcamp_week2.ui

import WaitingViewModel
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.example.madcamp_week2.PlayViewModel
import com.example.madcamp_week2.R


@Composable
fun PlayView(waitingViewModel: WaitingViewModel, navController: NavHostController) {
    val context = LocalContext.current
    Log.d("NavigationTest", "PlayView Loaded")

    val answer = waitingViewModel.answer.value
    val matchData = waitingViewModel.matchState
    // 전체 화면 배경 설정
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.LightGray),
        contentAlignment = Alignment.Center
    ) {
        // 카드 형식의 컨텐츠
        Column(
            modifier = Modifier
                .padding(16.dp)
                .background(Color.White, RoundedCornerShape(16.dp))
                .border(1.dp, Color.LightGray, RoundedCornerShape(16.dp))
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // 질문 텍스트
            matchData.value.matchData?.match?.problem?.let {
                Text(
                    text = it.question,
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color.Black
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            // 입력 필드
            OutlinedTextField(
                value = answer,
                onValueChange = {waitingViewModel.setAnswer(it)},
                placeholder = { Text("정답 입력") },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(8.dp)
            )

            Button(onClick = {
                waitingViewModel.performAnswer(
                    onResult = { state ->
                        if(state == "승리"){
                            Log.d("PlayView", state)
                            Toast.makeText(context, state, Toast.LENGTH_SHORT).show()
                            navController.navigate(Screen.BottomScreen.Profile.bRoute) {
                                popUpTo("match_flow") {
                                    inclusive = true // "match_flow" 서브 그래프 전체 제거
                                }
                                launchSingleTop = true
                            }
                        }else if(state == "패배"){
                            Log.d("PlayView", state)
                            Toast.makeText(context, "패배", Toast.LENGTH_SHORT).show()
                            navController.navigate(Screen.BottomScreen.Profile.bRoute) {
                                popUpTo("match_flow") {
                                    inclusive = true // "match_flow" 서브 그래프 전체 제거
                                }
                                launchSingleTop = true
                            }
                        }else if(state == "틀렸습니다"){
                            Log.d("PlayView", state)
                            Toast.makeText(context, state, Toast.LENGTH_SHORT).show()
                        }else{
                            Log.d("PlayView", state)
                            Toast.makeText(context, state, Toast.LENGTH_SHORT).show()
                            navController.navigate(Screen.BottomScreen.Profile.bRoute) {
                                popUpTo("match_flow") {
                                    inclusive = true // "match_flow" 서브 그래프 전체 제거
                                }
                                launchSingleTop = true
                            }
                        }

                    },
                    onError = {

                    }
                )
            }) {
                Text("정답 제출")
            }

        }
    }
}


