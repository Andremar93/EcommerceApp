package com.example.ecommerceapp.data.local.data_source

import com.example.ecommerceapp.data.local.dao.UsersDao
import com.example.ecommerceapp.data.local.mappers.toDomain
import com.example.ecommerceapp.data.local.mappers.toEntity
import com.example.ecommerceapp.domain.local.data_source.UsersLocalDataSource
import com.example.ecommerceapp.domain.model.User
import javax.inject.Inject

class UsersLocalDataSourceImpl @Inject constructor(
    private val usersDao: UsersDao
) : UsersLocalDataSource {

    override suspend fun insertUser(user: User) {
        usersDao.insertUser(user.toEntity())
    }

    override suspend fun getUserByEmail(email: String): User? {
        return usersDao.getUserByEmail(email)?.toDomain()
    }

    override suspend fun setActiveUser(userId: String) {
        return usersDao.setActiveUser(userId)
    }

    override suspend fun getActiveUser(): User? {
        return usersDao.getActiveUser()?.toDomain()
    }

    override suspend fun logoutUser(userId: String): Boolean {
        return usersDao.logoutUserById(userId) > 0
    }

    override suspend fun updateUser(user: User) {
        return usersDao.updateUser(user.toEntity())
    }

    override suspend fun logoutAllUsers(){
        return usersDao.logoutAllUsers()
    }
}