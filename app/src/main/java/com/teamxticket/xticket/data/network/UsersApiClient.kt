package com.teamxticket.xticket.data.network

import com.teamxticket.xticket.data.model.SaleDateResponse
import com.teamxticket.xticket.data.model.User
import com.teamxticket.xticket.data.model.UserResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface UsersApiClient {

    @POST("auth/login")
    suspend fun login(@Body user: User): Response<UserResponse>
}