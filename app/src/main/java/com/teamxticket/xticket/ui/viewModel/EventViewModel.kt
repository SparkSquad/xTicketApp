package com.teamxticket.xticket.ui.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.teamxticket.xticket.data.EventRepository
import com.teamxticket.xticket.data.model.Event
import com.teamxticket.xticket.data.model.EventProvider
import kotlinx.coroutines.launch

class EventViewModel : ViewModel() {
    private val repository = EventRepository()
    var eventModel = MutableLiveData<List<Event>?>()
    var genresModel = MutableLiveData<List<String>?>()
    var showLoader = MutableLiveData<Boolean>()
    var showLoaderRegister = MutableLiveData<Boolean>()
    var showLoaderGenres = MutableLiveData<Boolean>()
    var successfulRegister = MutableLiveData<Int>()

    fun loadEvents(userId: Int) {
        viewModelScope.launch {
            showLoader.postValue(true)
            val result = repository.getAllEvents(userId)
            EventProvider.eventsList.clear()
            EventProvider.eventsList.addAll(result)
            if (result.isNotEmpty()) {
                eventModel.postValue(result)
                showLoader.postValue(false)
            }
        }
    }

    fun registerEvent(event: Event) {
        viewModelScope.launch {
            showLoaderRegister.postValue(true)
            val result = repository.postEvent(event)
            successfulRegister.postValue(result)
            showLoaderRegister.postValue(false)
        }
    }

    fun loadGenres() {
        viewModelScope.launch {
            showLoaderGenres.postValue(true)
            val result = repository.getGenres()
            if (result.isNotEmpty()) {
                genresModel.postValue(result)
                showLoaderGenres.postValue(false)
            }
        }
    }
}