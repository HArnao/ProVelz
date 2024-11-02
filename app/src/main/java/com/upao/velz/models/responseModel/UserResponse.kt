package com.upao.velz.models.responseModel

import com.google.gson.annotations.SerializedName
import com.upao.velz.models.User

data class UserResponse(
    @SerializedName("message") val message: String,
    @SerializedName("user") val user: User?
){

}
