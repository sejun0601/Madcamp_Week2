package com.example.madcamp_week2.ui

import android.widget.Toast
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.platform.LocalContext
import com.example.madcamp_week2.MatchHistoryViewModel

@Composable

fun MatchHistoryView(matchHistoryViewModel: MatchHistoryViewModel){

    val context = LocalContext.current
    val myMatches = matchHistoryViewModel.matchHistoryState.value.list

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

    LazyColumn() {
        items(myMatches.size){ index ->
            Row(){
                Text(text = myMatches[index].id.toString())
            }
        }

    }



}