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
            if (!response.isSuccessful) {
                throw Exception("Error al conectar con el servidor")
            }
            response.body() ?: SaleDateResponse("", emptyList())
        }
    }

    suspend fun postSaleDate(newSaleDate: SaleDate): Int {
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

    suspend fun putSaleDate(saleDateId: Int, newSaleDate: SaleDate): Int {
        return withContext(Dispatchers.IO) {
            val response = retrofit.create(SaleDateApiClient::class.java).putSaleDate(saleDateId, newSaleDate)
            response.code()
        }
    }

}