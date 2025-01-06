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
    val detail: String
)

data class MatchQueueResponse(
    val detail: String,
    val match: MatchResponse?
)

data class MatchResponse(
    val id: Int, // 매칭 ID
    val player1: PlayerResponse,
    val player2: PlayerResponse,
    val problem: ProblemResponse?,
    val status: String
)

data class PlayerResponse(
    val id: Int,
    val username: String
)

data class ProblemResponse(
    val id: Int,
    val question: String,
    val answer: String
)


