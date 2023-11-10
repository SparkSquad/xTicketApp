package com.teamxticket.xticket.ui.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.teamxticket.xticket.data.UserRepository
import com.teamxticket.xticket.data.model.User
import com.teamxticket.xticket.data.model.UserResponse
import kotlinx.coroutines.launch
class UserViewModel : ViewModel() {
    var receivedUser = MutableLiveData<UserResponse>()
    var showLoader = MutableLiveData<Boolean>()
    var successfulRegister = MutableLiveData<Int>()
    fun searchUser(user : User) {
        viewModelScope.launch {
            try {
                val result = UserRepository().login(user)
                receivedUser.postValue(result)
            } catch (e: Exception) {
                receivedUser.postValue(UserResponse("", e.message, "", null))
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
}