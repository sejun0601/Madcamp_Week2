package com.example.madcamp_week2.ui

import androidx.annotation.DrawableRes
import com.example.madcamp_week2.R

sealed class Screen(val title: String, val route: String) {

    sealed class BottomScreen(
        val bTitle: String, val bRoute: String, @DrawableRes val bIcon: Int
    ):Screen(bTitle, bRoute){
        object Home: BottomScreen("Home", "home", R.drawable.home_icon)
        object Search: BottomScreen("Search", "search", R.drawable.search_icon)
        object Profile: BottomScreen("Profile", "profile", R.drawable.profile_icon)
    }

    sealed class OtherScreens(
        val oTitle: String, val oRoute: String
    ):Screen(oTitle, oRoute){
        object Login: OtherScreens("Login", "login")
        object Register: OtherScreens("Register", "register")
        object Detail: OtherScreens("Detail", "detail")
        object Main: OtherScreens("Main", "main")
        object Play : OtherScreens("Play", "matches/{matchId}")
        object Waiting: OtherScreens("Waiting", "waiting")
        object ASSALogin: OtherScreens("ASSALogin","assaLogin")
        object Signup: OtherScreens("Signup", "signup")
        object MatchHistory : OtherScreens( "MatchHistory", "matchHistory")

    }


}

val screensInBottomBar = listOf(
    Screen.BottomScreen.Home,
    Screen.BottomScreen.Search,
    Screen.BottomScreen.Profile
)