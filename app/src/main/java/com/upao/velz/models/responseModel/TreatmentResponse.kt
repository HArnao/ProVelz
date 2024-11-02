package com.upao.velz.models.responseModel

import com.google.gson.annotations.SerializedName

data class TreatmentResponse(
    @SerializedName("id") val id: Int,
    @SerializedName("name") val name: String,
    @SerializedName("description") val description: String,
    @SerializedName("created_at") val createdAt: String,
    @SerializedName("updated_at") val updatedAt: String
){}
