package com.upao.velz.repositories

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.upao.velz.models.User
import com.upao.velz.sqlite.DbHelper
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class UserRepository (context: Context){
    private val dbHelper = DbHelper(context)
    private val firebaseAuth = FirebaseAuth.getInstance()

    fun registerUserFirebase(user: User):Boolean{
        val email = user.email
        val password = user.password

        firebaseAuth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    registerUser(user)
                } else {
                    throw task.exception ?: Exception("Error al registrar usuario")
                }
            }
        return true
    }

    fun registerUser(user:User): Boolean {

        val db = dbHelper.writableDatabase
        val values = ContentValues().apply {
            put("nombre", user.name)
            put("apellido", user.lastname)
            put("email", user.email)
            put("phone", user.phone)
            put("dni", user.dni)
            put("password", user.password)
            put("createdAT", getCurrentDate())
            put("updatedAT", getCurrentDate())
        }

        val newRowId = db.insert("usuarios", null, values)
        db.close()

        return newRowId != -1L
    }


    /*
    fun loginUser(context: Context, email: String, password: String): Boolean {
        // Instancia de bd en modo lectura
        val db = dbHelper.readableDatabase

        // Consulta en la base de datos con los campos deseados
        val cursor = db.rawQuery("SELECT * FROM usuarios WHERE email = ? AND password = ?", arrayOf(email, password)
        )

        val successful = cursor.count > 0

        cursor.close()
        db.close()

        return successful
    }*/

    fun loginUser(email: String, password: String, onResult: (Boolean, String?) -> Unit) {
        firebaseAuth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {

                    onResult(true, null)
                } else {
                    onResult(false, "Error al iniciar sesión")
                }
            }
    }

    @SuppressLint("Range")
    fun getUserByEmail(email: String): User? {
        val db = dbHelper.readableDatabase
        var user: User? = null

        val cursor = db.rawQuery("SELECT * FROM usuarios WHERE email = ?" , arrayOf(email))

        if (cursor.moveToFirst()) {
            // Extraer los datos del cursor
            val id = cursor.getInt(cursor.getColumnIndex("idUsuario"))
            val name = cursor.getString(1)
            val lastname = cursor.getString(2)
            val email = cursor.getString(3)
            val phone = cursor.getString(4)
            val dni = cursor.getString(5)
            val password = cursor.getString(6)

            // Crear el objeto User
            user = User(id, name, lastname, email, phone, dni, password)
        }

        cursor.close()
        db.close()

        return user
    }

    @SuppressLint("Range")
    fun getUserById(userId: Int): User? {
        val db = dbHelper.readableDatabase
        var user: User? = null

        val cursor = db.rawQuery("SELECT * FROM usuarios WHERE idUsuario = ?", arrayOf(userId.toString()))

        if (cursor.moveToFirst()) {
            val id = cursor.getInt(cursor.getColumnIndex("idUsuario"))
            val name = cursor.getString(cursor.getColumnIndex("nombre"))
            val lastname = cursor.getString(cursor.getColumnIndex("apellido"))
            val email = cursor.getString(cursor.getColumnIndex("email"))
            val phone = cursor.getString(cursor.getColumnIndex("phone"))
            val dni = cursor.getString(cursor.getColumnIndex("dni"))
            val password = cursor.getString(cursor.getColumnIndex("password"))
            user = User(id, name, lastname, email, phone, dni, password)
        }

        cursor.close()
        db.close()

        return user
    }

    private fun getCurrentDate(): String {
        val dateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
        return dateFormat.format(Date())
    }


}