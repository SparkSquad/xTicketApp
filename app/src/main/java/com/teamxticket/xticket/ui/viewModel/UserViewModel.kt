package com.teamxticket.xticket.ui.viewModel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.teamxticket.xticket.data.UserRepository
import com.teamxticket.xticket.data.model.EventFollow
import com.teamxticket.xticket.data.model.User
import com.teamxticket.xticket.data.model.UserResponse
import com.teamxticket.xticket.domain.UserUseCase
import kotlinx.coroutines.launch
class UserViewModel : ViewModel() {
    private val userUseCase = UserUseCase()
    var receivedUser = MutableLiveData<UserResponse>()
    var followedEvents = MutableLiveData<List<EventFollow>>()
    var showLoader = MutableLiveData<Boolean>()
    var successfulRegister = MutableLiveData<Int>()
    var successfulUpdate = MutableLiveData<Int>()
    var errorCode = MutableLiveData<String>()

    fun searchUser(user : User) {
        viewModelScope.launch {
            showLoader.postValue(true)
            try {
                val result = UserRepository().login(user)
                receivedUser.postValue(result)
            } catch (e: Exception) {
                receivedUser.postValue(UserResponse("", e.message, "", null))
                showLoader.postValue(false)
            }
        }
    }
    
    fun registerUser(user: User) {
        viewModelScope.launch {
            showLoader.postValue(true)
            val result = UserRepository().postUser(user)
            successfulRegister.postValue(result)
            showLoader.postValue(false)
        }
    }

    fun updateUser(user: User) {
        viewModelScope.launch {
            showLoader.postValue(true)
            try {
                val result = userUseCase.putUser(user)
                successfulUpdate.postValue(result)
            } catch (e: Exception) {
                errorCode.postValue(e.message)
            }
            showLoader.postValue(false)
        }
    }

    fun loadFollowedEvent(userId: Int) {
        viewModelScope.launch {
            try {
                val result = UserRepository().getUserEventFollows(userId)
                followedEvents.postValue(result.response)
            } catch (e: Exception) {
                Log.d("UserViewModel", "loadFollowedEvent: {${e.message}}")
                errorCode.postValue(e.message)
            }
        }
    }
}