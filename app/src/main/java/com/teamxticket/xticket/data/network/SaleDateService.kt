package com.teamxticket.xticket.data.network

import android.content.res.Resources
import com.teamxticket.xticket.R
import com.teamxticket.xticket.core.RetrofitHelper
import com.teamxticket.xticket.data.model.SaleDate
import com.teamxticket.xticket.data.model.SaleDateResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.net.SocketTimeoutException

class SaleDateService {
    private val retrofit = RetrofitHelper.getRetrofit()

    suspend fun getSalesDates(eventId: Int): SaleDateResponse {
        return withContext(Dispatchers.IO) {
            try {
                val response = retrofit.create(SaleDateApiClient::class.java).getAllSalesDates(eventId)
                if (response.code() >= 500) {
                    throw Exception(Resources.getSystem().getString(R.string.message_exception_500))
                } else if (response.code() >= 400) {
                    throw Exception(Resources.getSystem().getString(R.string.message_exception_400))
                }
                response.body() ?: SaleDateResponse("", emptyList())
            } catch (e: SocketTimeoutException) {
                throw SocketTimeoutException(Resources.getSystem().getString(R.string.message_can_not_connect_with_server))
            }
        }
    }

    suspend fun postSaleDate(newSaleDate: SaleDate): Int {
        return withContext(Dispatchers.IO) {
            try {
                val response = retrofit.create(SaleDateApiClient::class.java).postSaleDate(newSaleDate)
                if (response.code() >= 500) {
                    throw Exception(Resources.getSystem().getString(R.string.message_exception_500))
                } else if (response.code() >= 400) {
                    throw Exception(Resources.getSystem().getString(R.string.message_exception_400))
                }
                response.code()
            } catch (e: SocketTimeoutException) {
                throw SocketTimeoutException(Resources.getSystem().getString(R.string.message_can_not_connect_with_server))
            }
        }
    }

    suspend fun deleteSaleDate(saleDateId: Int): Int {
        return withContext(Dispatchers.IO) {
            try {
                val response = retrofit.create(SaleDateApiClient::class.java).deleteSaleDate(saleDateId)
                if (response.code() >= 500) {
                    throw Exception(Resources.getSystem().getString(R.string.message_exception_500))
                } else if (response.code() >= 400) {
                    throw Exception(Resources.getSystem().getString(R.string.message_exception_400))
                }
                response.code()
            } catch (e: SocketTimeoutException) {
                throw SocketTimeoutException(Resources.getSystem().getString(R.string.message_can_not_connect_with_server))
            }
        }
    }

    suspend fun putSaleDate(saleDateId: Int, newSaleDate: SaleDate): Int {
        return withContext(Dispatchers.IO) {
            try {
                val response = retrofit.create(SaleDateApiClient::class.java).putSaleDate(saleDateId, newSaleDate)
                if (response.code() >= 500) {
                    throw Exception(Resources.getSystem().getString(R.string.message_exception_500))
                } else if (response.code() >= 400) {
                    throw Exception(Resources.getSystem().getString(R.string.message_exception_400))
                }
                response.code()
            } catch (e: SocketTimeoutException) {
                throw SocketTimeoutException(Resources.getSystem().getString(R.string.message_can_not_connect_with_server))
            }
        }
    }

}