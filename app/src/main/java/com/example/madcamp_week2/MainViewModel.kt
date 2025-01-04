package com.example.madcamp_week2

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.madcamp_week2.ui.Screen

class MainViewModel : ViewModel(){

    private val _currentScreen: MutableState<Screen> = mutableStateOf(Screen.BottomScreen.Home)

    val currentScreen: MutableState<Screen>
        get() = _currentScreen

    fun setCurrentScreen(screen: Screen){
        _currentScreen.value = screen
    }

}