package com.upao.velz.models.responseModel.payment

import com.google.gson.annotations.SerializedName
import com.upao.velz.models.responseModel.AppDetailResponse

data class PaymentResponse(
    @SerializedName("id") val id: Int,
    @SerializedName("appointment_id") val appointmentId: Int,
    @SerializedName("amount") val amount: Int,
    @SerializedName("status") val status: String,
    @SerializedName("payment_date") val paymentDate: String,
    @SerializedName("appointment") val appointment: AppDetailResponse
)
