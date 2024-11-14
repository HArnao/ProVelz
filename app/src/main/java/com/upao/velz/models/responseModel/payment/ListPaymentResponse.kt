package com.upao.velz.models.responseModel.payment

import com.google.gson.annotations.SerializedName

data class ListPaymentResponse(
    @SerializedName("payments") val payments: List<PaymentResponse>
)
