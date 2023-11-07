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
}