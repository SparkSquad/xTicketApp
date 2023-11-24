package com.teamxticket.xticket.domain

import com.teamxticket.xticket.data.UserRepository
import com.teamxticket.xticket.data.model.User

class UserUseCase {
    private val repository = UserRepository()

    suspend fun putUser(user: User): Int = repository.putUser(user)
    suspend fun searchEventPlanners(query: String, limit: Int, page: Int) = repository.searchEventPlanners(query, limit, page)
}