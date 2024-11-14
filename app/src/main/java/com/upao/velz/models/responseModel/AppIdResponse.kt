package com.upao.velz.models.responseModel

import com.google.gson.annotations.SerializedName

data class AppIdResponse(
    @SerializedName("message") val message: String,
    @SerializedName("appointment_id") val appointmentId: Int
)
