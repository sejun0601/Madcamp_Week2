package com.example.madcamp_week2.remote

import com.example.madcamp_week2.data.models.SignUpRequest
import com.example.madcamp_week2.data.models.SignUpResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Query

val retrofit: Retrofit = Retrofit.Builder()
    .baseUrl("http://172.10.7.19:8000/")
    .addConverterFactory(GsonConverterFactory.create())
    .build()

val apiService: ApiService = retrofit.create(ApiService::class.java)

interface ApiService {
    @FormUrlEncoded
    @POST("api/v1/auth/social/google/")
    fun loginWithGoogle(@Field("id_token") idToken: String): Call<Void>

    @GET("api/v1/video/trending-videos/")
    suspend fun getTrendingVideos(
        @Query("limit") limit: Int
    ): List<Video>

    // CSRF 토큰 요청
    @GET("api/v1/auth/csrf/")
    suspend fun getCSRFToken(): CSRFResponse

    // 로그인 요청
    @POST("api/v1/auth/login/")
    suspend fun login(
        @Header("X-CSRFToken") csrfToken: String,
        @Body loginRequest: LoginRequest
    ): LoginResponse


    // Sign Up
    @POST("api/v1/auth/register/")
    suspend fun signUp(
        @Header("X-CSRFToken") csrfToken: String,
        @Body signUpRequest: SignUpRequest
    ): SignUpResponse

    // 로그인 상태 확인
    @GET("api/v1/auth/status/")
    suspend fun checkLoginStatus(): LoginStatusResponse

    // 로그아웃 요청
    @POST("api/v1/auth/logout/")
    suspend fun logout(): LogoutResponse


}

suspend fun fetchCSRFToken(): String {
    return withContext(Dispatchers.IO) {
        val response = apiService.getCSRFToken()
        response.csrfToken
    }
}

suspend fun getLoggedInUser(): LoginStatusResponse? {
    return try {
        apiService.checkLoginStatus()
    } catch (e: Exception) {
        null // 로그인 상태 확인 실패 시 null 반환
    }
}

suspend fun logoutUser(): Boolean {
    return try {
        val response = apiService.logout()
        response.detail == "로그아웃 성공" // 서버 응답 메시지를 확인
    } catch (e: Exception) {
        false // 로그아웃 실패
    }
}
