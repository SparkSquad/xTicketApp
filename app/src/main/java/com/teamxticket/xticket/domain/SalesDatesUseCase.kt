package com.teamxticket.xticket.domain

import com.teamxticket.xticket.data.SaleDateRepository
import com.teamxticket.xticket.data.model.SaleDate

class SalesDatesUseCase {
    private val repository = SaleDateRepository()
    suspend fun getSaleDates(eventId: Int): List<SaleDate>? = repository.getAllSalesDates(eventId)

    suspend fun postSaleDate(newSaleDate: SaleDate): Int = repository.postSaleDate(newSaleDate)

    suspend fun deleteSaleDate(saleDateId: Int): Int = repository.deleteSaleDate(saleDateId)

    suspend fun updateSaleDate(saleDateId: Int, newSaleDate: SaleDate): Int = repository.putSaleDate(saleDateId, newSaleDate)

    suspend fun getSaleDate(saleDateId: Int): SaleDate? = repository.getSaleDate(saleDateId)
}
