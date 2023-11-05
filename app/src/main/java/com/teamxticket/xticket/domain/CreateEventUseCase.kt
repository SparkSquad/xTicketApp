package com.teamxticket.xticket.domain

import com.teamxticket.xticket.data.EventRepository
import com.teamxticket.xticket.data.model.Event

class CreateEventUseCase {
    private val repository = EventRepository()
    suspend fun getAllEvents(userId: Int): List<Event> = repository.getAllEvents(userId)

    suspend fun postEvent(newEvent: Event): Int = repository.postEvent(newEvent)

    suspend fun getGenres(): List<String> = repository.getGenres()
}