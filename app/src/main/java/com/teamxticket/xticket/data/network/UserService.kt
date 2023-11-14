package com.teamxticket.xticket.data.network

import UsersApiClient
import android.content.res.Resources
import com.teamxticket.xticket.R
import com.teamxticket.xticket.core.RetrofitHelper
import com.teamxticket.xticket.data.model.CodeResponse
import com.teamxticket.xticket.data.model.User
import com.teamxticket.xticket.data.model.UserResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.net.SocketTimeoutException

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

    suspend fun putUser(user: User): CodeResponse {
        return withContext(Dispatchers.IO) {
            try {
                val response = retrofit.create(UsersApiClient::class.java).putUser(user)
                if (response.code() >= 500) {
                    throw Exception(Resources.getSystem().getString(R.string.message_exception_500))
                } else if (response.code() >= 400) {
                    throw Exception(Resources.getSystem().getString(R.string.message_exception_400))
                }
                response.body() ?: CodeResponse(-1)

            } catch (e: SocketTimeoutException) {
                throw SocketTimeoutException(Resources.getSystem().getString(R.string.message_can_not_connect_with_server))
            }
        }
    }
}