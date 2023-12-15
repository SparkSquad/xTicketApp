package com.teamxticket.xticket.domain

import com.teamxticket.xticket.data.TicketRepository
import com.teamxticket.xticket.data.model.SaleDate
import com.teamxticket.xticket.data.model.Ticket
import com.teamxticket.xticket.data.model.TicketData
import io.mockk.impl.annotations.RelaxedMockK
import kotlinx.coroutines.runBlocking
import org.junit.Assert.*

import org.junit.After
import org.junit.Before
import org.junit.Test

class TicketsUseCaseTests {
    @RelaxedMockK
    private val repository = TicketRepository()
    @Before
    fun setUp() {
    }

    @After
    fun tearDown() {
    }

    @Test
    fun getTickets() =
        runBlocking {
            var ticket = Ticket(1.0, "20/20/2000", 1, 1, 4, 1, "10512" )
            var saleDate = SaleDate(1, "20/20/2000", 1,1,4.0, "1", 1, "10512",1)
            val ticketDataList: List<TicketData>? = listOf(TicketData("1", saleDate, ticket))
            val result = repository.getAllTickets(1)
            assertNotNull(result)
            assertEquals(ticketDataList?.size, result?.size)
    }

    @Test
    fun postTicket() = runBlocking {
        var ticket = Ticket(1.0, "20/20/2000", -1, -1, 4, -1, "10512" )
        try {
            var result = repository.postTicket(ticket)
        } catch (e: Exception) {
            assertEquals(e.message, "No tickets available")
        }
    }

    @Test
    fun deleteTicket() = runBlocking{
        try {
            var result = repository.deleteTicket(-1)
            assert(result == 200)
        } catch (e: Exception) {
            assertEquals(e.message, "")
        }
    }
}