package com.teamxticket.xticket.data

import com.teamxticket.xticket.core.ActiveUser
import com.teamxticket.xticket.data.model.EventsSearchResult
import com.teamxticket.xticket.data.model.SearchEventPlannerResponse
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
    
    suspend fun postUser(user: User): Int {
        val response = api.postUser(user)
        return response
    }

    suspend fun putUser(user: User): Int {
        return api.putUser(user).code
    }

    suspend fun searchEventPlanners(query: String, limit: Int, page: Int): SearchEventPlannerResponse {
        val response = api.searchEventPlanners(query, limit, page)
        if(response.results == null || response.page == null || response.totalElems == null) throw Exception("Error searching event planners")
        return SearchEventPlannerResponse(response.results, response.page, response.totalElems)
    }
}