package com.example.ecommerceapp.presentation.view.viewmodel

import android.util.Patterns
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ecommerceapp.domain.model.User
import com.example.ecommerceapp.domain.use_case.RegisterUseCase
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
    var errorMessage by mutableStateOf<String?>(null)
    var isSuccess by mutableStateOf(false)

    var registerState by mutableStateOf<RegisterState>(RegisterState.Idle)


    val isFormValid: Boolean
        get() = emailError == null && passwordError == null &&
                email.isNotBlank() && password.isNotBlank() &&
                lastName.isNotBlank() && name.isNotBlank() &&
                nationality.isNotBlank() && passwordConfirmationError == null

    fun onRegisterClick() {
        validateFields()

        if (!isFormValid) {
            errorMessage = "Corrige los errores antes de continuar"
            return
        }

        val newUser = User(
            name = name.trim(),
            lastName = lastName.trim(),
            nationality = nationality.trim(),
            email = email.trim(),
            password = password.trim()
        )


        registerUser(email = email, name = name, lastName = lastName, nationality = nationality, password = password, userImageUrl = "")

//        val registered = FakeUserRepository.registerUser(newUser)
//
//        if (!registered) {
//            errorMessage = "Este correo ya está registrado"
//            isSuccess = false
//            return
//        }

//        isSuccess = true
//        errorMessage = null
        clearForm()
    }

    fun validateFields() {

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

    fun registerUser(
        email: String,
        password: String,
        nationality: String,
        userImageUrl: String,
        name: String,
        lastName: String
    ) {
        viewModelScope.launch {
            val user  = User(
                name = name.trim(),
                lastName = lastName.trim(),
                nationality = nationality.trim(),
                email = email.trim(),
                password = password.trim(),
                avatar = ""
            )
            registerState = RegisterState.Loading
            val success =
                registerUseCase(user)
            if (success) {
                registerState = RegisterState.Success
                isSuccess = true
                errorMessage = null
                clearForm()
            } else {
                registerState = RegisterState.Error("Error al registrar el usuario")
                isSuccess = false
                errorMessage = "Error al registrar el usuario"
            }
        }
    }

    sealed class RegisterState {
        object Idle : RegisterState()
        object Loading : RegisterState()
        object Success : RegisterState()
        data class Error(val message: String) : RegisterState()
    }
}
