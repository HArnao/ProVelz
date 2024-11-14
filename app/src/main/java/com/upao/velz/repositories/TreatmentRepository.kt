package com.upao.velz.repositories

import android.content.Context
import android.util.Log
import com.upao.velz.R
import com.upao.velz.apiLaravel.ApiService
import com.upao.velz.apiLaravel.Apiclient
import com.upao.velz.models.Treatment
import com.upao.velz.models.responseModel.TreatmentResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.UUID

class TreatmentRepository (context: Context) {

    suspend fun getTreatments(): List<TreatmentResponse> {
        val apiService = Apiclient.createService(ApiService::class.java)
        val response = apiService.getTreatments()
        return withContext(Dispatchers.Main) {
            if (response.isSuccessful) {
                response.body()!!
            } else {
                    emptyList()
            }
        }
    }


    suspend fun getTreatmentsRepository(context: Context): List<Treatment> {
        Log.d("TreatmentActivity", "REPOSITORYU")
        val treatmentResponses = getTreatments()

        val procedure1 = listOf(
            "1. Evaluación del estado inicial de los dientes y encías.",
            "2. Protección de las encías y tejidos blandos con un gel especial.",
            "3. Aplicación del agente blanqueador en la superficie de los dientes.",
            "4. Activación del agente con luz LED durante varios ciclos.",
            "5. Retiro del gel blanqueador y enjuague final.",
            "6. Aplicación de un gel calmante para reducir sensibilidad post tratamiento."
        )
        val benefits1 = listOf(
            "Sonrisa más brillante.",
            "Mejora estética inmediata.",
            "Procedimiento indoloro.",
            "Resultados visibles en pocas sesiones."
        )
        val procedure2 = listOf(
            "1. Examen dental para detectar acumulación de sarro y placa.",
            "2. Uso de ultrasonido o herramientas manuales para remover el sarro.",
            "3. Limpieza cuidadosa bajo las encías para evitar infecciones.",
            "4. Pulido de los dientes para eliminar manchas superficiales.",
            "5. Aplicación de flúor para fortalecer el esmalte dental."
        )
        val benefits2 = listOf(
            "Prevención de caries.",
            "Mejora la salud de las encías.",
            "Aliento fresco y duradero.",
            "Dientes más limpios y brillantes."
        )
        val procedure3 = listOf(
            "1. Estudio ortodóntico con radiografías y moldes dentales.",
            "2. Colocación de los brackets en la superficie de los dientes.",
            "3. Inserción de arcos de alambre para mover los dientes.",
            "4. Ajustes periódicos para guiar los dientes a la posición correcta.",
            "5. Uso de elásticos intermaxilares para corregir la mordida.",
            "6. Retiro de brackets y colocación de retenedores."
        )
        val benefits3 = listOf(
            "Mejora estética de la sonrisa.",
            "Corrección de problemas de mordida.",
            "Mejora en la funcionalidad de la masticación.",
            "Reducción del riesgo de caries y enfermedades gingivales."
        )

        val procedure4 = listOf(
            "1. Evaluación y planificación con radiografías y tomografías.",
            "2. Colocación del implante de titanio en el hueso maxilar.",
            "3. Periodo de espera para que el implante se fusione con el hueso (osteointegración).",
            "4. Colocación de un pilar sobre el implante.",
            "5. Fijación de la corona dental personalizada sobre el pilar.",
            "6. Revisión final y seguimiento para garantizar el éxito del implante."
        )
        val benefits4 = listOf(
            "Restablecimiento de la funcionalidad dental.",
            "Mejora estética significativa.",
            "Implante durable y de larga vida útil.",
            "Mejora de la masticación y el habla."
        )

        val videoPath1 = "android.resource://com.upao.velz/${R.raw.blanqueamiento_dental_tiktok}"
        val videoPath2 = "android.resource://com.upao.velz/${R.raw.limpieza_dental_tiktok}"
        val videoPath3 = "android.resource://com.upao.velz/${R.raw.ortodoncia_tiktok}"
        val videoPath4 = "android.resource://com.upao.velz/${R.raw.implante_dental_tiktok}"

        return treatmentResponses.map { treatmentResponse ->
            when (treatmentResponse.id) {
                1 -> Treatment(
                    id = treatmentResponse.id,
                    name = treatmentResponse.name,
                    price = treatmentResponse.price,
                    description = treatmentResponse.description,
                    procedure = procedure1,
                    benefits = benefits1,
                    imageResId = R.drawable.treatment_1,
                    videoUri = videoPath1
                )
                2 -> Treatment(
                    id = treatmentResponse.id,
                    name = treatmentResponse.name,
                    price = treatmentResponse.price,
                    description = treatmentResponse.description,
                    procedure = procedure2,
                    benefits = benefits2,
                    imageResId = R.drawable.treatment_2,
                    videoUri = videoPath2
                )
                3 -> Treatment(
                    id = treatmentResponse.id,
                    name = treatmentResponse.name,
                    price = treatmentResponse.price,
                    description = treatmentResponse.description,
                    procedure = procedure3,
                    benefits = benefits3,
                    imageResId = R.drawable.treatment_3,
                    videoUri = videoPath3
                )
                4 -> Treatment(
                    id = treatmentResponse.id,
                    name = treatmentResponse.name,
                    price = treatmentResponse.price,
                    description = treatmentResponse.description,
                    procedure = procedure4,
                    benefits = benefits4,
                    imageResId = R.drawable.treatment_4,
                    videoUri = videoPath4
                )
                else -> Treatment(
                    id = treatmentResponse.id,
                    name = treatmentResponse.name,
                    price = treatmentResponse.price,
                    description = treatmentResponse.description,
                    procedure = emptyList(),
                    benefits = emptyList(),
                    imageResId = 0,
                    videoUri = ""
                )
            }
        }
    }

    suspend fun getTreatmentByName(context: Context, name: String): Treatment? {
        val treatments = getTreatmentsRepository(context)
        return treatments.find { it.name.equals(name, ignoreCase = true) }
    }


}