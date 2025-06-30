package com.example.ecommerceapp.data.user

import retrofit2.HttpException
import android.util.Log
import com.example.ecommerceapp.domain.model.User
import javax.inject.Inject

class UserDataSourceImpl @Inject constructor(
    private val apiService: UserApiService
) : UserDataSource {

    val user = User(
        name = "Test",
        lastName = "User",
        email = "test@test.com",
        nationality = "Argentina",
    )

    override fun getUserInfo(): User = user

    override suspend fun registerUser(user: User): Boolean
    {
        return try {
            val response = apiService.registerUser(RegisterRequest(
                email = user.email,
                nationality = user.nationality,
                encryptedPassword = user.password,
                userImageUrl = user.avatar,
                fullName = user.name + user.lastName
            ))

            Log.d("Login", "Usuario registrado: ${response.user.email}")

            true
        } catch (e: HttpException) {
            val errorCode = e.code()
            Log.e("Login", "Error HTTP: $errorCode")

            if (errorCode == 404) {
                Log.e("Login", "Endpoint no encontrado")
            } else if (errorCode == 401) {
                Log.e("Login", "Contraseña incorrecta")
            } else {
                Log.e("Login", "Error desconocido del servidor: ${e.message()}")
            }

            false
        } catch (e: Exception) {
            Log.e("Login", "Error inesperado: ${e.message}")
            false
        }
    }

    override suspend fun login(email: String, password: String): Boolean
    {
        return try {
            val response = apiService.login(LoginRequest(email, password))
            Log.d("Login", "Usuario logueado: ${response.user.email}")
            true
        } catch (e: HttpException)
        {
            val errorCode = e.code()
            Log.e("Login", "Error HTTP: $errorCode")

            if (errorCode == 404) {
                Log.e("Login", "Usuario no encontrado")
            } else if (errorCode == 401) {
                Log.e("Login", "Contraseña incorrecta")
            } else {
                Log.e("Login", "Error desconocido del servidor: ${e.message()}")
            }

            false
        } catch (e: Exception)
        {
            Log.e("Login", "Error inesperado: ${e.message}")
            false
        }
    }

}