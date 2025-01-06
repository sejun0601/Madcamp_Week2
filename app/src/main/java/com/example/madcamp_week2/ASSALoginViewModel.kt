package com.example.madcamp_week2

import androidx.collection.emptyIntSet
import androidx.compose.foundation.pager.PagerState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.madcamp_week2.remote.LoginRequest
import com.example.madcamp_week2.remote.apiService
import com.example.madcamp_week2.remote.fetchCSRFToken
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ASSALoginViewModel : ViewModel(){

    private val _loginState  = mutableStateOf(LoginState())
    val loginState : State<LoginState> = _loginState

    fun setUsername(username: String){
        _loginState.value = _loginState.value.copy(
              username = username
        )
    }

    fun setPassword(password: String){
        _loginState.value = _loginState.value.copy(
            password = password
        )
    }

    private suspend fun loginUser(csrfToken: String, username: String, password: String): String {
        return withContext(Dispatchers.IO) {
            val loginRequest = LoginRequest(username, password)
            val response = apiService.login(csrfToken, loginRequest)
            response.detail
        }
    }

    fun performLogin(onResult: (String) -> Unit, onError: (Throwable) -> Unit) {
        viewModelScope.launch {
            try {
                val csrfToken = fetchCSRFToken()
                val detail = loginUser(csrfToken, _loginState.value.username, _loginState.value.password)
                onResult(detail)
            } catch (e: Exception) {
                onError(e)
            }
        }
    }





}

data class LoginState(
    val username: String = "",
    val password : String = ""
)