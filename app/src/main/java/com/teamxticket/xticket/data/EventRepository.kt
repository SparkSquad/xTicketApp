package com.teamxticket.xticket.data

import com.teamxticket.xticket.data.model.Event
import com.teamxticket.xticket.data.model.EventsSearchResult
import com.teamxticket.xticket.data.network.EventService

class EventRepository {
    private val api = EventService()

    suspend fun getAllEvents(userId: Int): List<Event> {
        val response = api.getAllEvents(userId)
        return response.allEvents
    }

    suspend fun postEvent(newEvent: Event): Int {
        return api.postEvent(newEvent).code
    }

    suspend fun getGenres(): List<String> {
        val response = api.getGenres()
        return response.genres
    }

    suspend fun searchEvents(query: String, genre: String?, limit: Int, page: Int): EventsSearchResult {
        val response = api.searchEvents(query, genre, limit, page)
        if(response.results == null || response.page == null || response.totalElems == null) throw Exception("Error searching events")
        return EventsSearchResult(response.results, response.page, response.totalElems)
    }
}