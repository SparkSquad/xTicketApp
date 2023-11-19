package com.teamxticket.xticket.ui.viewModel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.teamxticket.xticket.data.EventRepository
import com.teamxticket.xticket.data.model.Event
import com.teamxticket.xticket.data.model.EventProvider
import com.teamxticket.xticket.domain.EventUseCase
import kotlinx.coroutines.launch

class EventViewModel : ViewModel() {
    private val eventUseCase = EventUseCase()
    var eventModel = MutableLiveData<List<Event>?>()
    var genresModel = MutableLiveData<List<String>?>()
    var showLoader = MutableLiveData<Boolean>()
    var showLoaderRegister = MutableLiveData<Boolean>()
    var showLoaderGenres = MutableLiveData<Boolean>()
    var successfulRegister = MutableLiveData<Int>()
    var error = MutableLiveData<String>()
    var firstLoad = true
    var successfulUpdate = MutableLiveData<Int>()
    var errorCode = MutableLiveData<String>()
    var repository = EventUseCase()

    fun loadEvents(userId: Int) {
        viewModelScope.launch {
            showLoader.postValue(true)
            EventProvider.eventsList.clear()
            try {
                val result = eventUseCase.getAllEvents(userId)
                EventProvider.eventsList.addAll(result)
                eventModel.postValue(result)
            } catch (e: Exception) {
                errorCode.postValue(e.message)
            }
            showLoader.postValue(false)
        }
    }

    fun searchEvents(query: String, genre: String?, limit: Int, page: Int) {
        viewModelScope.launch {
            if(firstLoad) {
                showLoader.postValue(true)
                firstLoad = false
            }
            val search = repository.searchEvents(query, genre, limit, page)
            EventProvider.eventsList.clear()
            EventProvider.eventsList.addAll(search.results)
            eventModel.postValue(search.results)
            showLoader.postValue(false)
        }
    }

    fun registerEvent(event: Event) {
        viewModelScope.launch {
            showLoaderRegister.postValue(true)
            try {
                val result = eventUseCase.postEvent(event)
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
                val result = eventUseCase.getGenres()
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
                val result = eventUseCase.putEvent(event)
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
                val result = eventUseCase.getEvent(eventId)
                Log.d("Events", result.bandsAndArtists.toString())
                eventModel.postValue(mutableListOf(result))
            } catch (e: Exception) {
                errorCode.postValue(e.message)
            }
            showLoader.postValue(false)
        }
    }
}