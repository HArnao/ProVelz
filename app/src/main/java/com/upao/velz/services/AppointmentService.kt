package com.upao.velz.services

import android.content.Context
import android.util.Log
import com.upao.velz.models.Appointment
import com.upao.velz.models.responseModel.AppDetailResponse
import com.upao.velz.repositories.AppointmentRepository

class AppointmentService(context: Context) {

    private val appointmentRepository = AppointmentRepository(context)

    suspend fun addAppointment(appointment: Appointment): Boolean{
        return appointmentRepository.addAppointment(appointment)
    }

    suspend fun getListAppointments(id: Int): List<AppDetailResponse>?{
        return appointmentRepository.getListAppointment(id)
    }

    suspend fun isAppointmentScheduled(date: String, time: String): Boolean{
        return appointmentRepository.isAppointmentScheduled(date, time)
    }

}