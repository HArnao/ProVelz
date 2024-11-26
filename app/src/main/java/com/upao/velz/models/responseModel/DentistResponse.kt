package com.upao.velz.models.responseModel

import com.google.gson.annotations.SerializedName

data class DentistResponse(
    @SerializedName("id") val id: Int,
    @SerializedName("name") val name: String,
    @SerializedName("lastname") val lastname: String,
    @SerializedName("specialty") val specialty: String,
    @SerializedName("email") val email: String,
){}
