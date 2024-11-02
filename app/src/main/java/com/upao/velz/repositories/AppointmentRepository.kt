package com.upao.velz.repositories

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.util.Log
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.upao.velz.apiLaravel.ApiService
import com.upao.velz.apiLaravel.Apiclient
import com.upao.velz.controllers.UserController
import com.upao.velz.models.Appointment
import com.upao.velz.models.RequestModel.AppointmentRequest
import com.upao.velz.models.Treatment
import com.upao.velz.models.User
import com.upao.velz.models.responseModel.AppDetailResponse
import com.upao.velz.sqlite.DbHelper
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.UUID

class AppointmentRepository(context: Context) {

    private val apiService = Apiclient.createService(ApiService::class.java)


    suspend fun addAppointment(appointment: Appointment): Boolean {
        Log.d("AppointmentRequest", "Request: $appointment")
        val appointmentRequest = AppointmentRequest(
            id = appointment.id,
            dateAppointment = appointment.dateAppointment,
            timeAppointment = appointment.timeAppointment,
            treatmentId = appointment.treatment,
            userId = appointment.user,
            reminder = appointment.reminder
        )
        Log.d("AppointmentRequest", "Request: $appointmentRequest")
        return try {
            val response = apiService.addAppointment(appointmentRequest)
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

    suspend fun getAppointments(): List<Appointment>? {
        return try {
            val response = apiService.getAppointment()
            if (response.isSuccessful) {
                response.body()?.map { appointmentResponse ->
                    Appointment(
                        id = appointmentResponse.id,
                        dateAppointment = appointmentResponse.dateAppointment,
                        timeAppointment = appointmentResponse.timeAppointment,
                        treatment = appointmentResponse.treatmentId,
                        user = appointmentResponse.userId,
                        status = appointmentResponse.status,
                        reminder = appointmentResponse.reminder
                    )
                }
            } else {
                null
            }
        } catch (e: Exception) {
            null
        }
    }

    suspend fun getListAppointment(id: Int): List<AppDetailResponse>? {
        Log.d("LoadAppointments123", "repos")
        return try {
            val response = apiService.getListAppointment(id)
            Log.d("LoadAppointments123", "Response: ${response.body()}")
            if (response.isSuccessful) {
                response.body()?.appointments
            } else {
                Log.e("LoadAppointments123", "Error en la respuesta: ${response.errorBody()?.string()}")
                null
            }
        } catch (e: Exception) {
            Log.e("LoadAppointments123", "Exception: ${e.message}", e)
            null
        }
    }

    suspend fun isAppointmentScheduled(date: String, time: String): Boolean {
        val appointments = getAppointments()
        return appointments?.any { it.dateAppointment == date && it.timeAppointment == time } ?: false
    }

}