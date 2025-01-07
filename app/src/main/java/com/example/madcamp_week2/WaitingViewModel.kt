import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavHostController
import com.example.madcamp_week2.UserProfileState
import com.example.madcamp_week2.remote.enQueue
import com.example.madcamp_week2.remote.MatchQueueResponse
import com.example.madcamp_week2.remote.SubmitAnswerRequest
import com.example.madcamp_week2.remote.answer
import com.example.madcamp_week2.remote.fetchCSRFToken
import com.example.madcamp_week2.remote.getProfile
import com.example.madcamp_week2.ui.Screen
import kotlinx.coroutines.launch
import kotlinx.coroutines.delay

class WaitingViewModel : ViewModel() {
    private val _matchState = mutableStateOf(MatchState(matchData = null))
    val matchState: State<MatchState> = _matchState

    private val _answer = mutableStateOf("")
    val answer: State<String> = _answer

    fun setAnswer(answer:String){
        _answer.value = answer
    }

    fun performAnswer(onResult: (String) -> Unit, onError: (Throwable) -> Unit){
        viewModelScope.launch {
            try {
                val matchData = _matchState.value.matchData?.match
                val submitAnswerRequest = SubmitAnswerRequest(answer = _answer.value)
                val csrfToken = fetchCSRFToken()
                val response = matchData?.let { answer(it.id, submitAnswerRequest, csrfToken) }

                if (response != null) {
                    Log.d("asdf", response.detail)
                    if (response.detail == "정답! 승리했습니다."){
                        onResult("승리")
                    }else if(response.detail == "이미 승자가 결정되었습니다."){
                        onResult("패배")
                    }else if(response.detail == "틀렸습니다."){
                        onResult("틀렸습니다")
                    }else{
                        onResult(response.detail)
                    }

                }
            }catch (e:Exception){
                onError(e)
            }
        }
    }

    // 매칭 큐에 사용자 추가
    fun startMatching(navHostController: NavHostController, onResult: (String) -> Unit, onError: (Throwable) -> Unit ) {
        viewModelScope.launch {
            try {
                while (_matchState.value.waiting) {
                    val csrfToken = fetchCSRFToken()
                    val response = enQueue(csrfToken)
                    if (response != null) {
                        if (response.matched) {
                            _matchState.value = _matchState.value.copy(waiting = false, matchData = response)
                            onResult(response.detail)
                            navHostController.navigate(Screen.OtherScreens.Play.oRoute)
                            break
                        }
                    }
                    delay(3000) // 2초 대기 후 다시 확인
                }
            } catch (e: Exception) {
                onError(e)
                Log.e("WaitingViewModel", "${e.message}")
            }
        }
    }

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


}

data class MatchState(
    val waiting : Boolean = true,
    val matchData: MatchQueueResponse?
)

