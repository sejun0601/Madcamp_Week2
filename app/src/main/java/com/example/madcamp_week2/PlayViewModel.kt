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

    // Set the match ID when navigating
    fun setMatchId(matchId: Int) {
        _matchId.value = matchId
        fetchProblem(matchId)
    }

    // Fetch problem associated with matchId
    private fun fetchProblem(matchId: Int) {
        // Simulate fetching problem (e.g., from server or database)
        _problem.value = Problem(
            id = matchId,
            question = "Match $matchId: What is 2 + 2?",
            answer = "4"
        )
    }
}

data class Problem(val id: Int, val question: String, val answer: String)
