package com.upao.velz.services

import android.content.Context
import android.util.Log
import com.upao.velz.models.Appointment
import com.upao.velz.repositories.AppointmentRepository

class AppointmentService(context: Context) {

    private val appointmentRepository = AppointmentRepository(context)

    fun addAppointment(appointment: Appointment){
        appointmentRepository.addAppointment(appointment)
    }

    fun isAppointmentScheduled(date: String, time: String): Boolean{
        Log.d("AppointmentActivity", "Fecha seleccionada - service: $date, Hora seleccionada: $time")
        return appointmentRepository.isAppointmentScheduled(date, time)
    }

}