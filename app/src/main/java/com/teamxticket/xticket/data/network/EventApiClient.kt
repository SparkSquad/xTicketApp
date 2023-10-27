package com.teamxticket.xticket.data.network

import com.teamxticket.xticket.data.model.Event
import com.teamxticket.xticket.data.model.EventResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface EventApiClient {
    @GET("event/getAll/{userId}")
    suspend fun getAllEvents(@Path("userId") userId: Int): Response<EventResponse>

    @POST("event/addEvent")
    suspend fun postEvent(@Body newEvent: Event): Response<Int>

    @GET("event/getGenres")
    suspend fun getGenres(): Response<List<String>>
}