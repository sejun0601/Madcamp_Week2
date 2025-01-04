package com.example.madcamp_week2.ui

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.unit.sp
import com.example.madcamp_week2.R

@Composable
fun ProfileView() {

    Column {
        ProfileTab()

        HorizontalDivider(
            modifier = Modifier.padding(8.dp)
        )

        RankTab()

        HorizontalDivider(
            modifier = Modifier.padding(8.dp)
        )

        FavoriteTab()

    }

}

@Composable
fun ProfileTab(){

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
                text = "이병헌",
            )
            Text(
                modifier = Modifier.padding(start = 8.dp),
                text = "leeBH@email.com",
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
            imageVector = Icons.Filled.MoreVert,
            contentDescription = null
        )

    }


}

@Composable
fun RankTab(){
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
                    text = "300 P",
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
            onClick = {},
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


@Preview(showBackground = true)
@Composable
fun ProfilePreView(){
    ProfileView()
}