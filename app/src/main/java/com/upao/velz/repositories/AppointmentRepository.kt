package com.upao.velz.repositories

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.util.Log
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.upao.velz.controllers.UserController
import com.upao.velz.models.Appointment
import com.upao.velz.models.Treatment
import com.upao.velz.models.User
import com.upao.velz.sqlite.DbHelper
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.UUID

class AppointmentRepository(context: Context) {

    private val dbHelper = DbHelper(context)
    private val userRepository = UserRepository(context)
    private val treatmentRepository = TreatmentRepository(context)

     fun addAppointment(appointment: Appointment): Boolean {
        val db = dbHelper.writableDatabase
        val values = ContentValues().apply {
            put("dateAppointment", appointment.dateAppointment)
            put("timeAppointment", appointment.timeAppointment)
            put("description", appointment.description.name)
            put("idUsuario", appointment.user.id)
            put("status", appointment.status)
            put("reminder", appointment.reminder)
            put("createdAT", getCurrentDate())
            put("updatedAT", getCurrentDate())
        }

        val newRowId = db.insert("appointments", null, values)
        db.close()

         return newRowId != -1L
    }

    private fun getCurrentDate(): String {
        val dateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
        return dateFormat.format(Date())
    }

    @SuppressLint("Range")
    fun getAppointments(): List<Appointment> {
        val db = dbHelper.readableDatabase
        val cursor = db.rawQuery("SELECT * FROM appointments WHERE status = 'Pendiente'", null)
        val appointments = mutableListOf<Appointment>()

        if (cursor.moveToFirst()) {
            do {
                val id = cursor.getInt(cursor.getColumnIndex("id"))
                val dateAppointment = cursor.getString(cursor.getColumnIndex("dateAppointment"))
                val timeAppointment = cursor.getString(cursor.getColumnIndex("timeAppointment"))
                val descriptionText = cursor.getString(cursor.getColumnIndex("description"))
                val userId = cursor.getInt(cursor.getColumnIndex("idUsuario"))
                val status = cursor.getString(cursor.getColumnIndex("status"))
                val reminder = cursor.getInt(cursor.getColumnIndex("reminder"))

                val user = userRepository.getUserById(userId) ?: User(0, "ABC", "ABC", "ABC", "ABC", "ABC", "ABC")
                val treatment = treatmentRepository.getTreatmentByName(descriptionText) ?: Treatment(
                    UUID.randomUUID(), "ABC", "ABC", listOf(), listOf(), 0, "ABC")

                val appointment = Appointment(
                    id = id,
                    dateAppointment = dateAppointment,
                    timeAppointment = timeAppointment,
                    description = treatment,
                    user = user,
                    status = status,
                    reminder = reminder
                )
                appointments.add(appointment)
            } while (cursor.moveToNext())
        }

        cursor.close()
        db.close()

        return appointments
    }

    fun isAppointmentScheduled(date: String, time: String): Boolean {
        val appointments = getAppointments()
        return appointments.any { it.dateAppointment == date && it.timeAppointment == time }
    }

}