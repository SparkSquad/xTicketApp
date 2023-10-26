package com.teamxticket.xticket.data

import com.teamxticket.xticket.data.model.NewSaleDate
import com.teamxticket.xticket.data.model.SaleDate
import com.teamxticket.xticket.data.model.SaleDateProvider
import com.teamxticket.xticket.data.network.SaleDateService

class SaleDateRepository {

    private val api = SaleDateService()

    suspend fun getAllSalesDates(eventId: Int): List<SaleDate> {
        val response = api.getSalesDates(eventId)
        SaleDateProvider.salesDates = response
        return response.saleDates
    }

    suspend fun postSaleDate(newSaleDate: NewSaleDate): Int {
        val response = api.postSaleDate(newSaleDate)
        return response
    }

    suspend fun deleteSaleDate(saleDateId: Int): Int {
        val response = api.deleteSaleDate(saleDateId)
        return response
    }

    suspend fun putSaleDate(saleDateId: Int, newSaleDate: NewSaleDate): Int {
        val response = api.putSaleDate(saleDateId, newSaleDate)
        return response
    }
}