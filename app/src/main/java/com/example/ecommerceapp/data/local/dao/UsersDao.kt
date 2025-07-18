package com.example.ecommerceapp.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Update
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.example.ecommerceapp.data.local.entity.UserEntity
import com.example.ecommerceapp.data.local.relations.UserWithOrders

@Dao
interface UsersDao {

    @Insert(onConflict = OnConflictStrategy.Companion.REPLACE)
    suspend fun insertUser(user: UserEntity)

    @Query("SELECT * FROM users WHERE email = :email")
    suspend fun getUserByEmail(email: String): UserEntity?

    @Query("SELECT * FROM users WHERE isLoggedIn = 1 LIMIT 1")
    suspend fun getActiveUser(): UserEntity?

    @Transaction
    @Query("SELECT * FROM users WHERE id = :userId")
    suspend fun getUserWithOrders(userId: String): UserWithOrders

    @Query("UPDATE users SET isLoggedIn = 0")
    suspend fun logoutAllUsers()

    @Query("UPDATE users SET isLoggedIn = 0 WHERE id = :userId")
    suspend fun logoutUserById(userId: String): Int

    @Query("UPDATE users SET isLoggedIn = 1 WHERE id = :userId")
    suspend fun setActiveUser(userId: String)

    @Update
    suspend fun updateUser(user: UserEntity)
}