package com.example.state.login.data.datasource

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthService {
    @POST("app/auth/login")
    suspend fun login(@Body credentials: Map<String, String>): Response<Map<String, String>>
}
