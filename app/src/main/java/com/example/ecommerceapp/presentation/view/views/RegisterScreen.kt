package com.example.ecommerceapp.presentation.view.views


import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
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
import androidx.compose.material3.OutlinedTextFieldDefaults
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

        emailError?.let {
            Text(it, color = MaterialTheme.colorScheme.error, style = MaterialTheme.typography.bodySmall)
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
            isError = passwordError != null,
            shape = MaterialTheme.shapes.medium
        )
        passwordError?.let {
            Text(it, color = MaterialTheme.colorScheme.error, style = MaterialTheme.typography.bodySmall)
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
            isError = confirmPasswordError != null,
            shape = MaterialTheme.shapes.medium
        )
        confirmPasswordError?.let {
            Text(it, color = MaterialTheme.colorScheme.error, style = MaterialTheme.typography.bodySmall)
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
            Text(stringResource(R.string.register_success), color = MaterialTheme.colorScheme.primary)
        }
    }

}


