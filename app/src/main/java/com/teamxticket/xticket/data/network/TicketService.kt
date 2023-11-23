package com.teamxticket.xticket.data.network

import android.content.res.Resources
import com.teamxticket.xticket.R
import com.teamxticket.xticket.core.RetrofitHelper
import com.teamxticket.xticket.data.model.Ticket
import com.teamxticket.xticket.data.model.TicketResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.net.SocketTimeoutException

class TicketService {
    private val retrofit = RetrofitHelper.getRetrofit()

    suspend fun getTickets(userId: Int): TicketResponse {
        return withContext(Dispatchers.IO) {
            try {
                val response = retrofit.create(TicketApiClient::class.java).getUserTickets(userId)
                if (response.code() >= 500) {
                    throw Exception(Resources.getSystem().getString(R.string.message_exception_500))
                } else if (response.code() >= 400) {
                    throw Exception(Resources.getSystem().getString(R.string.message_exception_400))
                }
                response.body() ?: TicketResponse("", emptyList())
            } catch (e: SocketTimeoutException) {
                throw SocketTimeoutException(Resources.getSystem().getString(R.string.message_can_not_connect_with_server))
            }
        }
    }

    suspend fun getTicketUuid (uuid: String): Ticket {
        return withContext(Dispatchers.IO) {
            try {
                val response = retrofit.create(TicketApiClient::class.java).getUuidTicket(uuid)
                if (response.code() >= 500) {
                    throw Exception(Resources.getSystem().getString(R.string.message_exception_500))
                } else if (response.code() >= 400) {
                    throw Exception(Resources.getSystem().getString(R.string.message_exception_400))
                }
                response.body() ?: Ticket(0.0, "", 0, 0, 0, 0, "")
            } catch (e: SocketTimeoutException) {
                throw SocketTimeoutException(Resources.getSystem().getString(R.string.message_can_not_connect_with_server))
            }
        }
    }

    suspend fun postTicket(newTicket: Ticket): Int {
        return withContext(Dispatchers.IO) {
            try {
                val response = retrofit.create(TicketApiClient::class.java).postTicket(newTicket)
                if (response.code() == 551) {
                    throw Exception("You already have a ticket for this event")
                } else if (response.code() == 550) {
                    throw Exception("No tickets available")
                } else if (response.code() >= 500) {
                    throw Exception(Resources.getSystem().getString(R.string.message_exception_500))
                } else if (response.code() >= 400) {
                    throw Exception(Resources.getSystem().getString(R.string.message_exception_400))
                }
                response.code()
            } catch (e: SocketTimeoutException) {
                throw SocketTimeoutException(Resources.getSystem().getString(R.string.message_can_not_connect_with_server))
            }
        }
    }

    suspend fun deleteTicket(ticketId: Int): Int {
        return withContext(Dispatchers.IO) {
            try {
                val response = retrofit.create(TicketApiClient::class.java).deleteTicket(ticketId)
                if (response.code() >= 500) {
                    throw Exception(Resources.getSystem().getString(R.string.message_exception_500))
                } else if (response.code() >= 400) {
                    throw Exception(Resources.getSystem().getString(R.string.message_exception_400))
                }
                response.code()
            } catch (e: SocketTimeoutException) {
                throw SocketTimeoutException(Resources.getSystem().getString(R.string.message_can_not_connect_with_server))
            }
        }
    }
}