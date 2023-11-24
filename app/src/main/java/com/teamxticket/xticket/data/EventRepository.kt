package com.teamxticket.xticket.data

import com.teamxticket.xticket.core.ActiveTicketTaker
import com.teamxticket.xticket.core.ActiveUser
import com.teamxticket.xticket.data.model.Event
import com.teamxticket.xticket.data.model.EventsSearchResult
import com.teamxticket.xticket.data.model.TicketTakerResponse
import com.teamxticket.xticket.data.model.User
import com.teamxticket.xticket.data.model.UserResponse
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

    suspend fun putEvent(event: Event): Int {
        return api.putEvent(event).code
    }

    suspend fun getEvent(eventId: Int): Event {
        return api.getEvent(eventId)
    }

    suspend fun deleteEvent(eventId: Int): Int {
        return api.deleteEvent(eventId)
    }

    suspend fun searchEvents(query: String, genre: String?, limit: Int, page: Int): EventsSearchResult {
        val response = api.searchEvents(query, genre, limit, page)
        if(response.results == null || response.page == null || response.totalElems == null) throw Exception("Error searching events")
        return EventsSearchResult(response.results, response.page, response.totalElems)
    }

    suspend fun loginTicketTaker(ticketTakerCode: String): TicketTakerResponse {
        val result = api.loginTickerTaker(ticketTakerCode)
        if (result.ticketTakerCode != null) {
            // Actualiza la información del ticket taker solo si la respuesta es válida
            val activeTicketTaker = ActiveTicketTaker.getInstance()
            activeTicketTaker.setTicketTaker(result.ticketTakerCode)
        }
        return result
    }
}