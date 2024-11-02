package com.upao.velz.models

data class User(
    val id: Int,
    val name: String,
    val lastname: String,
    val email: String,
    val phone: String,
    val dni: String,
    val password: String
){
    override fun toString(): String {
        return "User(id=$id, nombre='$name', apellido='$lastname', email='$email', phone='$phone', dni='$dni')"
    }
}
