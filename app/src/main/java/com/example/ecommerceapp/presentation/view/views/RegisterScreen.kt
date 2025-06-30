package com.example.ecommerceapp.presentation.view.views


import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
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
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.ecommerceapp.presentation.view.viewmodel.RegisterViewModel
import androidx.hilt.navigation.compose.hiltViewModel

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

    RegisterScreenContent(
        viewModel = hiltViewModel(),
        confirmPassword = viewModel.confirmPassword,
        emailError = viewModel.emailError,
        passwordError = viewModel.passwordError,
        confirmPasswordError = viewModel.passwordConfirmationError,
        isFormValid = viewModel.isFormValid,
        onEmailChange = {
            viewModel.email = it
            viewModel.validateFields()
        },
        onPasswordChange = {
            viewModel.password = it
            viewModel.validateFields()
        },
        onConfirmPasswordChange = {
            viewModel.confirmPassword = it
            viewModel.validateFields()
        },
        onRegisterClick = {
            viewModel.onRegisterClick()
        }
    )
}
//
//@Preview(showBackground = true)
//@Composable
//fun RegisterScreenPreview() {
//    RegisterScreenContent(
//        name = "Andrea",
//        lastName = "Martínez",
//        email = "andrea@example.com",
//        password = "123456",
//        confirmPassword = "123456",
//        nationality = "Venezolana",
//        isFormValid = true,
//        onEmailChange = {},
//        onPasswordChange = {},
//        onConfirmPasswordChange = {},
//        onRegisterClick = {}
//    )
//}

@Composable
fun RegisterScreenContent(
    viewModel: RegisterViewModel,
    confirmPassword: String,
    emailError: String? = null,
    passwordError: String? = null,
    confirmPasswordError: String? = null,
    isFormValid: Boolean = false,
    onEmailChange: (String) -> Unit,
    onPasswordChange: (String) -> Unit,
    onConfirmPasswordChange: (String) -> Unit,
    onRegisterClick: () -> Unit
){

    var passwordVisible by remember { mutableStateOf(false) }
    var confirmPasswordVisible by remember { mutableStateOf(false) }

    Column(modifier = Modifier
        .fillMaxSize()
        .fillMaxWidth()
        .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        OutlinedTextField(
            value = viewModel.name,
            onValueChange = {
                viewModel.name = it
            },
            label = { Text("Nombre")},
        )

        Spacer(modifier = Modifier.height(10.dp))

        OutlinedTextField(
            value = viewModel.lastName,
            onValueChange = {
                viewModel.lastName = it
            },
            label = { Text("Apellido")},
        )

        Spacer(modifier = Modifier.height(10.dp))

        OutlinedTextField(
            value = viewModel.nationality,
            onValueChange = {
                viewModel.nationality = it
            },
            label = { Text("Nationality")},
        )

        Spacer(modifier = Modifier.height(10.dp))

        OutlinedTextField(
            value= viewModel.email,
            onValueChange = {
                viewModel.email = it
                onEmailChange(it)
            },
            label = { Text("Email")},
            isError = emailError!= null
        )

        emailError?.let{
            Text(
                text = it,
                color = MaterialTheme.colorScheme.error,
                style = MaterialTheme.typography.bodySmall,
            )
        }

        Spacer(modifier = Modifier.height(10.dp))

        OutlinedTextField(
            value = viewModel.password,
            onValueChange = {
                viewModel.password = it
                onPasswordChange(it)
            },
            label = { Text("Contraseña")},
            visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
            isError = passwordError != null,
            trailingIcon = {
                val image = if (passwordVisible)
                    Icons.Filled.Visibility
                else
                    Icons.Filled.VisibilityOff

                IconButton(onClick = { passwordVisible = !passwordVisible }) {
                    Icon(imageVector = image, contentDescription = "Toggle Password Visibility")
                }
            }
        )

        passwordError?.let{
            Text(
                text = it,
                color = MaterialTheme.colorScheme.error,
                style = MaterialTheme.typography.bodySmall,
            )
        }

        Spacer(modifier = Modifier.height(10.dp))

        OutlinedTextField(
            value = confirmPassword,
            onValueChange = {
                onConfirmPasswordChange(it)
            },
            visualTransformation = if (confirmPasswordVisible) VisualTransformation.None else PasswordVisualTransformation(),
            label = { Text("Confirmar Contraseña")},
            isError = confirmPasswordError != null,
            trailingIcon = {
                val image = if (confirmPasswordVisible)
                    Icons.Filled.Visibility
                else
                    Icons.Filled.VisibilityOff

                IconButton(onClick = { confirmPasswordVisible = !confirmPasswordVisible }) {
                    Icon(imageVector = image, contentDescription = "Toggle Confirm Password Visibility")
                }
            }
        )

        confirmPasswordError?.let{
            Text(
                text = it,
                color = MaterialTheme.colorScheme.error,
                style = MaterialTheme.typography.bodySmall,
            )
        }
        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = { onRegisterClick()},
            enabled = isFormValid
        ){
            Text("Registrarme")
        }

    }
}


