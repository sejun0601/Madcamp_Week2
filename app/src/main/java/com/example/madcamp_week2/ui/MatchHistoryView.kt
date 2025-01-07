package com.example.madcamp_week2.ui

import android.os.Build
import android.widget.Toast
import androidx.annotation.RequiresApi
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
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.motionEventSpy
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavBackStackEntry
import com.example.madcamp_week2.MatchHistoryViewModel
import com.example.madcamp_week2.R
import com.example.madcamp_week2.remote.Match
import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter

@RequiresApi(Build.VERSION_CODES.O)
@Composable

fun MatchHistoryView(matchHistoryViewModel: MatchHistoryViewModel, backStackEntry: NavBackStackEntry){

    val context = LocalContext.current
    val myMatches = matchHistoryViewModel.matchHistoryState.value.list
    val myUsername = backStackEntry.arguments?.getString("myUserName") ?: ""

    LaunchedEffect(Unit) {
        matchHistoryViewModel.performGetMatches(
            onResult = {
                Toast.makeText(context, "대전기록을 불러왔습니다",Toast.LENGTH_SHORT ).show()
            },
            onError = {
                Toast.makeText(context, "대전기록을 불러오는 데 실패했습니다",Toast.LENGTH_SHORT ).show()
            }
        )
    }

    Column(
        modifier = Modifier.background(Color(0xFF141213))
    ) {
        Text(
            text = "대전 기록",
            fontSize = 32.sp,
            fontWeight = FontWeight.Bold,
            color = Color.White,
            modifier = Modifier
                .padding(16.dp)
        )
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFF141213)), // 배경 색,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            items(myMatches.size) { index ->

                HistoryItem(
                    record = myMatches[index],
                    myUsername = myUsername
                )
            }
        }
    }

}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun HistoryItem(record: Match, myUsername: String) {
    val winnerName = record.winner?.username
    var opponentName = ""
    val winnerColor = if (winnerName == myUsername) Color(0xFF4CAF50) else Color(0xFF1976D2) // 이긴 경우 초록, 진 경우 파랑
    if (record.player1.username == myUsername){
        opponentName = record.player2.username
    }else{
        opponentName = record.player1.username
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .height(100.dp)
            .padding(horizontal = 16.dp)
            .background(Color(0xFF201e1f), shape = RoundedCornerShape(8.dp)),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
        ){
            Column (
                modifier = Modifier.padding(16.dp)
            ){
                Text(opponentName, color = Color.White)
                Text(formatIsoTimestamp(record.started_at), color = Color(0xFF454545))
            }

            Spacer(Modifier.weight(1f))

            if(winnerName == myUsername) {
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(16.dp))
                        .background(Color(0xFF0FC2E3))
                ){
                    Text("승리", color = Color.Black, modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp ))
                }
            }else{
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(16.dp))
                        .background(Color(0xFFD53237))
                ){
                    Text("패배", color = Color.White, modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp ))
                }
            }

            Spacer(Modifier.width(16.dp))

        }

        // 상대방 이름 + 승리 아이콘
    }
}

@RequiresApi(Build.VERSION_CODES.O)
fun formatIsoTimestamp(isoString: String): String {
    // ISO 8601 문자열을 Instant로 파싱
    val instant = Instant.parse(isoString)

    // 시스템 기본 시간대에 맞게 포맷터 생성 (원하는 형식으로 지정 가능)
    val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
        .withZone(ZoneId.systemDefault())

    // Instant를 지정된 형식의 문자열로 변환
    return formatter.format(instant)
}