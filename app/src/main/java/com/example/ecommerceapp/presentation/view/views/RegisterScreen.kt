package com.example.ecommerceapp.presentation.view.views


import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ecommerceapp.presentation.view.viewmodel.RegisterViewModel
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.ecommerceapp.R
import com.example.ecommerceapp.presentation.view.viewmodel.RegisterViewModel.RegisterState

@Composable
fun RegisterScreen(
    viewModel: RegisterViewModel = hiltViewModel(),
    onRegisterSuccess: () -> Unit
) {
    var isSuccess = viewModel.isSuccess

    LaunchedEffect(isSuccess) {
        if (isSuccess) {
            onRegisterSuccess()
        }
    }

    val registerState = viewModel.registerState

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

    emailError: String?,
    passwordError: String?,
    confirmPasswordError: String?,

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
            color = MaterialTheme.colorScheme.primary,
            fontWeight = FontWeight.Bold,
            letterSpacing = 0.5.sp,
            maxLines = 1
        )
        Spacer(Modifier.height(10.dp))

        OutlinedTextField(
            value = name,
            onValueChange = onNameChange,
            label = { Text(stringResource(R.string.register_name)) })
        Spacer(Modifier.height(8.dp))

        OutlinedTextField(
            value = lastName,
            onValueChange = onLastNameChange,
            label = { Text(stringResource(R.string.register_last_name)) })
        Spacer(Modifier.height(8.dp))

        OutlinedTextField(
            value = nationality,
            onValueChange = onNationalityChange,
            label = { Text(stringResource(R.string.register_nationality)) })
        Spacer(Modifier.height(8.dp))

        OutlinedTextField(
            value = email,
            onValueChange = onEmailChange,
            label = { Text(stringResource(R.string.register_email)) },
            isError = emailError != null
        )
        emailError?.let {
            Text(
                text = it,
                color = MaterialTheme.colorScheme.error,
                style = MaterialTheme.typography.bodySmall
            )
        }

        Spacer(Modifier.height(8.dp))

        OutlinedTextField(
            value = password,
            onValueChange = onPasswordChange,
            label = { Text(stringResource(R.string.register_password)) },
            visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
            isError = passwordError != null,
            trailingIcon = {
                val icon =
                    if (passwordVisible) Icons.Default.Visibility else Icons.Default.VisibilityOff
                IconButton(onClick = { passwordVisible = !passwordVisible }) {
                    Icon(
                        imageVector = icon,
                        contentDescription = stringResource(R.string.password_toggle_description)
                    )
                }
            }
        )
        passwordError?.let {
            Text(
                text = it,
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
            isError = confirmPasswordError != null,
            trailingIcon = {
                val icon =
                    if (confirmPasswordVisible) Icons.Default.Visibility else Icons.Default.VisibilityOff
                IconButton(onClick = { confirmPasswordVisible = !confirmPasswordVisible }) {
                    Icon(
                        imageVector = icon,
                        contentDescription = stringResource(R.string.confirm_password_toggle_description)
                    )
                }
            }
        )
        confirmPasswordError?.let {
            Text(
                text = it,
                color = MaterialTheme.colorScheme.error,
                style = MaterialTheme.typography.bodySmall
            )
        }

        Spacer(Modifier.height(16.dp))


        Button(
            onClick = onRegisterClick,
            enabled = isFormValid && registerState !is RegisterState.Loading
        ) {
            if (registerState is RegisterState.Loading) {
                CircularProgressIndicator(modifier = Modifier.size(20.dp), strokeWidth = 2.dp)
            } else {
                Text(stringResource(R.string.register_button))
            }
        }

        if (registerState is RegisterState.Error) {
            Text(
                text = registerState.message,
                color = MaterialTheme.colorScheme.error,
                style = MaterialTheme.typography.bodySmall
            )
            Spacer(modifier = Modifier.height(8.dp))
        }


        if (registerState is RegisterState.Success) {
            Text(
                stringResource(R.string.register_success),
                color = MaterialTheme.colorScheme.primary
            )
        }

    }
}


