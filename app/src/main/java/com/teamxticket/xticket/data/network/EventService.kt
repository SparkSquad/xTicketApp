package com.teamxticket.xticket.data.network

import com.teamxticket.xticket.core.RetrofitHelper
import com.teamxticket.xticket.data.model.Event
import com.teamxticket.xticket.data.model.EventResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class EventService {
    private val retrofit = RetrofitHelper.getRetrofit()

    suspend fun getAllEvents(userId: Int): EventResponse {
        return withContext(Dispatchers.IO) {
            val response = retrofit.create(EventApiClient::class.java).getAllEvents(userId)
            response.body() ?: EventResponse("", emptyList())
        }
    }

    suspend fun postEvent(newEvent: Event): Int {
        return withContext(Dispatchers.IO) {
            val response = retrofit.create(EventApiClient::class.java).postEvent(newEvent)
            response.code()
        }
    }

    suspend fun getGenres(): List<String> {
        return withContext(Dispatchers.IO) {
            val response = retrofit.create(EventApiClient::class.java).getGenres()
            response.body() ?: emptyList()
        }
    }
}