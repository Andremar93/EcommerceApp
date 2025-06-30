package com.example.ecommerceapp.presentation.view.views

import android.annotation.SuppressLint
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.unit.dp
import com.example.ecommerceapp.domain.model.User
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.testing.TestNavHostController
import coil.compose.AsyncImage
import com.example.ecommerceapp.presentation.view.components.layout.MainLayout
import com.example.ecommerceapp.presentation.view.viewmodel.CartViewModel
import com.example.ecommerceapp.presentation.view.viewmodel.UserViewModel

@Composable
fun UserScreen(
    navController: NavHostController
) {

    val userViewModel: UserViewModel = hiltViewModel()

    val user by userViewModel.user.collectAsState()

    MainLayout(
        navController = navController,
        topBarMessage = "Perfil de Usuario",
        selectedItem = "profile",
        mainContent = {
            UserScreenContent(
                userViewModel = userViewModel,
                navController = navController,
            )
        }
    )
}

@Composable
fun UserAvatarEditable(
    avatarUrl: String?,
    onImageClick: () -> Unit
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        if (avatarUrl.isNullOrBlank()) {
            Icon(
                imageVector = Icons.Default.Person,
                contentDescription = "Sin imagen",
                modifier = Modifier
                    .size(120.dp)
                    .clip(CircleShape)
                    .background(MaterialTheme.colorScheme.surface)
                    .border(
                        width = 1.dp,
                        color = MaterialTheme.colorScheme.primary,
                        shape = CircleShape
                    )
                    .padding(16.dp),

                tint = MaterialTheme.colorScheme.primary
            )

        } else {
            AsyncImage(
                model = avatarUrl,
                contentDescription = "Foto de perfil",
                modifier = Modifier
                    .size(120.dp)
                    .clip(CircleShape)
                    .background(MaterialTheme.colorScheme.surface)
                    .border(2.dp, MaterialTheme.colorScheme.primary, CircleShape),
                contentScale = ContentScale.Crop
            )
        }

        Spacer(modifier = Modifier.height(8.dp))

        Button(onClick = onImageClick, shape = RectangleShape) {
            Text(
                text = "Cambiar imagen",
                modifier = Modifier
                    .padding(4.dp),
            )
        }

    }
}


@Composable
fun UserProfileFields(
    user: User,
    userViewModel: UserViewModel
) {

    var isEditable by remember { mutableStateOf(false) }

    var name by remember(user.name) { mutableStateOf(user.name) }
    var lastName by remember(user.lastName) { mutableStateOf(user.lastName) }
    var email by remember(user.email) { mutableStateOf(user.email) }
//    var password by remember(user.password) { mutableStateOf(user.password) }
    var nationality by remember(user.nationality) { mutableStateOf(user.nationality) }

    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        OutlinedTextField(
            label = { Text("Nombre") },
            value = name,
            onValueChange = { name = it },
            enabled = isEditable
        )

        Spacer(modifier = Modifier.height(10.dp))

        OutlinedTextField(
            label = { Text("Apellido") },
            value = lastName,
            onValueChange = { lastName = it },
            enabled = isEditable
        )

        Spacer(modifier = Modifier.height(10.dp))

        OutlinedTextField(
            label = { Text("Nacionalidad") },
            value = nationality,
            onValueChange = { nationality = it },
            enabled = isEditable
        )

        Spacer(modifier = Modifier.height(10.dp))

        OutlinedTextField(
            label = { Text("Email") },
            value = email,
            onValueChange = { email = it },
            enabled = isEditable
        )

//        Spacer(modifier = Modifier.height(10.dp))
//
//        OutlinedTextField(
//            label = { Text("Password") },
//            value = password,
//            onValueChange = {password = it},
//            visualTransformation = PasswordVisualTransformation(),
//            enabled = isEditable
//        )


        Row {
            Button(
                onClick = { isEditable = !isEditable }
            ) {
                Text(if (isEditable) "Cancelar" else "Editar Perfil")
            }

        }


        if (isEditable) {
            Spacer(modifier = Modifier.height(8.dp))
            Button(
                onClick = {
                    val updatedUser = user.copy(
                        name = name,
                        lastName = lastName,
                        email = email,
                        nationality = nationality,
//                        password = password
                    )
                    userViewModel.updateUser(updatedUser)
                    isEditable = false
                }
            ) {
                Text("Guardar cambios")
            }
        }


    }
}

@Composable
fun UserScreenContent(
    userViewModel: UserViewModel,
    navController: NavHostController,
) {

    val user by userViewModel.user.collectAsState()

    var selectedImageUri by remember { mutableStateOf<Uri?>(null) }

    val launcher = rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) { uri ->
        selectedImageUri = uri
        uri?.toString()?.let {
            userViewModel.updateAvatar(it)
        }
    }

    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            UserAvatarEditable(
                avatarUrl = user.avatar,
                onImageClick = {
                    launcher.launch("image/*")
                }
            )

            Spacer(modifier = Modifier.height(24.dp))

            UserProfileFields(
                user = user,
                userViewModel = userViewModel
            )

            Spacer(modifier = Modifier.height(24.dp))

            Button(
                onClick = {
                    navController.navigate("orders") {
                        launchSingleTop = true
                        restoreState = true
                    }
                }
            ) {
                Text("Mis ordenes")
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun UserScreenContentPreview() {
    val fakeUser = User(
        name = "Andrea",
        lastName = "Martínez",
        email = "andrea@email.com",
        nationality = "Argentina",
//        password = "",
        avatar = ""
    )
//
//    val fakeViewModel = object : UserViewModel() {
//
//    }
//
//    UserScreenContent(
//        userViewModel = fakeViewModel
//    )
}



