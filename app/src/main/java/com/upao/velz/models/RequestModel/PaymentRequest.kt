package com.upao.velz.models.RequestModel

import com.google.gson.annotations.SerializedName

data class PaymentRequest(
    @SerializedName("appointment_id") val appointmentId: Int,
    @SerializedName("amount") val amount: Int,
    @SerializedName("payment_date") val payment_date: String
)
