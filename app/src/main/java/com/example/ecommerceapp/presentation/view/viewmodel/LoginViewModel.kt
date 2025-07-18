package com.example.ecommerceapp.presentation.view.viewmodel

import android.util.Log
import android.util.Patterns
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.lifecycle.viewModelScope
import com.example.ecommerceapp.BuildConfig
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

    var emailError by mutableStateOf<String?>(null)
    var passwordError by mutableStateOf<String?>(null)
    var errorMessage by mutableStateOf<String?>(null)
    var isLoggedIn by mutableStateOf(false)

    val isFormValid: Boolean
        get() = emailError == null && passwordError == null && email.isNotBlank() && password.isNotBlank()

    fun validateFields() {
        emailError = if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            "El email no tiene un formato válido"
        } else null

        passwordError = if (password.length < 8) {
            "La contraseña debe tener al menos 8 caracteres"
        } else null
    }

    fun onLoginClicked() {
        validateFields()

        if (!isFormValid) {
            errorMessage = "Corrige los errores antes de continuar"
            return
        }

        if (email.isBlank() || password.isBlank()) {
            errorMessage = "Todos los campos son obligatorios"
            return
        }

        login(email.trim(), password)
    }

    var loginState by mutableStateOf<LoginState>(LoginState.Idle)

    fun login(email: String, password: String) {
        viewModelScope.launch {
            loginState = LoginState.Loading
            when (val result = loginUseCase(email, password)) {
                is LoginResult.Success -> {
                    loginState = LoginState.Success
                    isLoggedIn = true
                    errorMessage = null
                }
                is LoginResult.Error -> {
                    loginState = LoginState.Error(result.message)
                    isLoggedIn = false
                    errorMessage = result.message
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


}
