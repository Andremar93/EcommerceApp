package com.example.ecommerceapp.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.ecommerceapp.data.database.entities.UsersEntity


@Dao
interface UserDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUser(user: UsersEntity)

    @Query("SELECT * FROM user WHERE email = :email")
    suspend fun getUserByEmail(email: String): UsersEntity?

    @Query("SELECT * FROM user WHERE email = :email")
    suspend fun authenticateUser(email: String, password: String): UsersEntity?
}
