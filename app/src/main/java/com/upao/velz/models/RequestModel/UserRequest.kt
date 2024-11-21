package com.upao.velz.models.RequestModel

import com.google.gson.annotations.SerializedName

data class UserRequest(
    @SerializedName("name") val name: String,
    @SerializedName("lastname") val lastname: String,
    @SerializedName("phone") val phone: String,
    @SerializedName("dni") val dni: String
)
