package com.example.ecommerceapp.presentation.view.viewmodel

import android.app.Application
import android.net.Uri
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cloudinary.Cloudinary
import com.example.ecommerceapp.domain.model.User
import com.example.ecommerceapp.domain.use_case.user.GetActiveUserUseCase
import com.example.ecommerceapp.domain.use_case.user.LogoutUserUseCase
import com.example.ecommerceapp.domain.use_case.user.UpdateUserUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserViewModel @Inject constructor(
    val myApplication: Application,
    private val getActiveUserUseCase: GetActiveUserUseCase,
    private val updateUserUseCase: UpdateUserUseCase,
    private val logoutUserUseCase: LogoutUserUseCase
) : AndroidViewModel(myApplication) {

    private val _user = MutableStateFlow(User())
    val user: MutableStateFlow<User> = _user

    private val _navigateToLogin = MutableStateFlow(false)
    val navigateToLogin = _navigateToLogin

    private val _isImageUploading = MutableStateFlow(false)
    val isImageUploading: StateFlow<Boolean> = _isImageUploading

    private val _isUpdatingUser = MutableStateFlow(false)
    val isUpdatingUser: StateFlow<Boolean> = _isUpdatingUser


    private val cloudinary = Cloudinary(
        mapOf(
            "cloud_name" to "dhnjnynfp",
            "api_key" to "876161229965998",
            "api_secret" to "Mo4luqlaWwwh94jpcuKK9UFO1EI",
        )
    )

    fun loadUser() {
        viewModelScope.launch {
            _user.value = getActiveUserUseCase.invoke()
        }
    }

    fun logoutUser() {
        viewModelScope.launch {
            val result = logoutUserUseCase.invoke(userId = _user.value.id)
            if (result) {
                _navigateToLogin.value = true
            }
        }
    }


    fun uploadImage(imageUri: Uri) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                _isImageUploading.value = true
                val inputStream =
                    getApplication<Application>().contentResolver.openInputStream(imageUri)
                val uploadResult =
                    cloudinary.uploader().upload(inputStream, mapOf("upload_preset" to "PeyaImgs"))
                val imageUrl = uploadResult["secure_url"] as? String
                val updatedProfile = _user.value.copy(avatar = imageUrl ?: "")
                _user.value = updatedProfile

            } catch (e: Exception) {
                e.printStackTrace()
                null
            } finally {
                _isImageUploading.value = false
            }
        }
    }

    fun updateUser(updatedUser: User) {
        _user.value = updatedUser
        _isUpdatingUser.value = true


        viewModelScope.launch {
            try {
                updateUserUseCase(updatedUser).let { result ->
                }
            } catch (e: Exception) {
                e.printStackTrace()
            } finally {
                _isUpdatingUser.value = false
            }
        }
    }
}