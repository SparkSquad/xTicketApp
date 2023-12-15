package com.teamxticket.xticket.domain

import com.teamxticket.xticket.data.EventRepository
import com.teamxticket.xticket.data.model.Event
import io.mockk.impl.annotations.RelaxedMockK
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import java.util.concurrent.TimeUnit

class EventUseCaseTest {
    @RelaxedMockK
    private val repository = EventRepository()
    @RelaxedMockK
    private var eventId = 0
    private val userId = 1

    @Before
    fun setUp() {

    }

    @After
    fun tearDown() {
    }

    @Test
    fun getAllUserEvents() = runBlocking {
        println("\u001b[32m" + "getAllUserEvents")
        try {
            val result = repository.getAllEvents(userId)
            assertEquals(true, result.isNotEmpty())
        } catch (e: Exception) {
            println("\u001b[31m" + e.message)
        }
    }

    @Test
    fun postEvent() = runBlocking {
        println("\u001b[32m" + "postEvent")
        try {
            val event = Event(
                0,
                "Test Event",
                "Rock",
                "Test Description",
                "Test Location",
                userId,
                "000000",
                mutableListOf("ARTIST 0", "ARTIST 1"),
                null,
                null,
                null
            )
            val result = repository.postEvent(event)
            eventId = result
            assertEquals(true, result > 0)
        } catch (e: Exception) {
            println("\u001b[31m" + e.message)
        }
    }

    @Test
    fun getGenres() = runBlocking {
        println("\u001b[32m" + "getGenres")
        try {
            val result = repository.getGenres()
            assertEquals(true, result.isNotEmpty())
        } catch (e: Exception) {
            println("\u001b[31m" + e.message)
        }
    }

    @Test
    fun getEvent1() = runBlocking {
        println("\u001b[32m" + "getEvent1")
        try {
            val eventId = 1
            val result = repository.getEvent(eventId)
            assertEquals(true, result.eventId == eventId)
        } catch (e: Exception) {
            println("\u001b[31m" + e.message)
        }
    }

    @Test
    fun getEvent2() = runBlocking {
        println("\u001b[32m" + "getEvent2")
        try {
            val eventId = 2
            val result = repository.getEvent(eventId)
            assertEquals(true, result.eventId == eventId)
        } catch (e: Exception) {
            println("\u001b[31m" + e.message)
        }
    }

    @Test
    fun getEvent3() = runBlocking {
        println("\u001b[32m" + "getEvent3")
        try {
            val eventId = 3
            val result = repository.getEvent(eventId)
            assertEquals(true, result.eventId == eventId)
        } catch (e: Exception) {
            println("\u001b[31m" + e.message)
        }
    }

    @Test
    fun searchEvents1() = runBlocking {
        println("\u001b[32m" + "searchEvents1")
        try {
            val result = repository.searchEvents("Test", null, 10, 1)
            assertEquals(true, result.results.isNotEmpty())
        } catch (e: Exception) {
            println("\u001b[31m" + e.message)
        }
    }

    @Test
    fun searchEvents2() = runBlocking {
        println("\u001b[32m" + "searchEvents2")
        try {
            val result = repository.searchEvents("Event", null, 10, 1)
            assertEquals(true, result.results.isNotEmpty())
        } catch (e: Exception) {
            println("\u001b[31m" + e.message)
        }
    }
}