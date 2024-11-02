package com.upao.velz.controllers

import android.content.Context
import android.util.Log
import com.upao.velz.models.User
import com.upao.velz.repositories.UserRepository
import com.upao.velz.services.UserService

class UserController(context: Context) {

    private val userService = UserService(context)

    fun registerUser(user: User){
        userService.registerUser(user)
    }

    /*fun loginUser(context: Context, email: String, password: String): Boolean{
       return userService.loginUser(context, email, password)
    }*/

    fun loginUser(email: String, password: String, onResult: (Boolean, String?) -> Unit) {
        userService.loginUser(email, password, onResult)
    }

    fun getUserByEmail(email: String): User? {
        return userService.getUserByEmail(email)
    }
}