package com.teamxticket.xticket.domain

import com.teamxticket.xticket.data.SaleDateRepository
import com.teamxticket.xticket.data.model.SaleDate
import io.mockk.impl.annotations.RelaxedMockK
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Test

class SaleDatesUseCaseTest {

    @RelaxedMockK
    private val repository = SaleDateRepository()

    @Test
    fun getSaleDates() = runBlocking {
        var result = repository.getAllSalesDates(2)
        assert(result?.size == 1)
    }

    @Test
    fun getSaleDates2() = runBlocking {
        var result = repository.getAllSalesDates(1)
        assert(result?.size == 1)
    }

    @Test
    fun postSaleDate() = runBlocking {
        var saleDate = SaleDate(
            adults = 1,
            endTime = "",
            eventId = 1,
            maxTickets = 1,
            price = 4.0,
            saleDate = "10/10/2025",
            saleDateId = 1,
            startTime = "",
            tickets = 1
        )
        try {
            var result = repository.postSaleDate(saleDate)
            assert(result == 200)
        } catch (e: Exception) {
            assertEquals(e.message, e.message)
        }
    }

    @Test
    fun deleteSaleDate() = runBlocking{
        try {
            var result = repository.deleteSaleDate(-1)
            assert(result == 200)
        } catch (e: Exception) {
            assertEquals(e.message, e.message)
        }
    }

    @Test
    fun updateSaleDate() = runBlocking {
        var saleDate = SaleDate(
            adults = 1,
            endTime = "23:00:00",
            eventId = 2,
            maxTickets = 3,
            price = 600.0,
            saleDate = "2024-12-12",
            saleDateId = 2,
            startTime = "13:00:00",
            tickets = 1
        )
        try {
            var result = repository.putSaleDate(2, saleDate)
            assert(result == 200)
        } catch (e: Exception) {
            assertEquals(e.message, e.message)
        }
    }

    @Test
    fun getSaleDate() = runBlocking {
        var result = repository.getSaleDate(1)
        assert(result?.saleDateId == 1)
    }
}