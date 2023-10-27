package com.teamxticket.xticket.data.network

import com.teamxticket.xticket.data.model.ApiResponse
import com.teamxticket.xticket.data.model.User
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface UsersApiClient {
    @POST("auth/signup")
    suspend fun postUser(@Body user: User): Response<ApiResponse>
}