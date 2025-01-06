package com.example.madcamp_week2

import WaitingViewModel
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
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.madcamp_week2.ui.ASSALoginView
import com.example.madcamp_week2.ui.LoginView
import com.example.madcamp_week2.ui.MainView
import com.example.madcamp_week2.ui.Screen
import com.example.madcamp_week2.ui.PlayView
import com.example.madcamp_week2.ui.SignUpView
import com.example.madcamp_week2.ui.WaitingView
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
    val assaLoginViewModel:ASSALoginViewModel = viewModel()
    val signUpViewModel: SignUpViewModel = viewModel()
    val waitingViewModel: WaitingViewModel = viewModel()
    val playViewModel: PlayViewModel = viewModel()

    NavHost(navController, startDestination = Screen.OtherScreens.Login.oRoute) {
        composable(Screen.OtherScreens.Login.oRoute) { LoginView(navController) }
        composable(Screen.OtherScreens.Main.oRoute) { MainView() }
        composable(Screen.OtherScreens.Play.oRoute) { backStackEntry ->
            val matchId = backStackEntry.arguments?.getString("matchId")?.toIntOrNull()
            if (matchId != null) {
                playViewModel.setMatchId(matchId)
                PlayView(playViewModel = playViewModel)
            }
        }
        composable(Screen.OtherScreens.Waiting.oRoute) { WaitingView(waitingViewModel, navController) }
        composable(Screen.OtherScreens.ASSALogin.oRoute) { ASSALoginView(assaLoginViewModel, navController) }
        composable(Screen.OtherScreens.Signup.oRoute) { SignUpView(signUpViewModel, navController) }
    }
}