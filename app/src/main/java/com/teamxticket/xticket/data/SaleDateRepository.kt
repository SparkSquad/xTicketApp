package com.teamxticket.xticket.data

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
}