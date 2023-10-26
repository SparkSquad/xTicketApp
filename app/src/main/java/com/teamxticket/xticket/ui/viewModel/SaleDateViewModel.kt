package com.teamxticket.xticket.ui.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.teamxticket.xticket.data.model.NewSaleDate
import com.teamxticket.xticket.data.model.SaleDate
import com.teamxticket.xticket.domain.SalesDatesUseCase
import kotlinx.coroutines.launch

class SaleDateViewModel : ViewModel() {

    var saleDateModel = MutableLiveData<List<SaleDate>?>()
    var showLoader = MutableLiveData<Boolean>()
    var successfulRegister = MutableLiveData<Boolean>()
    private var saleDatesUseCase = SalesDatesUseCase()
    var showLoaderRegister = MutableLiveData<Boolean>()


    fun loadSaleDates(eventId : Int) {
        viewModelScope.launch {
            showLoader.postValue(true)
            val result = saleDatesUseCase.getSaleDates(1)

            if (!result.isNullOrEmpty()) {
                saleDateModel.postValue(result)
                showLoader.postValue(false)
            }
        }
    }

    fun registerSaleDate(newSaleDate: NewSaleDate) {
        viewModelScope.launch {
            showLoaderRegister.postValue(true)
            val result = saleDatesUseCase.postSaleDate(
                newSaleDate)
            if (result == 200) {
                successfulRegister.postValue(true)
            } else if (result == 500) {
                successfulRegister.postValue(false)
            }
        }
    }

   /* fun deleteSaleDate(saleDateId: Int) {
        viewModelScope.launch {
            showLoader.postValue(true)
            val result = saleDatesUseCase.deleteSaleDate(saleDateId)

            if (result == 200) {
                successfulRegister.postValue(true)
            }
        }
    }

    fun updateSaleDate(eventId: Int ,newSaleDate: NewSaleDate) {
        viewModelScope.launch {
            showLoader.postValue(true)
            val result = saleDatesUseCase.updateSaleDate(eventId, newSaleDate)

            if (result == 200) {
                successfulRegister.postValue(true)
            }
        }
    }*/
}
