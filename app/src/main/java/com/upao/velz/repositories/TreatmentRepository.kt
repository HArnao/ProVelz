package com.upao.velz.repositories

import android.content.ContentValues
import android.content.Context
import com.google.gson.Gson
import com.upao.velz.R
import com.upao.velz.models.Treatment
import com.upao.velz.sqlite.DbHelper
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.UUID

class TreatmentRepository (context: Context) {

    private val dbHelper = DbHelper(context)

    /*fun addTreatment(treatment: Treatment): Boolean {
        val db = dbHelper.writableDatabase
        val gson = Gson()
        val procedureJson = gson.toJson(treatment.procedure)
        val benefitsJson = gson.toJson(treatment.benefits)
        val values = ContentValues().apply {
            put("nameTreatment", treatment.name)
            put("descriptionTreatment", treatment.description)
            put("procedureTreatment", procedureJson)
            put("benefitsTreatment", benefitsJson)
            put("imgTreatment", treatment.imageResId)
            put("videoTreatment", treatment.videoUri)
            put("createdAT", getCurrentDate())
            put("updatedAT", getCurrentDate())
        }

        val newRowId = db.insert("treatments", null, values)
        db.close()

        return newRowId != -1L
    }

    private fun getCurrentDate(): String {
        val dateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
        return dateFormat.format(Date())
    }*/


    fun getTreatmentsRepository(): List<Treatment> {
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

        return listOf(
            Treatment(UUID.randomUUID(),"Blanqueamiento dental","Procedimiento estético que busca aclarar el color de los dientes, eliminando manchas y decoloraciones. Utiliza agentes blanqueadores, como el peróxido de hidrógeno,", procedure1, benefits1, R.drawable.treatment_1, videoPath1),
            Treatment(UUID.randomUUID(),"Limpieza Dental Profunda","Procedimiento de higiene dental que elimina el sarro y la placa acumulada debajo de las encías y en la superficie de los dientes", procedure2, benefits2,R.drawable.treatment_2, videoPath2),
            Treatment(UUID.randomUUID() ,"Ortodoncia (Brackets)","Tratamiento que corrige la alineación de los dientes y la mordida mediante el uso de brackets.", procedure3, benefits3, R.drawable.treatment_3, videoPath3),
            Treatment(UUID.randomUUID(),"Implantes Dentales","Procedimiento para reemplazar dientes perdidos mediante la colocación de un implante de titanio", procedure4, benefits4,R.drawable.treatment_4, videoPath4),
        )
    }

    fun getTreatmentByName(name: String): Treatment? {
        val treatments = getTreatmentsRepository()
        return treatments.find { it.name.equals(name, ignoreCase = true) }
    }

    /*fun insertDefaultTreatments() {
        val db = dbHelper.readableDatabase
        val defaultTreatments = getTreatmentsRepository()

        for (treatment in defaultTreatments) {
            val cursor = db.rawQuery("SELECT COUNT(*) FROM treatments WHERE nameTreatment = ?", arrayOf(treatment.name))
            cursor.moveToFirst()
            val count = cursor.getInt(0)
            cursor.close()
            if (count == 0) {
                addTreatment(treatment)
            }
        }
    }*/

}