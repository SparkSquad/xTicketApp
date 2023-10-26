package com.teamxticket.xticket.data.network

import com.teamxticket.xticket.data.model.ApiResponse
import com.teamxticket.xticket.data.model.NewSaleDate
import com.teamxticket.xticket.data.model.SaleDate
import com.teamxticket.xticket.data.model.SaleDateResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Query

interface SaleDateApiClient {

    @GET("saleDate/getAll/{eventId}")
    suspend fun getAllSalesDates(@Path("eventId") eventId: Int): Response<SaleDateResponse>

    @POST("saleDate/addSaleDate")
    suspend fun postSaleDate(@Body newSaleDate: NewSaleDate): Response<ApiResponse>

    @PUT("saleDate/putSaleDate/{saleDateId}")
    suspend fun putSaleDate(
        @Path("saleDateId") saleDateId: Int,
        @Body newSaleDate: NewSaleDate): Response<ApiResponse>

    @DELETE("saleDate/deleteSaleDate/{saleDateId}")
    suspend fun deleteSaleDate(@Path("saleDateId") saleDateId: Int): Response<ApiResponse>

}