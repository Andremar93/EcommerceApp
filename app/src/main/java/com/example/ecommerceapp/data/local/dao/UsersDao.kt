package com.example.ecommerceapp.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.ecommerceapp.data.local.entity.UserEntity

@Dao
interface UsersDao {

    @Insert(onConflict = OnConflictStrategy.Companion.REPLACE)
    suspend fun insertUser(user: UserEntity)

    @Query("SELECT * FROM users WHERE email = :email")
    suspend fun getUserByEmail(email: String): UserEntity?

//    @Query("SELECT * FROM user WHERE email = :email")
//    suspend fun authenticateUser(email: String, password: String): UsersEntity?
}