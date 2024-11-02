package com.upao.velz.controllers

import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.upao.velz.models.User
import com.upao.velz.repositories.UserRepository
import com.upao.velz.services.UserService
import kotlinx.coroutines.launch

class UserController(context: Context): ViewModel() {

    private val userService = UserService(context)

    fun registerUser(user: User) {
        viewModelScope.launch {
            val success = userService.registerUser(user)
            if (success) {
                Log.d("Register", "Registro exitoso")
            } else {
                Log.e("Register", "Error al registrar")
            }
        }
    }

    fun loginUser(email: String, password: String, onResult: (Boolean, String?) -> Unit) {
        viewModelScope.launch {
            val (success, errorMessage) = userService.loginUser(email, password)
            onResult(success, errorMessage)
        }
    }

    fun getUserByEmail(email: String, onResult: (User?) -> Unit) {
        viewModelScope.launch {
            val user = userService.getUserByEmail(email)
            onResult(user)
        }
    }

    fun getUserById(id: Int, onResult: (User?) -> Unit) {
        viewModelScope.launch {
            val user = userService.getUserById(id)
            onResult(user)
        }
    }
}