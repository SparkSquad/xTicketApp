package com.teamxticket.xticket.data.network

import com.teamxticket.xticket.data.model.CodeResponse
import com.teamxticket.xticket.data.model.Event
import com.teamxticket.xticket.data.model.EventResponse
import com.teamxticket.xticket.data.model.GenreResponse
import com.teamxticket.xticket.data.model.SearchEventsResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.FieldMap
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.QueryMap

interface EventApiClient {
    @GET("event/getAll/{userId}")
    suspend fun getAllEvents(@Path("userId") userId: Int): Response<EventResponse>

    @POST("event/addEvent")
    suspend fun postEvent(@Body newEvent: Event): Response<CodeResponse>

    @GET("event/getGenres")
    suspend fun getGenres(): Response<GenreResponse>

    @GET("event/search/{query}")
    suspend fun searchEvents(@Path("query") query: String, @QueryMap params: Map<String, String>) : Response<SearchEventsResponse>

    @PUT("event/updateEvent")
    suspend fun putEvent(@Body event: Event): Response<CodeResponse>

    @GET("event/getEvent/{eventId}")
    suspend fun getEvent(@Path("eventId") eventId: Int): Response<Event>

    @DELETE("event/delete/{eventId}")
    suspend fun deleteEvent(@Path("eventId") eventId: Int): Response<CodeResponse>
}
