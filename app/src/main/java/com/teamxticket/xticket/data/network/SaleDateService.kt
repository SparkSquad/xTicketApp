package com.teamxticket.xticket.data.network

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
                    throw Exception("Error al conectar con el servidor, intentelo mas tarde")
                } else if (response.code() >= 400) {
                    throw Exception("Hubo un error de nuestra parte, intentelo mas tarde")
                }
                response.body() ?: SaleDateResponse("", emptyList())
            } catch (e: SocketTimeoutException) {
                throw Exception("Servicio no disponible, intentelo mas tarde")
            }
        }
    }

    suspend fun postSaleDate(newSaleDate: SaleDate): Int {
        return withContext(Dispatchers.IO) {
            try {
                val response = retrofit.create(SaleDateApiClient::class.java).postSaleDate(newSaleDate)
                if (response.code() >= 500) {
                    throw Exception("Error al conectar con el servidor, intentelo mas tarde")
                } else if (response.code() >= 400) {
                    throw Exception("Ubo un error de nuestra parte, intentelo mas tarde")
                }
                response.code()
            } catch (e: SocketTimeoutException) {
                throw SocketTimeoutException("Error al conectar con el servidor")
            }
        }
    }

    suspend fun deleteSaleDate(saleDateId: Int): Int {
        return withContext(Dispatchers.IO) {
            try {
                val response = retrofit.create(SaleDateApiClient::class.java).deleteSaleDate(saleDateId)
                if (response.code() >= 500) {
                    throw Exception("Error al conectar con el servidor, intentelo mas tarde")
                } else if (response.code() >= 400) {
                    throw Exception("Ubo un error de nuestra parte, intentelo mas tarde")
                }
                response.code()
            } catch (e: SocketTimeoutException) {
                throw SocketTimeoutException("Error al conectar con el servidor")
            }
        }
    }

    suspend fun putSaleDate(saleDateId: Int, newSaleDate: SaleDate): Int {
        return withContext(Dispatchers.IO) {
            try {
                val response = retrofit.create(SaleDateApiClient::class.java).putSaleDate(saleDateId, newSaleDate)
                if (response.code() >= 500) {
                    throw Exception("Error al conectar con el servidor, intentelo mas tarde")
                } else if (response.code() >= 400) {
                    throw Exception("Ubo un error de nuestra parte, intentelo mas tarde")
                }
                response.code()
            } catch (e: SocketTimeoutException) {
                throw SocketTimeoutException("Error al conectar con el servidor")
            }
        }
    }

}