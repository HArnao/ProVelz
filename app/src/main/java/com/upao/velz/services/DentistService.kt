package com.upao.velz.services

import android.content.Context
import android.util.Log
import com.upao.velz.models.Dentist
import com.upao.velz.repositories.DentistRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class DentistService(private val context: Context) {

    private val dentistRepository = DentistRepository(context)

    suspend fun getDentistService(): List<Dentist> {
        return withContext(Dispatchers.IO) {
            val dentists = dentistRepository.getDentistRepository(context)
            dentists.forEach { dentist ->
                Log.d("DentistActivity", "dentist: $dentist")
            }
            dentists
        }
    }

    suspend fun getDentistIdService(id: Int): Dentist? {
        return withContext(Dispatchers.IO) {
            dentistRepository.getDentistId(context, id)
        }
    }
}