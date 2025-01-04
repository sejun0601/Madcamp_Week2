package com.example.madcamp_week2.ui

import android.util.Log
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController


@Composable
fun ProfileView(navController: NavHostController) {
    Button(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(8.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = Color(0xFFD13739)
        ),
        onClick = {
            Log.d("NavigationTest", "Navigating to PlayView")
            navController.navigate(Screen.OtherScreens.Play.oRoute)
                  }, // Paly 화면으로 이동
    ) {
        Text(text = "트렌드 경쟁하기")
    }
}
