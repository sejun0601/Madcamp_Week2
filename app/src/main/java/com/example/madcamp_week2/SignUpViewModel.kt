package com.example.madcamp_week2

import androidx.compose.foundation.pager.PagerState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.madcamp_week2.data.models.SignUpRequest
import com.example.madcamp_week2.remote.LoginRequest
import com.example.madcamp_week2.remote.apiService
import com.example.madcamp_week2.remote.fetchCSRFToken
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class SignUpViewModel : ViewModel(){

    private val _signupState = mutableStateOf(SignUpState())
    val signUpState  : State<SignUpState> = _signupState

    fun setPassword( password: String){
        _signupState.value = _signupState.value.copy(
            password = password
        )
    }

    fun setEmail(email: String){
        _signupState.value = _signupState.value.copy(
            email = email
        )
    }

    fun setUsername(username: String){
        _signupState.value = _signupState.value.copy(
            username = username
        )
    }


    private suspend fun signupUser(csrfToken: String, username: String, password: String, email: String): String {
        return withContext(Dispatchers.IO) {
            val signupRequest = SignUpRequest(username = username, password = password, email = email)
            val response = apiService.signUp(csrfToken, signupRequest)
            response.detail
        }
    }

    fun performSignup(onResult: (String) -> Unit, onError: (Throwable) -> Unit) {
        viewModelScope.launch {
            try {
                val csrfToken = fetchCSRFToken()
                val detail = signupUser(csrfToken, _signupState.value.username, _signupState.value.password, _signupState.value.email)
                onResult(detail)
            } catch (e: Exception) {
                onError(e)
            }
        }
    }


}

data class SignUpState(
    val email : String = "",
    val password : String = "",
    val username : String = ""
)