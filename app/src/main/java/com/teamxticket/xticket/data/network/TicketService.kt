package com.teamxticket.xticket.data.network

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
                response.body() ?: TicketResponse("", emptyList())
            } catch (e: SocketTimeoutException) {
                throw Exception("Error al conectar con el servidor")
            } catch (e: Exception) {
                throw Exception("Error al conectar con el servidor")
            }
        }
    }

    suspend fun getAvailableTicket (uuid: Int): TicketResponse {
        return withContext(Dispatchers.IO) {
            try {
                val response = retrofit.create(TicketApiClient::class.java).getUuidTicket(uuid)
                response.body() ?: TicketResponse("", emptyList())
            } catch (e: SocketTimeoutException) {
                throw Exception("Error al conectar con el servidor")
            } catch (e: Exception) {
                throw Exception("Error al conectar con el servidor")
            }
        }
    }

    suspend fun postTicket(newTicket: Ticket): Int {
        return withContext(Dispatchers.IO) {
            try {
                val response = retrofit.create(TicketApiClient::class.java).postTicket(newTicket)
                response.code()
            } catch (e: SocketTimeoutException) {
                throw SocketTimeoutException("Error al conectar con el servidor")
            } catch (e: Exception) {
                throw Exception("Error al conectar con el servidor")
            }
        }
    }

    suspend fun deleteTicket(ticketId: Int): Int {
        return withContext(Dispatchers.IO) {
            try {
                val response = retrofit.create(TicketApiClient::class.java).deleteTicket(ticketId)
                response.code()
            } catch (e: SocketTimeoutException) {
                throw SocketTimeoutException("Error al conectar con el servidor")
            } catch (e: Exception) {
                throw Exception("Error al conectar con el servidor")
            }
        }
    }
}