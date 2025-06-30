package com.example.ecommerceapp.data.fakeRepositories


import com.example.ecommerceapp.domain.model.User

object FakeUserRepository {
    private val users = mutableListOf<User>(
        User(name = "Test", lastName = "User", email = "test@test.com", nationality = "Argentina")
    )

    fun registerUser(user: User): Boolean {
        if (users.any { it.email.equals(user.email, ignoreCase = true) }) return false
        users.add(user)
        return true
    }

    fun login(email: String, password: String): Boolean {
        return true
//        users.any {
//            it.email.equals(email, ignoreCase = true) && it.password == password
//        }
    }

    fun getAllUsers(): List<User> = users
}
