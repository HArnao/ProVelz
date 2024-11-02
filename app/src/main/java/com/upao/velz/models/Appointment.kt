package com.upao.velz.models

data class Appointment(
    val id: Int,
    val dateAppointment: String,
    val timeAppointment: String,
    val description: Treatment,
    val user: User,
    val status: String = "Pendiente",
    val reminder: Int
){
    override fun toString(): String {
        return "User(id=$id, fecha='$dateAppointment', hora='$timeAppointment', descripción='${description.name}', paciente='${user.name}', recordatorio='$reminder')"
    }
}