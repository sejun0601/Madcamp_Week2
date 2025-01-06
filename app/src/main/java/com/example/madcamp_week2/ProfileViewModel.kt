package com.example.madcamp_week2

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel

class ProfileViewModel : ViewModel(){

    private val _userState = mutableStateOf(UserState())

}

data class UserState(
    val username: String = "",
    val email: String = ""
)