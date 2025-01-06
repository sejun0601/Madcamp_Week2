package com.example.madcamp_week2.ui

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
import androidx.compose.runtime.produceState
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.madcamp_week2.MainActivity
import com.example.madcamp_week2.ProfileViewModel
import com.example.madcamp_week2.R
import com.example.madcamp_week2.UserProfileState

@Composable
fun ProfileView(navHostController: NavHostController, profileViewModel: ProfileViewModel) {

    val userProfileState = profileViewModel.userProfileState.value

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


    Column {
        ProfileTab(userProfileState, profileViewModel, navHostController)

        HorizontalDivider(
            modifier = Modifier.padding(8.dp)
        )

        RankTab(navHostController, userProfileState)

        HorizontalDivider(
            modifier = Modifier.padding(8.dp)
        )

        FavoriteTab()

    }

}

@Composable
fun ProfileTab(userProfileState: UserProfileState, profileViewModel: ProfileViewModel, navHostController: NavHostController){

    val context = LocalContext.current

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {

        Image(
            modifier = Modifier
                .size(96.dp)
                .clip(CircleShape)
                .background(Color.Gray)
                .padding(8.dp),
            painter = painterResource(id = R.drawable.profile_icon),
            contentDescription = null
        )

        Column {

            Text(
                modifier = Modifier.padding(start = 8.dp),
                text = userProfileState.username ,
            )
            Text(
                modifier = Modifier.padding(start = 8.dp),
                text = userProfileState.email,
            )

            Row(
                modifier = Modifier.padding(start = 8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {

                Icon(
                    painter = painterResource(R.drawable.wr_challenger),
                    "",
                    tint = Color.Unspecified
                )

                Text(
                    modifier = Modifier.padding(8.dp),
                    text = "챌린저 I")

            }

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
            contentDescription = null
        )

    }


}

@Composable
fun RankTab(navHostController: NavHostController, userProfileState: UserProfileState){
    Column (
        modifier = Modifier.padding(8.dp)
    ) {

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(8.dp))
                .background(Color(0xFFD9D9D9))
        ) {
            Row(
                modifier = Modifier.padding(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {

                Icon(
                    modifier = Modifier.size(60.dp),
                    painter = painterResource(R.drawable.wr_challenger),
                    contentDescription = "",
                    tint = Color.Unspecified
                )

                Text(
                    modifier = Modifier.padding(8.dp),
                    text = "챌린저 I",
                    fontSize = 20.sp
                )

                Spacer(Modifier.weight(1f))

                Text(
                    text = userProfileState.rankScore,
                    fontSize = 30.sp
                )
            }

            HorizontalDivider(
            )

            Button(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 16.dp, bottom = 8.dp, top = 8.dp, end = 16.dp),
                shape = RoundedCornerShape(8.dp),
                onClick = {}
            ) {
                Text("대전 기록")
            }

        }




        Button(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(8.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFFD13739)
            ),
            onClick = {
                navHostController.navigate(Screen.OtherScreens.Waiting.oRoute)
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

