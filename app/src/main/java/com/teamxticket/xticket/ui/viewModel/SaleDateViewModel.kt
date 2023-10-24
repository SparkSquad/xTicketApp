package com.teamxticket.xticket.ui.viewModel

import android.content.Intent
import android.view.View
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.teamxticket.xticket.data.model.SaleDate
import com.teamxticket.xticket.domain.GetSalesDatesUseCase
import kotlinx.coroutines.launch

class SaleDateViewModel : ViewModel() {

    var saleDateModel = MutableLiveData<List<SaleDate>?>()

    val showLoader = MutableLiveData<Boolean>()

    var getSaleDatesUseCase = GetSalesDatesUseCase()

    fun onCreate(eventId : Int) {
        viewModelScope.launch {
            showLoader.postValue(true)
            val result = getSaleDatesUseCase.getallDateSales(1)

            if (!result.isNullOrEmpty()) {
                saleDateModel.postValue(result)
                showLoader.postValue(false)
            }
        }
    }
    fun onItemClick(view: View) {
        /*val context = view.context
        val intent = Intent(context, NextActivity::class.java)
        intent.putExtra("itemId", item.id) // Puedes pasar el ID u otra información aquí
        context.startActivity(intent)*/
    }
}
