package com.example.madcamp_week2.ui

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.madcamp_week2.ASSALoginViewModel

@Composable

fun ASSALoginView( assaLoginViewModel: ASSALoginViewModel, navHostController: NavHostController){

    val loginState = assaLoginViewModel.loginState.value

    val context = LocalContext.current

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = "Login", fontSize = 24.sp, color = Color.Black, fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.height(36.dp))
            // Email Input
            TextField(
                value = loginState.username,
                onValueChange = { assaLoginViewModel.setUsername(it) },
                label = { Text("User Name") },
                singleLine = true,
                modifier = Modifier
                    .fillMaxWidth(0.95f)
                    .height(48.dp)
            )

            Spacer(modifier = Modifier.height(8.dp))
            // Password Input
            TextField(
                value = loginState.password,
                onValueChange = { assaLoginViewModel.setPassword(it) },
                label = { Text("Password") },
                singleLine = true,
                modifier = Modifier
                    .fillMaxWidth(0.95f)
                    .height(48.dp)
            )
            Spacer(modifier = Modifier.height(16.dp))
            // Login Button
            Button(onClick = {
                assaLoginViewModel.performLogin(
                    onResult = { detail ->
                        Toast.makeText(context, detail, Toast.LENGTH_SHORT).show()
                        navHostController.navigate(Screen.OtherScreens.Main.oRoute)
                    },
                    onError = { error ->
                        Toast.makeText(context, "Error: ${error.message}", Toast.LENGTH_SHORT)
                            .show()
                    }
                )
            },
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFFD13739)
                ),
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(text = "Login")
            }

            // Sign Up Button
            Button(onClick = {
                navHostController.navigate(Screen.OtherScreens.Signup.oRoute)
            },
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Black
                ),
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(text = "Sign Up")
            }
        }
    }

}


