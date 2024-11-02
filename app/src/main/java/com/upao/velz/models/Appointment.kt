package com.upao.velz.models

data class Appointment(
    val id: Int,
    val dateAppointment: String,
    val timeAppointment: String,
    val treatment: Int,
    val user: Int,
    val status: String = "Pendiente",
    val reminder: Int
){
    override fun toString(): String {
        return "User(id=$id, fecha='$dateAppointment', hora='$timeAppointment', descripción='${treatment}', paciente='${user}', recordatorio='$reminder')"
    }
}