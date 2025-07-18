package com.example.ecommerceapp.data.repository

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.example.ecommerceapp.domain.local.data_source.UsersLocalDataSource
import com.example.ecommerceapp.domain.model.User
import com.example.ecommerceapp.domain.remote.data_source.UsersRemoteDataSource
import com.example.ecommerceapp.domain.repository.UserRepository
import com.example.ecommerceapp.domain.use_case.user.LoginResult
import com.example.ecommerceapp.domain.use_case.user.RegisterResult
import com.example.ecommerceapp.domain.use_case.user.UpdateUserResult
import retrofit2.HttpException

class UserRepositoryImpl(
    private val remote: UsersRemoteDataSource,
    private val local: UsersLocalDataSource
) : UserRepository {

    override suspend fun registerUser(user: User): RegisterResult {
        return try {
            val registeredUser = remote.register(user)
            local.insertUser(registeredUser)
            RegisterResult.Success
        } catch (e: HttpException) {
            val message = when (e.code()) {
                400 -> "Datos inválidos"
                409 -> "El usuario ya existe"
                else -> "Error desconocido del servidor: ${e.message()}"
            }
            RegisterResult.Error(message)
        } catch (e: Exception) {
            Log.e("Register", "Error inesperado: ${e.message ?: "Desconocido"}")
            RegisterResult.Error("Error inesperado.")
        }
    }

    override suspend fun login(email: String, password: String): LoginResult {
        return try {
            val user = remote.login(email, password)
            local.logoutAllUsers()
            local.insertUser(user)
            local.setActiveUser(user.id)
            LoginResult.Success
        } catch (e: HttpException) {
            val offlineSuccess = loginOffline(email, password)
            if (offlineSuccess) return LoginResult.Success
            LoginResult.Error("Login fallido: ${e.code()}")

            val message = when (e.code()) {
                404 -> "Usuario no encontrado"
                401 -> "Contraseña incorrecta"
                else -> "Error desconocido del servidor: ${e.message()}"
            }

            LoginResult.Error(message)
        } catch (e: Exception) {
            Log.e("Login", "Error inesperado")
            LoginResult.Error("Error inesperado: ${e.message ?: "Desconocido"}")
        }
    }

    override suspend fun loginOffline(email: String, password: String): Boolean {
        val localUser = local.getUserByEmail(email) ?: return false
        return localUser.password == password
    }

    override suspend fun getActiveUser(): User {
        return local.getActiveUser() ?: throw Exception("No active user found")
    }

    override suspend fun updateUser(user: User): UpdateUserResult {
        return try {
            remote.updateUser(user)
            local.updateUser(user)
            local.setActiveUser(user.id)
            UpdateUserResult.Success
        } catch (e: HttpException) {
            Log.e("UpdateUser", "Error inesperado: ${e.message ?: "Desconocido"}")
            UpdateUserResult.Error("Error al actualizar el usuario: ${e.message ?: "Desconocido"}")
        }
    }

     override suspend fun logoutUser(userId: String): Boolean {
        return local.logoutUser(userId)
    }

}