package com.teamxticket.xticket.ui.viewModel

import android.widget.Toast
import android.widget.Toast.makeText
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.teamxticket.xticket.data.model.SaleDate

class RegisterSaleDateViewModel : ViewModel() {

    fun validateForm(saleDate: SaleDate): Boolean {
        val eventDate = saleDate.eventDate
        val numberOfTickets = saleDate.numberOfTickets
        val price = saleDate.price
        val maxTickets = saleDate.maxTickets

        if (eventDate.isEmpty()) {
            return false
            println("validacion fallida 1")
        }

        if (numberOfTickets <= 0) {
            println("validacion fallida 2")
            return false
        }

        if (price <= 0.0) {
            println("validacion fallida 3")
            return false
        }

        if (maxTickets <= 0) {
            println("validacion fallida 4")
            return false
        }

        if (!isEndTimeAfterStartTime(saleDate.startTime, saleDate.endTime)) {
            return false
        }
        println("validacion hecha")
        return true
    }

    private fun isEndTimeAfterStartTime(startTime: String, endTime: String): Boolean {
        val startTimeParts = startTime.split(":")
        val endTimeParts = endTime.split(":")

        if (startTimeParts.size != 2 || endTimeParts.size != 2) {
            return false
        }

        val startHour = startTimeParts[0].toInt()
        val startMinute = startTimeParts[1].toInt()
        val endHour = endTimeParts[0].toInt()
        val endMinute = endTimeParts[1].toInt()

        if (endHour < startHour) {
            return false
        } else if (endHour == startHour && endMinute <= startMinute) {
            return false
        }
        println("validacion hecha")
        return true
    }

    fun registerSaleDate(saleDate: SaleDate): Boolean {
        println("realizando vaidacion")
        if (validateForm(saleDate)) {
            return true
        }
        return false
    }
}
