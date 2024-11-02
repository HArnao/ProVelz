package com.upao.velz.services

import android.content.Context
import android.util.Log
import com.upao.velz.models.Treatment
import com.upao.velz.repositories.TreatmentRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class TreatmentService (private val context: Context) {
    private val repository =  TreatmentRepository(context)

    suspend fun getTreatmentsService(): List<Treatment> {
        return withContext(Dispatchers.IO) {
            val treatments = repository.getTreatmentsRepository(context)
            treatments.forEach { treatment ->
                Log.d("TreatmentActivity", "Treatment: $treatment")
            }
            treatments
        }
    }

    suspend fun getTreatmentByNameService(name: String): Treatment? {
        return withContext(Dispatchers.IO) {
            repository.getTreatmentByName(context, name)
        }
    }

}