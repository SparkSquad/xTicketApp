package com.teamxticket.xticket.domain

import com.teamxticket.xticket.data.EventRepository
import com.teamxticket.xticket.data.model.Event

class EventUseCase {
    private val repository = EventRepository()
    suspend fun getAllEvents(userId: Int): List<Event> = repository.getAllEvents(userId)

    suspend fun postEvent(newEvent: Event): Int = repository.postEvent(newEvent)

    suspend fun getGenres(): List<String> = repository.getGenres()

    suspend fun putEvent(event: Event): Int = repository.putEvent(event)

    suspend fun getEvent(eventId: Int): Event = repository.getEvent(eventId)

    suspend fun searchEvents(query: String, genre: String?, limit: Int, page: Int) = repository.searchEvents(query, genre, limit, page)
}