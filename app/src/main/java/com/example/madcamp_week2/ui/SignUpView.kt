package com.example.madcamp_week2.ui

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.madcamp_week2.data.models.SignUpRequest
import com.example.madcamp_week2.data.models.SignUpResponse
import retrofit2.*
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.POST
import android.content.Context
import android.content.SharedPreferences
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.madcamp_week2.SignUpViewModel
import com.example.madcamp_week2.remote.apiService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@Composable
fun SignUpView(signUpViewModel: SignUpViewModel, navHostController: NavHostController) {
    val signUpState = signUpViewModel.signUpState.value
    val context = LocalContext.current

    Box(
        modifier = Modifier.fillMaxSize().padding(16.dp),
    ) {

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 64.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = "회원가입", fontSize = 24.sp, color = Color.Black, fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.height(36.dp))
            OutlinedTextField(
                value = signUpState.username,
                onValueChange = { signUpViewModel.setUsername(it) },
                label = { Text("사용자 이름") },
                singleLine = true,
                modifier = Modifier.fillMaxWidth(0.95f)
            )

            Spacer(modifier = Modifier.height(8.dp))

            OutlinedTextField(
                value = signUpState.email,
                onValueChange = { signUpViewModel.setEmail(it) },
                label = { Text("email") },
                singleLine = true,
                modifier = Modifier.fillMaxWidth(0.95f)
            )

            Spacer(modifier = Modifier.height(8.dp))

            OutlinedTextField(
                value = signUpState.password,
                onValueChange = { signUpViewModel.setPassword(it) },
                label = { Text("비밀번호") },
                singleLine = true,
                visualTransformation = PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
                modifier = Modifier.fillMaxWidth(0.95f)
            )

            Spacer(modifier = Modifier.weight(1f))

            Button(
                onClick = {
                    signUpViewModel.performSignup(
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
                modifier = Modifier.size(80.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFFD13739)
                ),
                shape = RoundedCornerShape(32.dp),
                contentPadding = PaddingValues(0.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.ArrowForward,
                    contentDescription = null,
                    modifier = Modifier.size(30.dp)
                )
            }

            Spacer(Modifier.height(64.dp))
        }
    }
}
