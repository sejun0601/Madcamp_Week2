package com.example.madcamp_week2.data.models

data class SignUpResponse(
    val detail: String,        // 회원가입 성공 메시지
    val user: UserData?,        // 사용자 정보
)

data class UserData(
    val username: String,
    val email: String
)
