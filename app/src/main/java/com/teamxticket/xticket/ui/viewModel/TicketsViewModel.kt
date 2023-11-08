package com.teamxticket.xticket.ui.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.teamxticket.xticket.data.model.Ticket
import com.teamxticket.xticket.data.model.TicketData
import com.teamxticket.xticket.domain.TicketsUseCase
import kotlinx.coroutines.launch

class TicketsViewModel: ViewModel() {

    var ticketsModel = MutableLiveData<List<TicketData>?>()
    var showLoader = MutableLiveData<Boolean>()
    var showLoaderPurchase = MutableLiveData<Boolean>()
    var showLoaderRefund = MutableLiveData<Boolean>()
    var successfulPurchase = MutableLiveData<Int>()
    var successfulRefund = MutableLiveData<Int>()
    var error = MutableLiveData<String>()
    var refundList = MutableLiveData<List<TicketData>?>()
    private var ticketsUseCase = TicketsUseCase()

    fun loadTickets(userId: Int) {
        viewModelScope.launch {
            showLoader.postValue(true)
            try {
                val result = ticketsUseCase.getTickets(userId)
                ticketsModel.postValue(result)
            } catch (e: Exception) {
                error.postValue(e.message)
            }
            showLoader.postValue(false)
        }
    }

    fun purchaseTicket(newTicket: Ticket) {
        viewModelScope.launch {
            showLoaderPurchase.postValue(true)
            try {
                val result = ticketsUseCase.postTicket(newTicket)
                successfulPurchase.postValue(result)
            } catch (e: Exception) {
                error.postValue(e.message)
            }
            showLoaderPurchase.postValue(false)
        }
    }

    fun refundTicket(ticketId: Int) {
        viewModelScope.launch {
            showLoaderRefund.postValue(true)
            try {
                val result = ticketsUseCase.deleteTicket(ticketId)
                successfulRefund.postValue(result)
            } catch (e: Exception) {
                error.postValue(e.message)
            }
            showLoaderRefund.postValue(false)
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

}