package com.teamxticket.xticket.data.network

import com.teamxticket.xticket.data.model.SaleDate
import com.teamxticket.xticket.data.model.SaleDateResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface SaleDateApiClient {

    @GET("saleDate/getAll/{eventId}")
    suspend fun getAllSalesDates(@Path("eventId") eventId: Int): Response<SaleDateResponse>

}