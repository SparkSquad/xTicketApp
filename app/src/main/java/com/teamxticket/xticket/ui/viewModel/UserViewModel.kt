package com.teamxticket.xticket.ui.viewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.teamxticket.xticket.data.UserRepository
import com.teamxticket.xticket.data.model.User

import kotlinx.coroutines.launch

class UserViewModel : ViewModel() {
    var showLoader = MutableLiveData<Boolean>()
    var successfulRegister = MutableLiveData<Int>()
    private var userRepository = UserRepository()


    fun registerUser(user: User) {
        viewModelScope.launch {
            showLoader.postValue(true)
            val result = userRepository.postSaleDate(user)
            successfulRegister.postValue(result)
            showLoader.postValue(false)
        }
    }
}