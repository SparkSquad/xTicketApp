package com.teamxticket.xticket.domain

import com.teamxticket.xticket.data.UserRepository
import com.teamxticket.xticket.data.model.OneTimeUseCode
import com.teamxticket.xticket.data.model.OneTimeUseCodeResponse
import com.teamxticket.xticket.data.model.User

class UserUseCase {
    private val repository = UserRepository()

    suspend fun putUser(user: User): Int = repository.putUser(user)
    suspend fun requestOTUCode(email: OneTimeUseCode): OneTimeUseCodeResponse = repository.requestOTUCode(email)
    suspend fun followEvent(userId: Int, eventId: Int) = repository.followEvent(userId, eventId)
}