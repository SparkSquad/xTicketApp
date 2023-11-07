package com.teamxticket.xticket.data.network

import UsersApiClient
import com.teamxticket.xticket.core.RetrofitHelper
import com.teamxticket.xticket.data.model.User
import com.teamxticket.xticket.data.model.UserResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class UserService{
    private val retrofit = RetrofitHelper.getRetrofit()

    suspend fun login(user: User): UserResponse {
        return withContext(Dispatchers.IO) {
            val response = retrofit.create(UsersApiClient::class.java).login(user)
            response.body() ?: UserResponse("", "", "", null)
        }
    }
    
    suspend fun postUser(user: User): Int {
        return withContext(Dispatchers.IO) {
            val response = retrofit.create(UsersApiClient::class.java).postUser(user)
            response.code()
        }
    }
}