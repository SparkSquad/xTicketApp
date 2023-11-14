package com.teamxticket.xticket.ui.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.teamxticket.xticket.data.model.Event
import com.teamxticket.xticket.data.model.EventProvider
import com.teamxticket.xticket.domain.EventUseCase
import kotlinx.coroutines.launch

class EventViewModel : ViewModel() {
    private val createEventUseCase = EventUseCase()
    var eventModel = MutableLiveData<List<Event>?>()
    var genresModel = MutableLiveData<List<String>?>()
    var showLoader = MutableLiveData<Boolean>()
    var showLoaderRegister = MutableLiveData<Boolean>()
    var showLoaderGenres = MutableLiveData<Boolean>()
    var successfulRegister = MutableLiveData<Int>()
    var successfulUpdate = MutableLiveData<Int>()
    var errorCode = MutableLiveData<String>()

    fun loadEvents(userId: Int) {
        viewModelScope.launch {
            showLoader.postValue(true)
            EventProvider.eventsList.clear()
            try {
                val result = createEventUseCase.getAllEvents(userId)
                EventProvider.eventsList.addAll(result)
                eventModel.postValue(result)
            } catch (e: Exception) {
                errorCode.postValue(e.message)
            }
            showLoader.postValue(false)
        }
    }

    fun registerEvent(event: Event) {
        viewModelScope.launch {
            showLoaderRegister.postValue(true)
            try {
                val result = createEventUseCase.postEvent(event)
                successfulRegister.postValue(result)
            } catch (e: Exception) {
                errorCode.postValue(e.message)
            }
            showLoaderRegister.postValue(false)
        }
    }

    fun loadGenres() {
        viewModelScope.launch {
            try {
                showLoaderGenres.postValue(true)
                val result = createEventUseCase.getGenres()
                genresModel.postValue(result)
            } catch (e: Exception) {
                errorCode.postValue(e.message)
            }
            showLoaderGenres.postValue(false)
        }
    }

    fun updateEvent(event: Event) {
        viewModelScope.launch {
            showLoaderRegister.postValue(true)
            try {
                val result = createEventUseCase.putEvent(event)
                successfulUpdate.postValue(result)
            } catch (e: Exception) {
                errorCode.postValue(e.message)
            }
            showLoaderRegister.postValue(false)
        }
    }

    fun getEvent(eventId: Int) {
        viewModelScope.launch {
            showLoader.postValue(true)
            try {
                val result = createEventUseCase.getEvent(eventId)
                eventModel.postValue(mutableListOf(result))
            } catch (e: Exception) {
                errorCode.postValue(e.message)
            }
            showLoader.postValue(false)
        }
    }
}