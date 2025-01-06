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
