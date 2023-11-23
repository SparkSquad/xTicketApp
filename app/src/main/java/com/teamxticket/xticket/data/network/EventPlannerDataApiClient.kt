package com.teamxticket.xticket.data.network

import com.teamxticket.xticket.data.model.EventPlannerDataResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface EventPlannerDataApiClient {
    @GET("eventPlannerData/{eventPlannerDataId}")
    suspend fun getByUserId(@Path("eventPlannerDataId") eventPlannerId: Int):
            Response<EventPlannerDataResponse>
}
