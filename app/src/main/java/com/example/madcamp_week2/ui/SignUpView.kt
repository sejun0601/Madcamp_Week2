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

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center
    ) {
        TextField(
            value = signUpState.username,
            onValueChange = { signUpViewModel.setUsername(it) },
            label = { Text("사용자 이름") },
            singleLine = true,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        TextField(
            value = signUpState.email,
            onValueChange = { signUpViewModel.setEmail(it)},
            label = { Text("email") },
            singleLine = true,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        TextField(
            value = signUpState.password,
            onValueChange = {signUpViewModel.setPassword(it) },
            label = { Text("비밀번호") },
            singleLine = true,
            visualTransformation = PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

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
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("회원가입")
        }
    }
}
