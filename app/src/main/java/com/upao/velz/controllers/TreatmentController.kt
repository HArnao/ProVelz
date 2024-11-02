package com.upao.velz.controllers

import android.content.Context
import com.upao.velz.models.Treatment
import com.upao.velz.services.TreatmentService

class TreatmentController(context: Context){
    private val service = TreatmentService(context)

    fun getTreatmentController(): List<Treatment>{
        return service.getTreatmentsService()
    }

    fun getTreatmentByNameController(name:String): Treatment? {
        return service.getTreatmentByNameService(name)
    }

    /*fun addTreatmentController(){
        service.addTreatmentService()
    }*/
}