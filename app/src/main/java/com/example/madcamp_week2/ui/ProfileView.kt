package com.example.madcamp_week2.ui

import WaitingViewModel
import android.content.Context
import android.content.Intent
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyHorizontalGrid
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Logout
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.produceState
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.madcamp_week2.MainActivity
import com.example.madcamp_week2.ProfileViewModel
import com.example.madcamp_week2.R
import com.example.madcamp_week2.UserProfileState
import com.google.accompanist.systemuicontroller.rememberSystemUiController

@Composable
fun ProfileView(navHostController: NavHostController, profileViewModel: ProfileViewModel) {

    val userProfileState = profileViewModel.userProfileState.value
    val context = LocalContext.current
    LaunchedEffect(userProfileState) {
        profileViewModel.performGetProfile(
            onResult = { result ->
                Log.d("ProfileView", result)
            },
            onError = { error ->
                Log.e("ProfileView", "Error: ${error.message}")
            }
        )
    }

    val systemUiController = rememberSystemUiController()
    val useDarkIcons = false  // 상태 표시줄 아이콘을 어둡게 사용할지 여부

    SideEffect {
        systemUiController.setStatusBarColor(
            color = Color(0xff141213),    // 원하는 상태 표시줄 색상
            darkIcons = useDarkIcons
        )
        systemUiController.setNavigationBarColor(
            color = Color(0xff141213),
            darkIcons = useDarkIcons
        )
    }


    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xff141213))
    ) {

        Text("정보",
            fontSize = 40.sp,
            modifier = Modifier.padding(24.dp),
            color = Color.White,
        )

        ProfileTab(context, userProfileState, profileViewModel, navHostController)

        RankTab(context, navHostController, userProfileState, userProfileState.username)


    }

}

@Composable
fun ProfileTab(context: Context,userProfileState: UserProfileState, profileViewModel: ProfileViewModel, navHostController: NavHostController){



    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 16.dp, start = 16.dp, end = 16.dp, bottom = 16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {

        Column {

            Text(
                modifier = Modifier.padding(start = 8.dp),
                text = userProfileState.username.substringBefore("@") ,
                color = Color.White,
                fontSize = 25.sp
            )
            Text(
                modifier = Modifier.padding(start = 8.dp),
                text = userProfileState.email,
                color = Color(0xff474647),
                fontSize = 15.sp
            )

            Text(
                "온라인",
                color = Color(0xFF1DCA5F),
                modifier = Modifier.padding(start = 8.dp),
                fontSize = 15.sp
            )

        }
        
        Spacer(Modifier.weight(1f))
    
        Icon(
            modifier = Modifier.clickable {
                profileViewModel.performLogout(
                    onResult = { response ->
                        if(response){
                            Toast.makeText(context, "로그아웃 성공", Toast.LENGTH_SHORT).show()
                            val intent = Intent(context, MainActivity::class.java)
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
                            context.startActivity(intent)

                        }else{
                            Toast.makeText(context, "로그아웃 실패", Toast.LENGTH_SHORT).show()
                        }
                    },
                    onError = {
                            Toast.makeText(context, "로그아웃 에러", Toast.LENGTH_SHORT).show()
                    }
                )
            },
            imageVector = Icons.Filled.Logout,
            contentDescription = null,
            tint = Color.White
        )

    }


}

@Composable
fun RankTab(context: Context,navHostController: NavHostController, userProfileState: UserProfileState, myUserName:String){

    val rankScore = userProfileState.rankScore.toIntOrNull() ?:0

    val (rankIconResource, rankName) = when (rankScore) {
        in 0 until 100 -> R.drawable.wr_iron to "아이언"
        in 100 until 200 -> R.drawable.wr_bronze to "브론즈"
        in 200 until 300 -> R.drawable.wr_silver to "실버"
        in 300 until 400 -> R.drawable.wr_gold to "골드"
        in 400 until 500 -> R.drawable.wr_platinum to "플래티넘"
        in 500 until 600 -> R.drawable.wr_diamond to "다이아몬드"
        in 600 until 700 -> R.drawable.wr_master to "마스터"
        in 700 until 800 -> R.drawable.wr_grand_master to "그랜드마스터"
        else -> R.drawable.wr_challenger to "챌린저"
    }

    Column (
        modifier = Modifier.padding(8.dp)
    ) {

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(8.dp))
                .background(Color(0xFF201E1F))
        ) {
            Row(
                modifier = Modifier.padding(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {

                Icon(
                    modifier = Modifier.size(60.dp),
                    painter = painterResource(rankIconResource),
                    contentDescription = "",
                    tint = Color.Unspecified
                )

                Text(
                    modifier = Modifier.padding(8.dp),
                    text = rankName,
                    fontSize = 20.sp,
                    color = Color.White
                )

                Spacer(Modifier.weight(1f))

                Text(
                    text = "${userProfileState.rankScore} P",
                    fontSize = 30.sp,
                    color = Color.White
                )
            }



            Button(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 16.dp, bottom = 8.dp, top = 8.dp, end = 16.dp),
                shape = RoundedCornerShape(8.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF333132)
                ),
                onClick = {
                    navHostController.navigate(Screen.OtherScreens.MatchHistory.oRoute + "/${myUserName}")
                }
            ) {
                Text("대전 기록")
            }

        }




        Button(
            modifier = Modifier.fillMaxWidth().padding(8.dp),
            shape = RoundedCornerShape(8.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFFD13739)
            ),
            onClick = {

                navHostController.navigate("match_flow")

            },
        ) {

            Text(
                text = "트렌드 경쟁하기"
            )

        }
    }

    }


@Composable
fun FavoriteTab(){

    Column(
        modifier = Modifier.padding(8.dp)
    ) {
        Text("즐겨찾기",
            modifier = Modifier.padding(8.dp),
            fontSize = 15.sp)

        LazyVerticalGrid(
            columns = GridCells.Fixed(3),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ){items(50){ index ->

            Box(
                modifier = Modifier.fillMaxSize().aspectRatio(1f),
                contentAlignment = Alignment.Center
            ) {


                Image(
                    modifier = Modifier
                        .fillMaxSize(),
                    painter = painterResource(id = R.drawable.instragram_logo),
                    contentDescription = null,
                )

            }

        }

        }

    }

}

