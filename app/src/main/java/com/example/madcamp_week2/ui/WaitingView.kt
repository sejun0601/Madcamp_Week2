package com.example.madcamp_week2.ui

import WaitingViewModel
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController

@Composable
fun WaitingView(
    waitingViewModel: WaitingViewModel = viewModel(),
    navHostController: NavHostController
) {
    val waitingStatus by waitingViewModel.waitingStatus.collectAsState()

    LaunchedEffect(Unit) {
        waitingViewModel.startMatching(navHostController)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF101010)),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        // Title
        Text(
            text = "게임 매칭 대기",
            fontSize = 24.sp,
            color = Color.White,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(32.dp))

        // Circular Progress Indicator
        CircularProgressIndicator(
            color = Color.White,
            strokeWidth = 4.dp,
            modifier = Modifier.size(48.dp)
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Waiting Status Text
        Text(
            text = waitingStatus,
            fontSize = 16.sp,
            color = Color.Gray,
            textAlign = TextAlign.Center
        )

        /*
        Spacer(modifier = Modifier.height(64.dp))

        // Cancel Button
        Button(
            onClick = { playViewModel.cancelMatching() },
            shape = CircleShape,
            colors = ButtonDefaults.buttonColors(containerColor = Color.Red),
            modifier = Modifier
                .width(200.dp)
                .height(48.dp)
        ) {
            Text(text = "취소", color = Color.White, fontSize = 16.sp)
        }
        */

    }
}

