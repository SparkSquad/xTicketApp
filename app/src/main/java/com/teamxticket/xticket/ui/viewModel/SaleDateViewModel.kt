package com.teamxticket.xticket.ui.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.teamxticket.xticket.data.model.SaleDate
import com.teamxticket.xticket.domain.SalesDatesUseCase
import kotlinx.coroutines.launch

class SaleDateViewModel : ViewModel() {

    var saleDateModel = MutableLiveData<List<SaleDate>?>()
    var showLoader = MutableLiveData<Boolean>()
    var showLoaderRegister = MutableLiveData<Boolean>()
    var showLoaderUpdate = MutableLiveData<Boolean>()
    var successfulUpdate = MutableLiveData<Int>()
    var successfulRegister = MutableLiveData<Int>()
    private var saleDatesUseCase = SalesDatesUseCase()

    fun loadSaleDates(eventId : Int) {
        viewModelScope.launch {
            showLoader.postValue(true)
            val result = saleDatesUseCase.getSaleDates(eventId)
            if (!result.isNullOrEmpty()) {
                saleDateModel.postValue(result)
            } else {
                saleDateModel.postValue(null)
            }
            showLoader.postValue(false)
        }
    }

    fun registerSaleDate(newSaleDate: SaleDate) {
        viewModelScope.launch {
            showLoaderRegister.postValue(true)
            val result = saleDatesUseCase.postSaleDate(newSaleDate)
            successfulRegister.postValue(result)
            showLoaderRegister.postValue(false)
        }
    }

   fun deleteSaleDate(saleDateId: Int) {
        viewModelScope.launch {
            showLoaderUpdate.postValue(true)
            val result = saleDatesUseCase.deleteSaleDate(saleDateId)
            showLoaderUpdate.postValue(false)
            successfulUpdate.postValue(result)
        }
    }

    fun updateSaleDate(saleDateId: Int, newSaleDate: SaleDate) {
        viewModelScope.launch {
            showLoaderUpdate.postValue(true)
            val result = saleDatesUseCase.updateSaleDate(saleDateId, newSaleDate)
            showLoaderUpdate.postValue(false)
            successfulUpdate.postValue(result)
        }
    }
}
