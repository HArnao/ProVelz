package com.upao.velz.services

import android.content.Context
import android.provider.ContactsContract.CommonDataKinds.Email
import android.util.Log
import com.upao.velz.models.User
import com.upao.velz.repositories.UserRepository

class UserService(context: Context) {

    private val userRepository = UserRepository(context)

    fun registerUser(user: User){
        userRepository.registerUserFirebase(user)
    }

    /*fun loginUser(context: Context, email: String, password: String): Boolean{
        return userRepository.loginUser(context, email,password)
    }*/

    fun loginUser(email: String, password: String, onResult: (Boolean, String?) -> Unit) {
        userRepository.loginUser(email, password, onResult)
    }

    fun getUserByEmail(email: String): User? {
        return userRepository.getUserByEmail(email)
    }

}