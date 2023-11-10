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
                if (response.code() >= 500) {
                    throw Exception("No se pudo conectar con el servidor, intente más tarde")
                } else if (response.code() >= 400) {
                    throw Exception("Hubo un erro de nuestra parte, intente más tarde")
                }
                response.body() ?: TicketResponse("", emptyList())
            } catch (e: SocketTimeoutException) {
                throw Exception("Error al conectar con el servidor")
            }
        }
    }

    suspend fun getTicketUuid (uuid: String): Ticket {
        return withContext(Dispatchers.IO) {
            try {
                val response = retrofit.create(TicketApiClient::class.java).getUuidTicket(uuid)
                if (response.code() >= 500) {
                    throw Exception("No se pudo conectar con el servidor, intente más tarde")
                } else if (response.code() >= 400) {
                    throw Exception("Hubo un erro de nuestra parte, intente más tarde")
                }
                response.body() ?: Ticket(0.0, "", 0, 0, 0, 0, "")
            } catch (e: SocketTimeoutException) {
                throw Exception("Error al conectar con el servidor")
            }
        }
    }

    suspend fun postTicket(newTicket: Ticket): Int {
        return withContext(Dispatchers.IO) {
            try {
                val response = retrofit.create(TicketApiClient::class.java).postTicket(newTicket)
                if (response.code() >= 500) {
                    throw Exception("No se pudo conectar con el servidor, intente más tarde")
                } else if (response.code() >= 400) {
                    throw Exception("Hubo un erro de nuestra parte, intente más tarde")
                }
                response.code()
            } catch (e: SocketTimeoutException) {
                throw SocketTimeoutException("Error al conectar con el servidor")
            }
        }
    }

    suspend fun deleteTicket(ticketId: Int): Int {
        return withContext(Dispatchers.IO) {
            try {
                val response = retrofit.create(TicketApiClient::class.java).deleteTicket(ticketId)
                if (response.code() >= 500) {
                    throw Exception("No se pudo conectar con el servidor, intente más tarde")
                } else if (response.code() >= 400) {
                    throw Exception("Hubo un erro de nuestra parte, intente más tarde")
                }
                response.code()
            } catch (e: SocketTimeoutException) {
                throw SocketTimeoutException("Error al conectar con el servidor")
            }
        }
    }
}