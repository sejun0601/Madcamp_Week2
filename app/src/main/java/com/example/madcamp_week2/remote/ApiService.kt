package com.example.madcamp_week2.remote

import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

val retrofit: Retrofit = Retrofit.Builder()
    .baseUrl("http://192.168.64.26:8000/")
    .addConverterFactory(GsonConverterFactory.create())
    .build()

val apiService: ApiService = retrofit.create(ApiService::class.java)

interface ApiService {
    @FormUrlEncoded
    @POST("api/v1/auth/social/google/")
    fun loginWithGoogle(@Field("id_token") idToken: String): Call<Void>
}