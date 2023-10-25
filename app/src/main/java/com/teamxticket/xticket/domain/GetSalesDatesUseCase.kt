package com.teamxticket.xticket.domain

import com.teamxticket.xticket.data.SaleDateRepository
import com.teamxticket.xticket.data.model.SaleDate

class GetSalesDatesUseCase {
    private val repository = SaleDateRepository()
    suspend fun getAllDateSales(eventId: Int): List<SaleDate>? = repository.getAllSalesDates(eventId)
}