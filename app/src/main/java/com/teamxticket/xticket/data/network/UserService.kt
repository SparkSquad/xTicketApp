package com.teamxticket.xticket.data.network

import com.teamxticket.xticket.core.RetrofitHelper
import com.teamxticket.xticket.data.model.User
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class UserService {
    private val retrofit = RetrofitHelper.getRetrofit()

    suspend fun postUser(user: User): Int {
        return withContext(Dispatchers.IO) {
            val response = retrofit.create(UsersApiClient::class.java).postUser(user)
            response.code()
        }
    }

}