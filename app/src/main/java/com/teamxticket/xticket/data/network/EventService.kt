package com.teamxticket.xticket.data.network

import com.teamxticket.xticket.core.RetrofitHelper
import com.teamxticket.xticket.data.model.CodeResponse
import com.teamxticket.xticket.data.model.Event
import com.teamxticket.xticket.data.model.EventResponse
import com.teamxticket.xticket.data.model.GenreResponse
import com.teamxticket.xticket.data.model.SearchEventsResponse
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

    suspend fun postEvent(newEvent: Event): CodeResponse {
        return withContext(Dispatchers.IO) {
            val response = retrofit.create(EventApiClient::class.java).postEvent(newEvent)
            response.body() ?: CodeResponse(-1)
        }
    }

    suspend fun getGenres(): GenreResponse {
        return withContext(Dispatchers.IO) {
            val response = retrofit.create(EventApiClient::class.java).getGenres()
            response.body() ?: GenreResponse("", emptyList())

        }
    }

    suspend fun searchEvents(query: String, genre: String?, limit: Int, page: Int): SearchEventsResponse {
        val params = mutableMapOf(
            "limit" to limit.toString(),
            "page" to page.toString()
        )
        if(genre != null) {
            params["genre"] = genre
        }
        return withContext(Dispatchers.IO) {
            val response = retrofit.create(EventApiClient::class.java).searchEvents(query, params.toMap())
            response.body() ?: SearchEventsResponse(null, null, null)
        }
    }
}