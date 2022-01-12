package com.mypro.suitmedia.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mypro.suitmedia.model.UserResponse
import com.mypro.suitmedia.repository.ThirdActivityRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ThirdActivityViewModel : ViewModel() {
    var repo = ThirdActivityRepository()
    var userResponse = MutableLiveData<UserResponse>()
    var errorResponse: MutableLiveData<String>? = null

    fun getUsers(page: Int, perPage: Int) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                try {
                    userResponse.postValue(repo.getUsers(page, perPage))
                } catch (throwable: Throwable) {
                    errorResponse?.postValue(throwable.localizedMessage)
                }
            }
        }
    }
}