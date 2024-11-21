package com.upao.velz.models

data class Dentist(
    val id: Int,
    val name: String,
    val lastname: String,
    val specialty: String,
    val email: String,
    val service: List<String>,
    val experience: List<String>,
    val imageResId: Int,
)
