package com.upao.velz.models.RequestModel

import com.google.gson.annotations.SerializedName

data class AppointmentRequest (
    val id: Int,
    @SerializedName("date_appointment") val dateAppointment: String,
    @SerializedName("time_appointment") val timeAppointment: String,
    @SerializedName("treatment_id") val treatmentId: Int,
    @SerializedName("user_id") val userId: Int,
    val reminder: Int
){

}
