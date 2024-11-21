package com.upao.velz.repositories

import android.content.Context
import com.upao.velz.R
import com.upao.velz.apiLaravel.ApiService
import com.upao.velz.apiLaravel.Apiclient
import com.upao.velz.models.Dentist
import com.upao.velz.models.responseModel.DentistResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class DentistRepository(context: Context) {

    val apiService = Apiclient.createService(ApiService::class.java)

    suspend fun getDentists(): List<DentistResponse> {
        val response = apiService.getDentists()
        return withContext(Dispatchers.Main) {
            if (response.isSuccessful) {
                response.body()!!
            } else {
                emptyList()
            }
        }
    }

    suspend fun getDentistRepository(context: Context): List<Dentist> {

        val dentistResponses = getDentists()

        val service1 = listOf(
            "1. Alineación Dental con Brackets.",
            "2. Ortodoncia Preventiva para Niños y Adolescentes",
            "3. Retenedores y Mantenimiento Post-Tratamiento."
        )
        val service2  = listOf(
            "1. hTratamiento de Enfermedades Periodontales",
            "2. Limpieza Dental Profunda (Curetaje)",
            "3. Injertos de Encías"
        )
        val service3 = listOf(
            "1. Blanqueamiento Dental Profesional",
            "2. Carillas Dentales",
            "3. Contorneado Estético Dental",
        )
        val service4  = listOf(
            "1. Implantes Dentales",
            "2. Regeneración Ósea",
            "3. Coronas sobre Implantes",
        )
        val experience = listOf(
            "1. Más de 10 años de experiencia",
            "2. Egresado de la carrera de Estomatología de la UPAO",
            "3. Trabajando en las mejores clínicas odontóligcas de Trujillo y Lima.",
        )

        return dentistResponses.map { dentistResponse ->
            when (dentistResponse.id) {
                1 -> Dentist(
                    id = dentistResponse.id,
                    name = dentistResponse.name,
                    lastname = dentistResponse.lastname,
                    specialty = dentistResponse.specialty,
                    email = dentistResponse.email,
                    service = service1,
                    experience = experience,
                    imageResId = R.drawable.dentist1_h
                )
                2 -> Dentist(
                    id = dentistResponse.id,
                    name = dentistResponse.name,
                    lastname = dentistResponse.lastname,
                    specialty = dentistResponse.specialty,
                    email = dentistResponse.email,
                    service = service2,
                    experience = experience,
                    imageResId = R.drawable.dentist2_h
                )
                3 -> Dentist(
                    id = dentistResponse.id,
                    name = dentistResponse.name,
                    lastname = dentistResponse.lastname,
                    specialty = dentistResponse.specialty,
                    email = dentistResponse.email,
                    service = service3,
                    experience = experience,
                    imageResId = R.drawable.dentist1_m
                )
                4 -> Dentist(
                    id = dentistResponse.id,
                    name = dentistResponse.name,
                    lastname = dentistResponse.lastname,
                    specialty = dentistResponse.specialty,
                    email = dentistResponse.email,
                    service = service4,
                    experience = experience,
                    imageResId = R.drawable.dentist2_m
                )
                else -> Dentist(
                    id = dentistResponse.id,
                    name = dentistResponse.name,
                    lastname = dentistResponse.lastname,
                    specialty = dentistResponse.specialty,
                    email = dentistResponse.email,
                    service = emptyList(),
                    experience = emptyList(),
                    imageResId = 0
                )
            }
        }
    }

    suspend fun getDentistId(context: Context, id: Int): Dentist? {
        val dentists = getDentistRepository(context)
        return dentists.find { it.id == id }
    }

}
