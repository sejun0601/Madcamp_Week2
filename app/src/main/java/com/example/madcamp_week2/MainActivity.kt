package com.example.madcamp_week2

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.madcamp_week2.ui.LoginView
import com.example.madcamp_week2.ui.MainView
import com.example.madcamp_week2.ui.Screen
import com.example.madcamp_week2.ui.PlayView
import com.example.madcamp_week2.ui.theme.Madcamp_Week2Theme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Madcamp_Week2Theme {
                Surface(modifier = Modifier.fillMaxSize()) {
                    App()
                }
            }
        }
    }
}

@Composable
fun App(){
    val navController = rememberNavController()

    NavHost(navController, startDestination = Screen.OtherScreens.Login.oRoute) {
        composable(Screen.OtherScreens.Login.oRoute) { LoginView(navController) }
        composable(Screen.OtherScreens.Main.oRoute) { MainView() }
        composable(Screen.OtherScreens.Play.oRoute) { PlayView() }
    }
}