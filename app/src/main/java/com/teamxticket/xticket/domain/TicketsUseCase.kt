package com.teamxticket.xticket.domain

import com.teamxticket.xticket.data.TicketRepository
import com.teamxticket.xticket.data.model.Ticket
import com.teamxticket.xticket.data.model.TicketData
import com.teamxticket.xticket.data.model.TicketProvider
import java.text.SimpleDateFormat
import java.util.Date

class TicketsUseCase {
    private val repository = TicketRepository()

    suspend fun getTickets(userId: Int): List<TicketData>? = repository.getAllTickets(userId)

    suspend fun postTicket(newTicket: Ticket): Int = repository.postTicket(newTicket)

    suspend fun deleteTicket(ticketId: Int): Int = repository.deleteTicket(ticketId)

    suspend fun getTicketUuid(ticketUuid: String): Ticket = repository.getTicketUuid(ticketUuid)

    suspend fun getRefundTicket () : List<TicketData>? {
        val validTicketDataList = mutableListOf<TicketData>()

        for (ticketData in TicketProvider.tickets!!.tickets) {
            val saleDate = ticketData.saleDate
            if (isDateBeforeToday(saleDate.saleDate)) {
                validTicketDataList.add(ticketData)
            }
        }
        return validTicketDataList
    }

    private fun isDateBeforeToday(dateString: String): Boolean {
        val sdf = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
        val date = sdf.parse(dateString)
        val currentDate = Date()
        return date.after(currentDate)
    }
}