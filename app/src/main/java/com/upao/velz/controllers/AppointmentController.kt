package com.upao.velz.controllers

import android.content.Context
import android.util.Log
import com.upao.velz.models.Appointment
import com.upao.velz.services.AppointmentService

class AppointmentController(context: Context) {
    private val appointmentService = AppointmentService(context)

    fun addAppointment(appointment: Appointment) {
        appointmentService.addAppointment(appointment)
    }

    fun isAppointmentScheduled(date: String, time: String): Boolean{
        Log.d("AppointmentActivity", "Fecha seleccionada - CONTROLLER: $date, Hora seleccionada: $time")
        return appointmentService.isAppointmentScheduled(date, time)
    }
}