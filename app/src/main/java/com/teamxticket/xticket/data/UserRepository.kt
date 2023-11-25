package com.teamxticket.xticket.data

import com.teamxticket.xticket.core.ActiveUser
import com.teamxticket.xticket.data.model.OneTimeUseCode
import com.teamxticket.xticket.data.model.OneTimeUseCodeResponse
import com.teamxticket.xticket.data.model.User
import com.teamxticket.xticket.data.model.UserResponse
import com.teamxticket.xticket.data.network.UserService

class UserRepository {
    private val api = UserService()

    suspend fun login(user: User): UserResponse {
        val result = api.login(user)
        if (result.user != null) {
            val activeUser = ActiveUser.getInstance()
            activeUser.setUser(result.user)
        }
        return result
    }

    suspend fun codeLogin(code: OneTimeUseCode): UserResponse {
        val result = api.codeLogin(code)
        if (result.user != null) {
            val activeUser = ActiveUser.getInstance()
            activeUser.setUser(result.user)
        }
        return result
    }
    
    suspend fun postUser(user: User): Int {
        val response = api.postUser(user)
        return response
    }

    suspend fun putUser(user: User): Int {
        return api.putUser(user).code
    }

    suspend fun requestOTUCode(email: String): OneTimeUseCodeResponse {
        return api.requestOTUCode("asldjfh")
    }
}