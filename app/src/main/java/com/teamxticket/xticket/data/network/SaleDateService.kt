package com.teamxticket.xticket.data.network

import com.teamxticket.xticket.core.RetrofitHelper
import com.teamxticket.xticket.data.model.NewSaleDate
import com.teamxticket.xticket.data.model.SaleDate
import com.teamxticket.xticket.data.model.SaleDateResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.IOException

class SaleDateService {
    private val retrofit = RetrofitHelper.getRetrofit()

    suspend fun getSalesDates(eventId: Int): SaleDateResponse {
        return withContext(Dispatchers.IO) {
            val response = retrofit.create(SaleDateApiClient::class.java).getAllSalesDates(eventId)
            response.body() ?: SaleDateResponse("", emptyList())
        }
    }

    suspend fun postSaleDate(newSaleDate: NewSaleDate): Int {
        return withContext(Dispatchers.IO) {
            val response = retrofit.create(SaleDateApiClient::class.java).postSaleDate(newSaleDate)
            response.code()
        }
    }


    suspend fun deleteSaleDate(saleDateId: Int): Int {
        return withContext(Dispatchers.IO) {
            val response = retrofit.create(SaleDateApiClient::class.java).deleteSaleDate(saleDateId)
            response.code()
        }
    }

    suspend fun putSaleDate(saleDateId: Int, newSaleDate: NewSaleDate): Int {
        return withContext(Dispatchers.IO) {
            val response = retrofit.create(SaleDateApiClient::class.java).putSaleDate(saleDateId, newSaleDate)
            response.code()
        }
    }

}