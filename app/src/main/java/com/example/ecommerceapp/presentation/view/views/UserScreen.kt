package com.example.ecommerceapp.presentation.view.views

import android.Manifest
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.saveable.Saver
import androidx.compose.runtime.saveable.mapSaver
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.example.ecommerceapp.R
import com.example.ecommerceapp.domain.model.User
import com.example.ecommerceapp.presentation.view.components.layout.MainLayout
import com.example.ecommerceapp.presentation.view.viewmodel.UserViewModel

@Composable
fun UserScreen(
    navController: NavHostController
) {
    val userViewModel: UserViewModel = hiltViewModel()

    MainLayout(
        navController = navController,
        topBarMessage = stringResource(id = R.string.user_profile_title),
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
fun UserScreenContent(
    userViewModel: UserViewModel,
    navController: NavHostController,
) {
    val user by userViewModel.user.collectAsState()
    var selectedImageUri by rememberSaveable { mutableStateOf<Uri?>(null) }
    var isEditable by rememberSaveable { mutableStateOf(false) }
    val isUpdatingUser by userViewModel.isUpdatingUser.collectAsState()
    val navigateToLogin by userViewModel.navigateToLogin.collectAsState()

    val context = LocalContext.current

    val launcher = rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) { uri ->
        selectedImageUri = uri
        if (uri != null) {
            userViewModel.uploadImage(uri)
        }
    }

    val permissionLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        if (isGranted) {
            launcher.launch("image/*")
        } else {

//            val finalGranted = ContextCompat.checkSelfPermission(context, imagePermission) == PackageManager.PERMISSION_GRANTED
//            if (finalGranted) {
//                launcher.launch("image/*")
//            } else {
//            }
            Toast.makeText(
                context,
                context.getString(R.string.profile_files_permission),
                Toast.LENGTH_LONG
            ).show()

        }
    }

    val imagePermission = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
        Manifest.permission.READ_MEDIA_IMAGES
    } else {
        Manifest.permission.READ_EXTERNAL_STORAGE
    }

    LaunchedEffect(navigateToLogin) {
        if (navigateToLogin) {
            navController.navigate("login") {
                popUpTo(0) { inclusive = true }
            }
        }
    }

    LaunchedEffect(Unit) {
        userViewModel.loadUser()
    }

    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(scrollState)
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        UserAvatarEditable(
            avatarUrl = user.avatar,
            isEditable = isEditable,
            userViewModel = userViewModel,
            onImageClick = {
                if (ContextCompat.checkSelfPermission(
                        context,
                        imagePermission
                    ) == PackageManager.PERMISSION_GRANTED
                ) {
                    launcher.launch("image/*")
                } else {
                    permissionLauncher.launch(imagePermission)
                }
            }
        )

        UserProfileFields(
            user = user,
            userViewModel = userViewModel,
            isEditable = isEditable,
            onEditableChange = { isEditable = it })

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            PrimaryButton(stringResource(id = R.string.my_orders)) {
                navController.navigate("orders") {
                    launchSingleTop = true
                    restoreState = true
                }
            }

            PrimaryButton(stringResource(id = R.string.logout)) {
                userViewModel.logoutUser()
            }
        }
    }

    if (isUpdatingUser) {
        AlertDialog(
            onDismissRequest = {},
            confirmButton = {},
            title = { Text(stringResource(id = R.string.updating_profile)) },
            text = {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    CircularProgressIndicator(
                        modifier = Modifier.size(50.dp),
                        color = MaterialTheme.colorScheme.primary,
                        strokeWidth = 4.dp
                    )
                }
            }
        )
    }
}

@Composable
fun UserAvatarEditable(
    avatarUrl: String?,
    userViewModel: UserViewModel,
    isEditable: Boolean,
    onImageClick: () -> Unit
) {
    val isImageUploading by userViewModel.isImageUploading.collectAsState()

    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        val avatarModifier = Modifier
            .size(120.dp)
            .clip(CircleShape)
            .background(MaterialTheme.colorScheme.surfaceContainerHighest)

        if (isImageUploading) {
            AlertDialog(
                onDismissRequest = {},
                confirmButton = {},
                title = { Text(stringResource(id = R.string.uploading_image)) },
                text = {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        CircularProgressIndicator(
                            modifier = Modifier.size(50.dp),
                            color = MaterialTheme.colorScheme.primary
                        )
                    }
                }
            )
        }

        if (avatarUrl.isNullOrBlank()) {
            Icon(
                imageVector = Icons.Default.Person,
                contentDescription = stringResource(id = R.string.no_image_description),
                modifier = avatarModifier.padding(16.dp),
                tint = MaterialTheme.colorScheme.primary
            )
        } else {
            AsyncImage(
                model = avatarUrl,
                contentDescription = stringResource(id = R.string.profile_picture_description),
                modifier = avatarModifier,
                contentScale = ContentScale.Crop
            )
        }

        Spacer(modifier = Modifier.height(8.dp))

        if (isEditable) {
            FilledTonalButton(
                onClick = onImageClick,
                shape = MaterialTheme.shapes.medium
            ) {
                Text(stringResource(id = R.string.change_image))
            }
        }
    }
}


