package com.teamxticket.xticket.data

import com.teamxticket.xticket.data.model.SaleDate
import com.teamxticket.xticket.data.model.SaleDateProvider
import com.teamxticket.xticket.data.network.SaleDateService

class SaleDateRepository {

    private val api = SaleDateService()

    suspend fun getAllSalesDates(eventId: Int): List<SaleDate>? {
        val response = api.getSalesDates(eventId)
        SaleDateProvider.salesDates = response
        return response.saleDate
    }

    suspend fun postSaleDate(newSaleDate: SaleDate): Int {
        return api.postSaleDate(newSaleDate)
    }

    suspend fun deleteSaleDate(saleDateId: Int): Int {
        return api.deleteSaleDate(saleDateId)
    }

    suspend fun putSaleDate(saleDateId: Int, newSaleDate: SaleDate): Int {
        return api.putSaleDate(saleDateId, newSaleDate)
    }

    suspend fun getSaleDate(saleDateId: Int): SaleDate? {
        return api.getSaleDate(saleDateId)
    }
}