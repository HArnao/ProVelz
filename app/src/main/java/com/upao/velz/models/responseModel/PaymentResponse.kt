package com.upao.velz.models.responseModel

import com.google.gson.annotations.SerializedName

data class PaymentResponse(
    @SerializedName("message") val message: String
)