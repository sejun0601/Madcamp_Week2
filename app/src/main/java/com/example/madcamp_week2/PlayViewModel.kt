package com.example.madcamp_week2

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flow

class PlayViewModel : ViewModel() {

    private val _matchId = MutableStateFlow<Int?>(null)
    val matchId: StateFlow<Int?> get() = _matchId

    private val _problem = MutableStateFlow<Problem?>(null)
    val problem: StateFlow<Problem?> get() = _problem

}

data class Problem(val id: Int, val question: String, val answer: String)
