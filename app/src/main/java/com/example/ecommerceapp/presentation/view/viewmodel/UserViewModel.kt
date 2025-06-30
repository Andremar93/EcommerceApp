package com.example.ecommerceapp.presentation.view.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ecommerceapp.data.user.UserDataSource
import com.example.ecommerceapp.domain.model.User
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserViewModel @Inject constructor(
    private val userDataSource: UserDataSource
) : ViewModel() {

    private val _user = MutableStateFlow(User())
    val user: MutableStateFlow<User> = _user

    init {
        loadUser()
    }

    private fun loadUser() {
        viewModelScope.launch {
            delay(1500)
            _user.value = userDataSource.getUserInfo()
        }
    }

    fun updateAvatar(avatarUrl: String) {
        _user.update { it.copy(avatar = avatarUrl) }
    }

    fun updateUser(updatedUser: User){
        _user.value = updatedUser
    }
}