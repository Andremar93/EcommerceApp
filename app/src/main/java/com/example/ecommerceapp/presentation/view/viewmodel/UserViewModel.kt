package com.example.ecommerceapp.presentation.view.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ecommerceapp.domain.model.User
import com.example.ecommerceapp.domain.use_case.user.GetActiveUserUseCase
import com.example.ecommerceapp.domain.use_case.user.UpdateUserUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserViewModel @Inject constructor(
    private val getActiveUserUseCase: GetActiveUserUseCase,
    private val updateUserUseCase: UpdateUserUseCase
) : ViewModel() {

    private val _user = MutableStateFlow(User())
    val user: MutableStateFlow<User> = _user

    fun loadUser() {
        viewModelScope.launch {
            _user.value = getActiveUserUseCase.invoke()
        }
    }

    fun updateAvatar(avatarUrl: String) {
        _user.update { it.copy(avatar = avatarUrl) }
    }

    fun updateUser(updatedUser: User) {
        _user.value = updatedUser
        viewModelScope.launch {
            updateUserUseCase(updatedUser).let { result ->
               println("Update result: $result")
//                when (result) {
//                    is UpdateUserUserCase.UpdateUserResult.Success -> {
//                        // Handle success, e.g., show a message or update UI
//                    }
//                    is UpdateUserUserCase.UpdateUserResult.Error -> {
//                        // Handle error, e.g., show an error message
//                    }
//                }
            }

        }
    }
}