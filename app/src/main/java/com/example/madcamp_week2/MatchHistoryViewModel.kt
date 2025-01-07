package com.example.madcamp_week2

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.madcamp_week2.remote.Match
import com.example.madcamp_week2.remote.MatchResponse
import com.example.madcamp_week2.remote.fetchCSRFToken
import com.example.madcamp_week2.remote.getMatchHistory
import com.example.madcamp_week2.remote.getProfile
import kotlinx.coroutines.launch

class MatchHistoryViewModel : ViewModel() {

    private val _matchHistoryState = mutableStateOf(MatchHistoryState())
    val matchHistoryState : State<MatchHistoryState> = _matchHistoryState


    fun performGetMatches(onResult:(String) -> Unit, onError: (Throwable) -> Unit){
        viewModelScope.launch {

            try {
                val csrfToken = fetchCSRFToken()
                val myMatches = getMatchHistory(csrfToken)
                if(myMatches == null){
                    Log.e("MyMatches", "fetch Error")
                }
                myMatches?.let {
                    _matchHistoryState.value= _matchHistoryState.value.copy(
                        list = it,
                        loading = false
                    )
                }



            }catch(e:Exception){
                onError(e)
            }

        }
    }

}

data class MatchHistoryState(
    val list : List<Match> = emptyList(),
    val loading : Boolean = true
)