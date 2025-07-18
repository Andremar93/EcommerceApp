package com.example.ecommerceapp.presentation.view.viewmodel

import android.util.Patterns
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ecommerceapp.domain.model.User
import com.example.ecommerceapp.domain.use_case.user.RegisterResult
import com.example.ecommerceapp.domain.use_case.user.RegisterUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(
    val registerUseCase: RegisterUseCase
) : ViewModel() {

    var email by mutableStateOf("")
    var lastName by mutableStateOf("")
    var nationality by mutableStateOf("")
    var name by mutableStateOf("")
    var password by mutableStateOf("")
    var confirmPassword by mutableStateOf("")

    //    Errores
    var emailError by mutableStateOf<String?>(null)
    var passwordError by mutableStateOf<String?>(null)
    var passwordConfirmationError by mutableStateOf<String?>(null)
    var generalError by mutableStateOf<String?>(null)

    var isSuccess by mutableStateOf(false)

    val isFormValid: Boolean
        get() = emailError == null &&
                passwordError == null &&
                passwordConfirmationError == null &&
                name.isNotBlank() &&
                lastName.isNotBlank() &&
                nationality.isNotBlank() &&
                email.isNotBlank() &&
                password.isNotBlank() &&
                confirmPassword.isNotBlank()

    fun onFieldChanged() {
        validateFields()
        generalError = null
    }

    var registerState by mutableStateOf<RegisterState>(RegisterState.Idle)

    fun onRegisterClick() {
        validateFields()

        if (!isFormValid) {
            generalError = "Corrige los errores antes de continuar"
            return
        }

        val user = User(
            name = name.trim(),
            lastName = lastName.trim(),
            nationality = nationality.trim(),
            email = email.trim(),
            password = password.trim(),
            avatar = ""
        )

        viewModelScope.launch {
            registerState = RegisterState.Loading

            when (val result = registerUseCase(user)) {
                is RegisterResult.Success -> {
                    registerState = RegisterState.Success
                    generalError = null
                    isSuccess = true
                }

                is RegisterResult.Error -> {
                    registerState = RegisterState.Error(result.message)
                    generalError = result.message
                }
            }
        }

    }

    private fun validateFields() {

        emailError = when {
            email.isBlank() -> "El campo email es obligatorio"
            !Patterns.EMAIL_ADDRESS.matcher(email)
                .matches() -> "El email no tiene un formato válido"

            else -> null
        }

        passwordError = when {
            password.isBlank() -> "El campo contraseña es obligatorio"
            password.length < 8 -> "La contraseña debe tener al menos 8 caracteres"
            else -> null
        }

        passwordConfirmationError = when {
            confirmPassword.isBlank() -> "Debes confirmar tu contraseña"
            password != confirmPassword -> "Las contraseñas no coinciden"
            else -> null
        }
    }

    private fun clearForm() {
        name = ""
        lastName = ""
        nationality = ""
        email = ""
        password = ""
        confirmPassword = ""
    }

    sealed class RegisterState {
        object Idle : RegisterState()
        object Loading : RegisterState()
        object Success : RegisterState()
        data class Error(val message: String) : RegisterState()
    }

}
