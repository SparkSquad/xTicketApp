package com.teamxticket.xticket.data

import com.teamxticket.xticket.data.model.Ticket
import com.teamxticket.xticket.data.model.TicketData
import com.teamxticket.xticket.data.model.TicketProvider
import com.teamxticket.xticket.data.network.TicketService

class TicketRepository {
    private val api = TicketService()

    suspend fun getAllTickets(eventId: Int): List<TicketData>? {
        val response = api.getTickets(eventId)
        TicketProvider.tickets = response
        return response.tickets
    }

    suspend fun postTicket(newTicket: Ticket): Int {
        return api.postTicket(newTicket)
    }

    suspend fun deleteTicket(ticketId: Int): Int {
        return api.deleteTicket(ticketId)
    }

    suspend fun getTicketUuid(ticketUuid: String): Ticket {
        return api.getTicketUuid(ticketUuid)
    }
}