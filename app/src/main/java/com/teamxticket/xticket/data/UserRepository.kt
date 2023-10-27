package com.teamxticket.xticket.data


import com.teamxticket.xticket.data.model.User
import com.teamxticket.xticket.data.network.UserService

class UserRepository {
    private val api = UserService()

    suspend fun postSaleDate(user: User): Int {
        val response = api.postUser(user)
        return response
    }
}