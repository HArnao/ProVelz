package com.upao.velz.controllers

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.upao.velz.models.Treatment
import com.upao.velz.services.TreatmentService
import kotlinx.coroutines.launch

class TreatmentController(private val context: Context): ViewModel(){
    private val service = TreatmentService(context)

    fun getTreatmentController(callback: (List<Treatment>) -> Unit) {
        viewModelScope.launch {
            try {
                val treatments = service.getTreatmentsService()
                callback(treatments)
            } catch (e: Exception) {
                callback(emptyList())
            }
        }
    }

    fun getTreatmentByName(name: String, callback: (Treatment?) -> Unit) {
        viewModelScope.launch {
            try {
                val treatment = service.getTreatmentByNameService(name)
                callback(treatment)
            } catch (e: Exception) {
                callback(null)
            }
        }
    }

}