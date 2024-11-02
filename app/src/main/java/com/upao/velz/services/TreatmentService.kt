package com.upao.velz.services

import android.content.Context
import com.upao.velz.models.Treatment
import com.upao.velz.repositories.TreatmentRepository

class TreatmentService (context: Context) {
    private val repository =  TreatmentRepository(context)

    fun getTreatmentsService(): List<Treatment>{
        return repository.getTreatmentsRepository()
    }
    fun getTreatmentByNameService(name:String): Treatment? {
        return repository.getTreatmentByName(name)
    }

    /*fun addTreatmentService(){
        repository.insertDefaultTreatments()
    }*/
}