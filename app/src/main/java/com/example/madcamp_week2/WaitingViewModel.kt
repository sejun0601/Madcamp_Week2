import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavHostController
import com.example.madcamp_week2.remote.MatchQueueResponse
import com.example.madcamp_week2.remote.apiService
import kotlinx.coroutines.launch
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext

class WaitingViewModel : ViewModel() {
    private val _waitingStatus = MutableStateFlow("매칭 중...")
    val waitingStatus: StateFlow<String> get() = _waitingStatus
    private val _isCancelled = MutableStateFlow(false)

    // 매칭 큐에 사용자 추가
    fun startMatching(navHostController: NavHostController) {
        _waitingStatus.value = "매칭 중..."
        _isCancelled.value = false
        viewModelScope.launch {
            try {
                while (_waitingStatus.value == "매칭 중...") {
                    if (_isCancelled.value) { // 취소 버튼 누르면 매칭 중단
                        _waitingStatus.value = "매칭 취소됨"
                        break
                    }

                    val response = performMatchQueueAction()
                    if (response.match != null) {
                        val matchId = response.match.id
                        _waitingStatus.value = "매칭 성공"
                        navHostController.navigate("play/$matchId") {
                            popUpTo("waiting") { inclusive = true }
                        }
                        break
                    }
                    delay(2000) // 2초 대기 후 다시 확인
                }
            } catch (e: Exception) {
                _waitingStatus.value = "매칭 실패"
            }
        }
    }
    /*
    // 매칭 큐에서 사용자 제거
    fun cancelMatching() {
        _isCancelled.value = true
        _waitingStatus.value = "매칭 취소 중..."
        viewModelScope.launch {
            try {
                val response = performMatchQueueAction("remove")
                if (response.status == "success") {
                    _waitingStatus.value = "매칭 취소됨"
                } else {
                    _waitingStatus.value = "매칭 취소 실패"
                }
            } catch (e: Exception) {
                _waitingStatus.value = "매칭 취소 실패"
            }
        }
    }
    */

    suspend fun performMatchQueueAction(): MatchQueueResponse {
        return withContext(Dispatchers.IO) {
            apiService.matchQueue() // API 호출
        }
    }


}

