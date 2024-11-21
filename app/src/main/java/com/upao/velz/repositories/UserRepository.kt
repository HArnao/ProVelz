package com.upao.velz.repositories

import android.content.Context
import android.util.Log
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.FirebaseAuth
import com.upao.velz.apiLaravel.ApiService
import com.upao.velz.apiLaravel.Apiclient
import com.upao.velz.models.RequestModel.LoginRequest
import com.upao.velz.models.RequestModel.UserRequest
import com.upao.velz.models.User
import kotlinx.coroutines.tasks.await
import com.google.firebase.storage.FirebaseStorage

class UserRepository (context: Context){

    private val firebaseAuth = FirebaseAuth.getInstance()
    val apiService = Apiclient.createService(ApiService::class.java)

    suspend fun registerUserFirebase(user: User): Boolean {
        return try {
            val email = user.email
            val password = user.password

            if (password != null) {
                firebaseAuth.createUserWithEmailAndPassword(email, password).await()
                registerUser(user)
                true
            } else {
                Log.e("Register", "El password es nulo, no se puede registrar el usuario en Firebase")
                false
            }

            registerUser(user)
        } catch (e: Exception) {
            Log.e("Register", "Error al registrar en Firebase: ${e.message}")
            false
        }
    }

    suspend fun registerUser(user: User): Boolean {
        return try {
            val response = apiService.registerUser(user)

            if (response.isSuccessful) {
                Log.d("Register-Api", "Registro exitoso en Laravel")
                true
            } else {
                Log.e("Register-Api", "Error al registrar en Laravel: ${response.errorBody()?.string()}")
                false
            }
        } catch (e: Exception) {
            Log.e("Register-Api", "Error en la llamada a la API: ${e.message}")
            false
        }
    }

    suspend fun getUserByEmail(email: String): User? {
        return try {
            val response = apiService.getUserByEmail(email)

            if (response.isSuccessful) {
                Log.d("UserRepository", "Respuesta exitosa: ${response.body()}")
                response.body()?.let { userResponse ->
                    userResponse.user?.let { user ->
                        User(
                            id = user.id,
                            name = user.name,
                            lastname = user.lastname,
                            email = user.email,
                            phone = user.phone,
                            dni = user.dni,
                        )
                    }
                }
            } else {
                Log.e("UserRepository", "Error al obtener usuario por email: ${response.errorBody()?.string()}")
                null
            }
        } catch (e: Exception) {
            Log.e("UserRepository", "Error en la llamada a la API: ${e.message}")
            null
        }
    }

    suspend fun loginUser(email: String, password: String): Pair<Boolean, String?> {

        return try {
            firebaseAuth.signInWithEmailAndPassword(email, password).await()
            Pair(true, null)

        } catch (firebaseException: Exception) {
            try {
                val userRequest = LoginRequest(email = email, password = password)
                val response = apiService.loginUser(userRequest)
                if (response.isSuccessful && response.body() != null) {
                    Pair(true, null)
                } else {
                    val errorMessage = response.errorBody()?.string() ?: "Error desconocido"
                    Pair(false, errorMessage)
                }
            } catch (apiException: Exception) {
                Pair(false, "Error al iniciar sesión en la API: ${apiException.message}")
            }
        }
    }

    suspend fun getUserById(userId: Int): User? {
        return try {
            val response = apiService.getUserById(userId)

            if (response.isSuccessful) {
                response.body()?.let { userResponse ->
                    userResponse.user?.let { user ->
                        User(
                            id = user.id,
                            name = user.name,
                            lastname = user.lastname,
                            email = user.email,
                            phone = user.phone,
                            dni = user.dni,
                        )
                    }
                }
            } else {
                Log.e("UserRepository", "Error al obtener usuario por ID: ${response.errorBody()?.string()}")
                null
            }
        } catch (e: Exception) {
            Log.e("UserRepository", "Error en la llamada a la API: ${e.message}")
            null
        }
    }

    suspend fun editUserProfile(id: Int, user: User): Boolean {
        val userRequest = UserRequest(
            name = user.name,
            lastname = user.lastname,
            phone = user.phone,
            dni = user.dni,
        )

        Log.d("UserRequest", "Request: $userRequest")

        return try {
            val response = apiService.editUser(id, userRequest)
            Log.d("API Response", response.toString())
            if (response.isSuccessful) {
                true
            } else {
                Log.e("API Error", response.errorBody()?.string() ?: "Unknown error")
                false
            }
        } catch (e: Exception) {
            Log.e("API Exception", e.message ?: "Error desconocido")
            false
        }
    }

}