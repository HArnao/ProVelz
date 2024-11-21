package com.upao.velz.controllers

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.upao.velz.models.Dentist
import com.upao.velz.services.DentistService
import kotlinx.coroutines.launch

class DentistController(private val context: Context): ViewModel() {

    private val dentistService = DentistService(context)

    fun getDentistController(callback: (List<Dentist>) -> Unit) {
        viewModelScope.launch {
            try {
                val dentists = dentistService.getDentistService()
                callback(dentists)
            } catch (e: Exception) {
                callback(emptyList())
            }
        }
    }

    fun getDentistId(id: Int, callback: (Dentist?) -> Unit) {
        viewModelScope.launch {
            try {
                val dentist = dentistService.getDentistIdService(id)
                callback(dentist)
            } catch (e: Exception) {
                callback(null)
            }            }
    }

}