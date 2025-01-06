package com.example.madcamp_week2

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.madcamp_week2.remote.fetchCSRFToken
import com.example.madcamp_week2.remote.getProfile
import com.example.madcamp_week2.remote.logoutUser
import kotlinx.coroutines.launch
import retrofit2.http.Body

class ProfileViewModel : ViewModel(){

    private val _userProfileState = mutableStateOf(UserProfileState())
    val userProfileState : State<UserProfileState> = _userProfileState

    fun performGetProfile(onResult: (String) -> Unit, onError: (Throwable) -> Unit) {
        viewModelScope.launch {
            try {
                val csrfToken = fetchCSRFToken()
                val profile = getProfile(csrfToken)
                profile?.let {
                    _userProfileState.value = _userProfileState.value.copy(
                        username = it.user,
                        email = it.email,
                        rankScore = it.rank_score,
                        winCount = it.win_count,
                        loseCount = it.lose_count
                    )
                }

                onResult("Profile fetched")

            } catch (e: Exception) {
                onError(e)
            }
        }
    }

    fun performLogout(onResult: (Boolean) -> Unit, onError: (Throwable) -> Unit){
        viewModelScope.launch {

            try {
                val csrfToken = fetchCSRFToken()
                val response = logoutUser(csrfToken)
                onResult(response)

            } catch (e: Exception) {
                onError(e)
            }
        }
    }

}

data class UserProfileState(
    val username: String = "",
    val email: String = "",
    val rankScore: String = "",
    val winCount : String = "",
    val loseCount :String = ""
)