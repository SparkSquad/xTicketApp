package com.teamxticket.xticket.data.network

import com.teamxticket.xticket.core.RetrofitHelper
import com.teamxticket.xticket.data.model.SaleDate
import com.teamxticket.xticket.data.model.SaleDateResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class SaleDateService {
    private val retrofit = RetrofitHelper.getRetrofit()

    suspend fun getSalesDates(eventId: Int): SaleDateResponse {
        return withContext(Dispatchers.IO) {
            val response = retrofit.create(SaleDateApiClient::class.java).getAllSalesDates(eventId)
            response.body() ?: SaleDateResponse("", emptyList())
        }
    }
}