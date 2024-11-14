package com.upao.velz.controllers

import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.upao.velz.models.Appointment
import com.upao.velz.models.User
import com.upao.velz.models.responseModel.AppDetailResponse
import com.upao.velz.services.AppointmentService
import kotlinx.coroutines.launch

class AppointmentController(context: Context) : ViewModel() {
    private val appointmentService = AppointmentService(context)

    fun addAppointment(appointment: Appointment, callback: (Int?) -> Unit) {
        viewModelScope.launch {
            val appointmentId = appointmentService.addAppointment(appointment)
            callback(appointmentId)
        }
    }

    fun editAppointment(id: Int, appointment: Appointment) {
        viewModelScope.launch {
            val success = appointmentService.editAppointment(id, appointment)
            if (success) {
                Log.d("Cita", "Cita postergada con exito")
            } else {
                Log.e("Cita", "Error al postergar cita")
            }
        }
    }

    fun isAppointmentScheduled(date: String, time: String, callback: (Boolean) -> Unit) {
        viewModelScope.launch {
            val success = appointmentService.isAppointmentScheduled(date, time)
            callback(success)
        }
    }

    fun getListAppointments(id: Int, callback: (List<AppDetailResponse>?) -> Unit) {
        viewModelScope.launch {
            val appointments = appointmentService.getListAppointments(id)
            callback(appointments)
        }
    }

    fun getAppointments(callback: (List<AppDetailResponse>?) -> Unit) {
        viewModelScope.launch {
            val appointments = appointmentService.getAppointments()
            callback(appointments)
        }
    }
}