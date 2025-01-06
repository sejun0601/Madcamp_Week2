package com.example.madcamp_week2.remote

data class Video(
    val video_id: String,
    val title: String,
    val description: String?,
    val trend_score: Int,
    val view_count: Int,
    val like_count: Int,
    val published_at: String
)


data class VideoResponse(val data: List<Video> = emptyList(), )

data class CSRFResponse(
    val csrfToken: String
)

data class LoginRequest(
    val username: String,
    val password: String
)

data class LoginResponse(
    val detail: String
)

data class LoginStatusResponse(
    val logged_in: Boolean,
    val username: String?,
    val email: String?
)

data class LogoutResponse(
    val detail: String // 예: "로그아웃 성공"
)

data class ProfileResponse(
    val user : String,
    val email : String,
    val rank_score: String,
    val win_count : String,
    val lose_count : String
)
