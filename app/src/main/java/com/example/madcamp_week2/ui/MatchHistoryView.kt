package com.example.madcamp_week2.ui
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.madcamp_week2.R

data class BattleRecord(
    val myUsername: String,
    val opponentUsername: String,
    val winner: String
)

@Composable
fun MatchHistoryView(records: List<BattleRecord>, myUsername: String) {

    Column {
        Text(
            text = "Match History",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Black,
            modifier = Modifier
                .padding(start = 24.dp, top = 40.dp, end = 24.dp, bottom = 26.dp)
        )
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFFF7F7F7)) // 배경 색
                .padding(horizontal = 16.dp)
        ) {
            items(records) { record ->
                HistoryItem(
                    record = record,
                    myUsername = myUsername
                )
            }
        }
    }
}

@Composable
fun HistoryItem(record: BattleRecord, myUsername: String) {
    val winnerColor = if (record.winner == record.myUsername) Color(0xFF4CAF50) else Color(0xFF1976D2) // 이긴 경우 초록, 진 경우 파랑

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(4.dp)
            .background(Color.White, shape = RoundedCornerShape(12.dp))
            .padding(12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Spacer(modifier = Modifier.width(8.dp))
        // 내 이름 + 승리 아이콘
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(
                text = record.myUsername,
                fontSize = 18.sp,
                fontWeight = if (record.winner == record.myUsername) FontWeight.Bold else FontWeight.Normal,
                color = if (record.winner == record.myUsername) winnerColor else Color.Black
            )
            if (record.winner == record.myUsername) {
                Spacer(modifier = Modifier.width(4.dp))
                Image(
                    painter = painterResource(R.drawable.winner),
                    contentDescription = "Winner Icon",
                    modifier = Modifier.size(18.dp),
                )
            }
        }

        Spacer(modifier = Modifier.width(8.dp))

        // "VS" 표시
        Text(
            text = "VS",
            fontSize = 16.sp,
            color = Color.Gray
        )

        Spacer(modifier = Modifier.width(8.dp))

        // 상대방 이름 + 승리 아이콘
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(
                text = record.opponentUsername,
                fontSize = 18.sp,
                fontWeight = if (record.winner == record.opponentUsername) FontWeight.Bold else FontWeight.Normal,
                color = if (record.winner == record.opponentUsername) winnerColor else Color.Black
            )
            if (record.winner == record.opponentUsername) {
                Spacer(modifier = Modifier.width(4.dp))
                Image(
                    painter = painterResource(R.drawable.winner),
                    contentDescription = "Winner Icon",
                    modifier = Modifier.size(18.dp)
                )
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun PreviewBattleRecordList() {
    val sampleRecords = listOf(
        BattleRecord("이병헌", "이정재", "이정재"),
        BattleRecord("이병헌", "정호영", "정호영"),
        BattleRecord("이병헌", "전재준", "이병헌"),
        BattleRecord("이병헌", "공유", "공유")
    )
    MatchHistoryView(records = sampleRecords, myUsername = "이병헌")
}