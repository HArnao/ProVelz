package com.upao.velz.models

data class Treatment(
    var id: Int,
    var name: String,
    var description: String,
    val procedure: List<String>,
    val benefits: List<String>,
    val imageResId: Int,
    val videoUri: String
)
