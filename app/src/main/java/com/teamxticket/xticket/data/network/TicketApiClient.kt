package com.teamxticket.xticket.data.network

import com.teamxticket.xticket.data.model.ApiResponse
import com.teamxticket.xticket.data.model.Ticket
import com.teamxticket.xticket.data.model.TicketResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface TicketApiClient {

    @GET("fullTickets/getAll/{userId}")
    suspend fun getUserTickets(@Path("userId") userId: Int): Response<TicketResponse>

    @GET("ticket/getByUuid/{uuid}")
    suspend fun getUuidTicket(@Path("uuid") uuid: String): Response<Ticket>

    @POST("ticket/create")
    suspend fun postTicket(@Body newTicket: Ticket): Response<ApiResponse>

    @DELETE("ticket/delete/{ticketId}")
    suspend fun deleteTicket(@Path("ticketId") ticketId: Int): Response<ApiResponse>
}