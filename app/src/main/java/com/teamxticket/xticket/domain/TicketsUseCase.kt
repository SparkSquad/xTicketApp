package com.teamxticket.xticket.domain

import com.teamxticket.xticket.data.TicketRepository
import com.teamxticket.xticket.data.model.Ticket
import com.teamxticket.xticket.data.model.TicketData

class TicketsUseCase {
    private val repository = TicketRepository()

    suspend fun getTickets(userId: Int): List<TicketData>? = repository.getAllTickets(userId)

    suspend fun postTicket(newTicket: Ticket): Int = repository.postTicket(newTicket)

    suspend fun deleteTicket(ticketId: Int): Int = repository.deleteTicket(ticketId)

    suspend fun getTicketUuid(ticketUuid: String): Ticket = repository.getTicketUuid(ticketUuid)
}