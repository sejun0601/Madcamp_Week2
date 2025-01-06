package com.example.madcamp_week2.ui

import android.widget.Toast
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.madcamp_week2.SignUpViewModel


@Composable
fun SignUpView(signUpViewModel: SignUpViewModel, navHostController: NavHostController) {
    val signUpState = signUpViewModel.signUpState.value
    val context = LocalContext.current

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center
    ) {
        Text(text = "Sign Up", fontSize = 24.sp, color = Color.Black, fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.height(36.dp))

        TextField(
            value = signUpState.username,
            onValueChange = { signUpViewModel.setUsername(it) },
            label = { Text("User Name") },
            singleLine = true,
            modifier = Modifier
                .fillMaxWidth(0.95f)
                .height(48.dp)
        )

        Spacer(modifier = Modifier.height(8.dp))

        TextField(
            value = signUpState.email,
            onValueChange = { signUpViewModel.setEmail(it)},
            label = { Text("Email") },
            singleLine = true,
            modifier = Modifier
                .fillMaxWidth(0.95f)
                .height(48.dp)
        )

        Spacer(modifier = Modifier.height(8.dp))

        TextField(
            value = signUpState.password,
            onValueChange = {signUpViewModel.setPassword(it) },
            label = { Text("Password") },
            singleLine = true,
            visualTransformation = PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
            modifier = Modifier
                .fillMaxWidth(0.95f)
                .height(48.dp)
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
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFFD13739)
            ),
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Sign Up")
        }
    }
}
