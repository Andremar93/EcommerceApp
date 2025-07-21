package com.example.ecommerceapp.presentation.view.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ecommerceapp.domain.use_case.user.LoginResult
import com.example.ecommerceapp.domain.use_case.user.LoginUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginUseCase: LoginUseCase
) : ViewModel() {

    var email by mutableStateOf("")
    var password by mutableStateOf("")

    var emailError by mutableStateOf<LoginError?>(null)
    var passwordError by mutableStateOf<LoginError?>(null)
    var error by mutableStateOf<LoginError?>(null)

    var isLoggedIn by mutableStateOf(false)

    val isFormValid: Boolean
        get() = emailError == null && passwordError == null &&
                email.isNotBlank() && password.isNotBlank()

    var loginState by mutableStateOf<LoginState>(LoginState.Idle)

    fun validateFields() {
        emailError = if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            LoginError.InvalidEmail
        } else null

        passwordError = if (password.length < 8) {
            LoginError.ShortPassword
        } else null
    }

    fun onLoginClicked() {
        validateFields()

        if (!isFormValid) {
            error = LoginError.FixErrors
            return
        }

        if (email.isBlank() || password.isBlank()) {
            error = LoginError.EmptyFields
            return
        }

        login(email.trim(), password)
    }

    private fun login(email: String, password: String) {
        viewModelScope.launch {
            loginState = LoginState.Loading
            when (val result = loginUseCase(email, password)) {
                is LoginResult.Success -> {
                    loginState = LoginState.Success
                    isLoggedIn = true
                    error = null
                }
                is LoginResult.Error -> {
                    loginState = LoginState.Error(result.message)
                    isLoggedIn = false
                }
            }
        }
    }

    sealed class LoginState {
        object Idle : LoginState()
        object Loading : LoginState()
        object Success : LoginState()
        data class Error(val message: String) : LoginState()
    }

    sealed class LoginError {
        object InvalidEmail : LoginError()
        object ShortPassword : LoginError()
        object EmptyFields : LoginError()
        object FixErrors : LoginError()
        data class Unknown(val message: String) : LoginError()
    }
}
