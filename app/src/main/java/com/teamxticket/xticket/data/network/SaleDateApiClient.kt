package com.teamxticket.xticket.data.network

import com.teamxticket.xticket.data.model.ApiResponse
import com.teamxticket.xticket.data.model.GetSaleDateResponse
import com.teamxticket.xticket.data.model.SaleDate
import com.teamxticket.xticket.data.model.SaleDateResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface SaleDateApiClient {

    @GET("saleDate/getAll/{eventId}")
    suspend fun getAllSalesDates(@Path("eventId") eventId: Int): Response<SaleDateResponse>

    @POST("saleDate/create")
    suspend fun postSaleDate(@Body newSaleDate: SaleDate): Response<ApiResponse>

    @PUT("saleDate/update/{saleDateId}")
    suspend fun putSaleDate(
        @Path("saleDateId") saleDateId: Int,
        @Body newSaleDate: SaleDate): Response<ApiResponse>

    @DELETE("saleDate/delete/{saleDateId}")
    suspend fun deleteSaleDate(@Path("saleDateId") saleDateId: Int): Response<ApiResponse>

    @GET("saleDate/get/{saleDateId}")
    suspend fun getSaleDate(@Path("saleDateId") saleDateId: Int): Response<GetSaleDateResponse>

}