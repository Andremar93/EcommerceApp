package com.example.ecommerceapp.data.repository

import android.util.Log
import com.example.ecommerceapp.data.local.dao.UsersDao
import com.example.ecommerceapp.data.remote.LoginRequest
import com.example.ecommerceapp.data.remote.RegisterRequest
import com.example.ecommerceapp.data.remote.UserApiService
import com.example.ecommerceapp.domain.model.User
import com.example.ecommerceapp.domain.repository.UserRepository
import com.example.ecommerceapp.domain.use_case.user.LoginResult
import com.example.ecommerceapp.domain.use_case.user.RegisterResult
import retrofit2.HttpException

class UserRepositoryImpl(
    private val apiService: UserApiService,
    private val userDao: UsersDao
) : UserRepository {

    val user = User(
        name = "Test",
        lastName = "User",
        email = "test@test.com",
        nationality = "Argentina",
    )

    override fun getUserInfo(): User = user

    override suspend fun registerUser(user: User): RegisterResult {
        return try {
            val response = apiService.registerUser(
                RegisterRequest(
                    email = user.email,
                    nationality = user.nationality,
                    encryptedPassword = user.password,
                    userImageUrl = user.avatar,
                    fullName = "${user.name} ${user.lastName}"
                )
            )

            val userDto = response.user
            userDao.insertUser(userDto.toEntity())

            Log.d("Login", "Usuario registrado: ${userDto.toEntity()}")

            RegisterResult.Success

        } catch (e: HttpException) {
            val errorCode = e.code()
            Log.e("Login", "Error HTTP: $errorCode")

            val message = when (errorCode) {
                404 -> "Endpoint no encontrado"
                409 -> "El usuario ya existe"
                else -> "Error desconocido del servidor: ${e.message()}"
            }

            RegisterResult.Error(message)
        } catch (e: Exception) {
            Log.e("Register User", "Error inesperado: ${e.message}")
            RegisterResult.Error("Error inesperado: ${e.message ?: "Desconocido"}")

        }
    }

    override suspend fun login(email: String, password: String): LoginResult {
        return try {
            val response = apiService.login(LoginRequest(email, password))
            val userDto = response.user
            userDao.insertUser(userDto.toEntity())

            LoginResult.Success
        } catch (e: HttpException) {
            loginOffline(email, password)
            val errorCode = e.code()
            Log.e("Login", "Error HTTP: $errorCode")

            val offlineSuccess = loginOffline(email, password)
            if (offlineSuccess) {
                return LoginResult.Success
            }

            val message = when (errorCode) {
                404 -> "Usuario no encontrado"
                401 -> "Contraseña incorrecta"
                else -> "Error desconocido del servidor: ${e.message()}"
            }

            LoginResult.Error(message)
        } catch (e: Exception) {
            Log.e("Login", "Error inesperado: ${e.message}")
            LoginResult.Error("Error inesperado: ${e.message ?: "Desconocido"}")
        }
    }

    suspend fun loginOffline(email: String, password: String): Boolean {
        val user = userDao.getUserByEmail(email) ?: return false
        return password == user.password
    }


}