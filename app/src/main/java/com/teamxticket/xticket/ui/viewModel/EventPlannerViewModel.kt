package com.teamxticket.xticket.ui.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.teamxticket.xticket.data.model.EventPlannerData
import com.teamxticket.xticket.domain.EventPlannerUseCase
import kotlinx.coroutines.launch

class EventPlannerViewModel : ViewModel() {
    private val eventPlannerUseCase = EventPlannerUseCase()
    val eventPlannerData = MutableLiveData<EventPlannerData>()
    var showLoader = MutableLiveData<Boolean>()
    var errorCode = MutableLiveData<String>()

    fun getEventPlanner(eventPlannerId: Int) {
        viewModelScope.launch {
            showLoader.postValue(true)
            try {
                val result = eventPlannerUseCase.getEventPlannerById(eventPlannerId)
                eventPlannerData.postValue(result)
            } catch (e: Exception) {
                errorCode.postValue(e.message)
            }
            showLoader.postValue(false)
        }
    }
}