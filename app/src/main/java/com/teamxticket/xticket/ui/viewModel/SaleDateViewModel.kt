package com.teamxticket.xticket.ui.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.teamxticket.xticket.data.model.SaleDate
import com.teamxticket.xticket.domain.GetSalesDatesUseCase
import kotlinx.coroutines.launch

class SaleDateViewModel : ViewModel() {

    var saleDateModel = MutableLiveData<List<SaleDate>?>()
    val showLoader = MutableLiveData<Boolean>()
    private var getSaleDatesUseCase = GetSalesDatesUseCase()

    fun loadSaleDates(eventId : Int) {
        viewModelScope.launch {
            showLoader.postValue(true)
            val result = getSaleDatesUseCase.getAllDateSales(1)

            if (!result.isNullOrEmpty()) {
                saleDateModel.postValue(result)
                showLoader.postValue(false)
            }
        }
    }
}
