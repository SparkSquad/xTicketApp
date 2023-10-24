package com.teamxticket.xticket.data.network

import com.teamxticket.xticket.data.model.SaleDate
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface SaleDateApiClient {

    @GET("sales_dates/salesdates/")
    suspend fun getAllSalesDates(
        @Query("eventId") eventId: Int,
    ): Response<List<SaleDate>>
}