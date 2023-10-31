package com.teamxticket.xticket.data

import com.teamxticket.xticket.data.model.User
import com.teamxticket.xticket.data.model.UserResponse
import com.teamxticket.xticket.data.network.UserService

class UserRepository {
    private val api = UserService()

    suspend fun login(user: User): UserResponse {
        return api.login(user)
    }
}