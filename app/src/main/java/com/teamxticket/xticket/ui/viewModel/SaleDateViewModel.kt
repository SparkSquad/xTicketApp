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
    var errorCode = MutableLiveData<String>()
    private var saleDatesUseCase = SalesDatesUseCase()

    fun loadSaleDates(eventId : Int) {
        viewModelScope.launch {
            showLoader.postValue(true)
            try {
                val result = saleDatesUseCase.getSaleDates(eventId)
                saleDateModel.postValue(result)
            } catch (e: Exception) {
                errorCode.postValue(e.message)
            }
            showLoader.postValue(false)
        }
    }

    fun registerSaleDate(newSaleDate: SaleDate) {
        viewModelScope.launch {
            showLoaderRegister.postValue(true)
            try {
                val result = saleDatesUseCase.postSaleDate(newSaleDate)
                successfulRegister.postValue(result)
            } catch (e: Exception) {
                errorCode.postValue(e.message)
            }
            showLoaderRegister.postValue(false)
        }
    }

   fun deleteSaleDate(saleDateId: Int) {
        viewModelScope.launch {
            showLoaderUpdate.postValue(true)
            try {
                val result = saleDatesUseCase.deleteSaleDate(saleDateId)
                successfulUpdate.postValue(result)
            } catch (e: Exception) {
                errorCode.postValue(e.message)
            }
            showLoaderUpdate.postValue(false)
        }
    }

    fun updateSaleDate(saleDateId: Int, newSaleDate: SaleDate) {
        viewModelScope.launch {
            showLoaderUpdate.postValue(true)
            try {
                val result = saleDatesUseCase.updateSaleDate(saleDateId, newSaleDate)
                successfulUpdate.postValue(result)
            } catch (e: Exception) {
                errorCode.postValue(e.message)
            }
            showLoaderUpdate.postValue(false)
        }
    }
}
