package com.teamxticket.xticket.ui.viewModel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.teamxticket.xticket.data.model.Event
import com.teamxticket.xticket.data.model.SaleDate
import com.teamxticket.xticket.data.model.Ticket
import com.teamxticket.xticket.data.model.TicketData
import com.teamxticket.xticket.domain.EventUseCase
import com.teamxticket.xticket.domain.SalesDatesUseCase
import com.teamxticket.xticket.domain.TicketsUseCase
import kotlinx.coroutines.launch

class TicketsViewModel: ViewModel() {

    private val saleDatesUseCase = SalesDatesUseCase()
    private val eventUseCase = EventUseCase()
    private var ticketsUseCase = TicketsUseCase()
    var ticketsModel = MutableLiveData<List<TicketData>?>()
    var showLoader = MutableLiveData<Boolean>()
    var successfulPurchase = MutableLiveData<Int>()
    var successfulRefund = MutableLiveData<Int>()
    var error = MutableLiveData<String>()
    var refundList = MutableLiveData<List<TicketData>?>()
    var eventModel = MutableLiveData<List<Event>?>()
    var saleDateActive = MutableLiveData<SaleDate?>()

    fun loadTickets(userId: Int) {
        viewModelScope.launch {
            showLoader.postValue(true)
            try {
                val result = ticketsUseCase.getTickets(userId)
                ticketsModel.postValue(result)
            } catch (e: Exception) {
                val errorMessage = e.localizedMessage ?: "Unknown error occurred"
                error.postValue(errorMessage)
            }
            showLoader.postValue(false)
        }
    }

    fun purchaseTicket(newTicket: Ticket) {
        viewModelScope.launch {
            showLoader.postValue(true)
            try {
                val result = ticketsUseCase.postTicket(newTicket)
                successfulPurchase.postValue(result)
            } catch (e: Exception) {
                val errorMessage = e.localizedMessage ?: "Unknown error occurred"
                error.postValue(errorMessage)
            }
            showLoader.postValue(false)
        }
    }

    fun refundTicket(ticketId: Int) {
        viewModelScope.launch {
            showLoader.postValue(true)
            try {
                val result = ticketsUseCase.deleteTicket(ticketId)
                successfulRefund.postValue(result)
            } catch (e: Exception) {
                val errorMessage = e.localizedMessage ?: "Unknown error occurred"
                error.postValue(errorMessage)
            }
            showLoader.postValue(false)
        }
    }

    fun loadRefundTickets() {
        viewModelScope.launch {
            showLoader.postValue(true)
            val result = ticketsUseCase.getRefundTicket()
            if (result != null) {
                refundList.postValue(result)
            } else {
                error.postValue("No tickets to refund")
            }
            showLoader.postValue(false)
        }
    }

    fun getTicketData(eventId:Int, saleDateId: Int) {
        viewModelScope.launch {
            showLoader.postValue(true)
            try {
                val result1 = saleDatesUseCase.getSaleDate(saleDateId)
                saleDateActive.postValue(result1)
                val result = eventUseCase.getEvent(eventId)
                eventModel.postValue(mutableListOf(result))
            } catch (e: Exception) {
                val errorMessage = e.localizedMessage ?: "Unknown error occurred"
                error.postValue(errorMessage)
            }
            showLoader.postValue(false)
        }
    }
}