val UserSaver: Saver<User, *> = mapSaver(
    save = {
        mapOf(
            "name" to it.name,
            "lastName" to it.lastName,
            "email" to it.email,
            "nationality" to it.nationality,
            "avatar" to it.avatar
        )
    },
    restore = {
        User(
            name = it["name"] as String,
            lastName = it["lastName"] as String,
            email = it["email"] as String,
            nationality = it["nationality"] as String,
            avatar = it["avatar"] as String
        )
    }
)

@Composable
fun UserProfileFields(
    user: User,
    userViewModel: UserViewModel,
    isEditable: Boolean,
    onEditableChange: (Boolean) -> Unit
) {
    var name by rememberSaveable(user.name) { mutableStateOf(user.name) }
    var lastName by rememberSaveable(user.lastName) { mutableStateOf(user.lastName) }
    var nationality by rememberSaveable(user.nationality) { mutableStateOf(user.nationality) }
    var email by rememberSaveable(user.email) { mutableStateOf(user.email) }

    var originalUser by rememberSaveable(stateSaver = UserSaver) { mutableStateOf(user) }

    Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
        ProfileField(stringResource(id = R.string.first_name), name, isEditable) { name = it }
        ProfileField(stringResource(id = R.string.last_name), lastName, isEditable) {
            lastName = it
        }
        ProfileField(
            stringResource(id = R.string.nationality),
            nationality,
            isEditable
        ) { nationality = it }
        ProfileField(stringResource(id = R.string.email), email, isEditable) { email = it }

        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            FilledTonalButton(
                onClick = {
                    if (isEditable) {
                        name = originalUser.name
                        lastName = originalUser.lastName
                        nationality = originalUser.nationality
                        email = originalUser.email
                        onEditableChange(false)
                    } else {
                        originalUser = user
                        onEditableChange(true)
                    }
                },
                shape = MaterialTheme.shapes.medium
            ) {
                Text(
                    if (isEditable) stringResource(id = R.string.cancel)
                    else stringResource(id = R.string.edit_profile)
                )
            }

            if (isEditable) {
                PrimaryButton(
                    text = stringResource(id = R.string.save),
                    onClick = {
                        val updatedUser = user.copy(
                            name = name,
                            lastName = lastName,
                            nationality = nationality,
                            email = email
                        )
                        userViewModel.updateUser(updatedUser)
                        onEditableChange(false)
                    }
                )
            }
        }
    }
}


@Composable
fun ProfileField(
    label: String,
    value: String,
    isEditable: Boolean,
    onValueChange: (String) -> Unit
) {
    OutlinedTextField(
        label = { Text(label) },
        value = value,
        onValueChange = onValueChange,
        enabled = isEditable,
        singleLine = true,
        modifier = Modifier
            .fillMaxWidth()
            .heightIn(min = 56.dp),
        shape = MaterialTheme.shapes.medium,
        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = MaterialTheme.colorScheme.primary,
            unfocusedBorderColor = MaterialTheme.colorScheme.outlineVariant,
            cursorColor = MaterialTheme.colorScheme.primary
        )
    )
}


@Composable
fun PrimaryButton(text: String, onClick: () -> Unit) {
    Button(
        onClick = onClick,
        shape = MaterialTheme.shapes.medium,
        colors = ButtonDefaults.buttonColors(
            containerColor = MaterialTheme.colorScheme.primary,
            contentColor = MaterialTheme.colorScheme.onPrimary
        ),
        modifier = Modifier
            .height(48.dp)
            .padding(vertical = 4.dp)
    ) {
        Text(
            text = text,
            style = MaterialTheme.typography.labelLarge
        )
    }
}

