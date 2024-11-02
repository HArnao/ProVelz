package com.upao.velz.services

import android.content.Context
import android.provider.ContactsContract.CommonDataKinds.Email
import android.util.Log
import com.upao.velz.models.User
import com.upao.velz.repositories.UserRepository

class UserService(context: Context) {

    private val userRepository = UserRepository(context)

    suspend fun registerUser(user: User): Boolean {
        return userRepository.registerUserFirebase(user)
    }

    suspend fun loginUser(email: String, password: String): Pair<Boolean, String?> {
        return userRepository.loginUser(email, password)
    }

    suspend fun getUserByEmail(email: String): User? {
        return userRepository.getUserByEmail(email)
    }

    suspend fun getUserById(id: Int): User? {
        return userRepository.getUserById(id)
    }

}