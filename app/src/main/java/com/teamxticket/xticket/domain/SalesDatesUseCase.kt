package com.teamxticket.xticket.domain

import com.teamxticket.xticket.data.SaleDateRepository
import com.teamxticket.xticket.data.model.NewSaleDate
import com.teamxticket.xticket.data.model.SaleDate

class SalesDatesUseCase {
    private val repository = SaleDateRepository()
    suspend fun getSaleDates(eventId: Int): List<SaleDate>? = repository.getAllSalesDates(eventId)

    suspend fun postSaleDate(newSaleDate: NewSaleDate): Int = repository.postSaleDate(newSaleDate)

    suspend fun deleteSaleDate(saleDateId: Int): Int = repository.deleteSaleDate(saleDateId)

    suspend fun updateSaleDate(saleDateId: Int, newSaleDate: NewSaleDate): Int = repository.putSaleDate(saleDateId, newSaleDate)
}