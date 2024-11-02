package com.upao.velz.models.responseModel
import com.google.gson.annotations.SerializedName

data class ListAppResponse(
    @SerializedName("appointments") val appointments: List<AppDetailResponse>
)
