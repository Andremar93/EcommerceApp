package com.example.ecommerceapp.presentation.view.views

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.ecommerceapp.R
import com.example.ecommerceapp.presentation.view.viewmodel.RegisterViewModel
import com.example.ecommerceapp.presentation.view.viewmodel.RegisterViewModel.RegisterState

@Composable
fun RegisterScreen(
    viewModel: RegisterViewModel = hiltViewModel(),
    onRegisterSuccess: () -> Unit
) {
    val registerState = viewModel.registerState

    LaunchedEffect(viewModel.isSuccess) {
        if (viewModel.isSuccess) {
            onRegisterSuccess()
        }
    }

    RegisterScreenContent(
        name = viewModel.name,
        lastName = viewModel.lastName,
        nationality = viewModel.nationality,
        email = viewModel.email,
        password = viewModel.password,
        confirmPassword = viewModel.confirmPassword,

        emailError = viewModel.emailError,
        passwordError = viewModel.passwordError,
        confirmPasswordError = viewModel.passwordConfirmationError,

        registerState = registerState,
        isFormValid = viewModel.isFormValid,

        onNameChange = { viewModel.name = it; viewModel.onFieldChanged() },
        onLastNameChange = { viewModel.lastName = it; viewModel.onFieldChanged() },
        onNationalityChange = { viewModel.nationality = it; viewModel.onFieldChanged() },
        onEmailChange = { viewModel.email = it; viewModel.onFieldChanged() },
        onPasswordChange = { viewModel.password = it; viewModel.onFieldChanged() },
        onConfirmPasswordChange = { viewModel.confirmPassword = it; viewModel.onFieldChanged() },
        onRegisterClick = { viewModel.onRegisterClick() },
    )
}

@Composable
fun RegisterScreenContent(
    name: String,
    lastName: String,
    nationality: String,
    email: String,
    password: String,
    confirmPassword: String,

    emailError: RegisterViewModel.RegisterError?,
    passwordError: RegisterViewModel.RegisterError?,
    confirmPasswordError: RegisterViewModel.RegisterError?,

    registerState: RegisterState,
    isFormValid: Boolean,

    onNameChange: (String) -> Unit,
    onLastNameChange: (String) -> Unit,
    onNationalityChange: (String) -> Unit,
    onEmailChange: (String) -> Unit,
    onPasswordChange: (String) -> Unit,
    onConfirmPasswordChange: (String) -> Unit,
    onRegisterClick: () -> Unit,
) {
    var passwordVisible by remember { mutableStateOf(false) }
    var confirmPasswordVisible by remember { mutableStateOf(false) }

    // Mapeo inline para convertir RegisterError? a String? usando stringResource
    val emailErrorText = when (emailError) {
        RegisterViewModel.RegisterError.EmailRequired -> stringResource(R.string.error_email_required)
        RegisterViewModel.RegisterError.InvalidEmail -> stringResource(R.string.error_invalid_email)
        else -> null
    }

    val passwordErrorText = when (passwordError) {
        RegisterViewModel.RegisterError.PasswordRequired -> stringResource(R.string.error_password_required)
        RegisterViewModel.RegisterError.PasswordTooShort -> stringResource(R.string.error_short_password)
        else -> null
    }

    val confirmPasswordErrorText = when (confirmPasswordError) {
        RegisterViewModel.RegisterError.PasswordConfirmationRequired -> stringResource(R.string.error_confirm_password_required)
        RegisterViewModel.RegisterError.PasswordsDoNotMatch -> stringResource(R.string.error_passwords_do_not_match)
        else -> null
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = stringResource(R.string.register_title),
            style = MaterialTheme.typography.headlineLarge,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.primary
        )

        Spacer(Modifier.height(16.dp))

        listOf(
            Triple(name, onNameChange, R.string.register_name),
            Triple(lastName, onLastNameChange, R.string.register_last_name),
            Triple(nationality, onNationalityChange, R.string.register_nationality),
            Triple(email, onEmailChange, R.string.register_email)
        ).forEach { (value, onChange, labelId) ->
            OutlinedTextField(
                value = value,
                onValueChange = onChange,
                label = { Text(stringResource(labelId)) },
                shape = MaterialTheme.shapes.medium,
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = MaterialTheme.colorScheme.primary,
                    unfocusedBorderColor = MaterialTheme.colorScheme.outlineVariant
                ),
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(Modifier.height(8.dp))
        }

        // Mostrar error email si existe
        emailErrorText?.let {
            Text(
                it,
                color = MaterialTheme.colorScheme.error,
                style = MaterialTheme.typography.bodySmall
            )
        }

        OutlinedTextField(
            value = password,
            onValueChange = onPasswordChange,
            label = { Text(stringResource(R.string.register_password)) },
            visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
            trailingIcon = {
                val icon = if (passwordVisible) Icons.Default.Visibility else Icons.Default.VisibilityOff
                IconButton(onClick = { passwordVisible = !passwordVisible }) {
                    Icon(icon, contentDescription = null)
                }
            },
            modifier = Modifier.fillMaxWidth(),
            isError = passwordErrorText != null,
            shape = MaterialTheme.shapes.medium,
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = MaterialTheme.colorScheme.primary,
                unfocusedBorderColor = MaterialTheme.colorScheme.outlineVariant
            ),
        )

        // Mostrar error password si existe
        passwordErrorText?.let {
            Text(
                it,
                color = MaterialTheme.colorScheme.error,
                style = MaterialTheme.typography.bodySmall
            )
        }

        Spacer(Modifier.height(8.dp))

        OutlinedTextField(
            value = confirmPassword,
            onValueChange = onConfirmPasswordChange,
            label = { Text(stringResource(R.string.register_confirm_password)) },
            visualTransformation = if (confirmPasswordVisible) VisualTransformation.None else PasswordVisualTransformation(),
            trailingIcon = {
                val icon = if (confirmPasswordVisible) Icons.Default.Visibility else Icons.Default.VisibilityOff
                IconButton(onClick = { confirmPasswordVisible = !confirmPasswordVisible }) {
                    Icon(icon, contentDescription = null)
                }
            },
            modifier = Modifier.fillMaxWidth(),
            isError = confirmPasswordErrorText != null,
            shape = MaterialTheme.shapes.medium,
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = MaterialTheme.colorScheme.primary,
                unfocusedBorderColor = MaterialTheme.colorScheme.outlineVariant
            ),
        )

        // Mostrar error confirm password si existe
        confirmPasswordErrorText?.let {
            Text(
                it,
                color = MaterialTheme.colorScheme.error,
                style = MaterialTheme.typography.bodySmall
            )
        }

        Spacer(Modifier.height(16.dp))

        Button(
            onClick = onRegisterClick,
            enabled = isFormValid && registerState !is RegisterState.Loading,
            shape = MaterialTheme.shapes.medium
        ) {
            if (registerState is RegisterState.Loading) {
                CircularProgressIndicator(modifier = Modifier.size(20.dp), strokeWidth = 2.dp)
            } else {
                Text(stringResource(R.string.register_button))
            }
        }

        if (registerState is RegisterState.Error) {
            Spacer(modifier = Modifier.height(8.dp))
            Text(registerState.message, color = MaterialTheme.colorScheme.error)
        }

        if (registerState is RegisterState.Success) {
            Text(
                stringResource(R.string.register_success),
                color = MaterialTheme.colorScheme.primary
            )
        }
    }
}




