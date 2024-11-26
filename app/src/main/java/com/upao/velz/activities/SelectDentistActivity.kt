package com.upao.velz.activities

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import com.upao.velz.R

class SelectDentistActivity : AppCompatActivity() {

    private lateinit var cardDoctorInfo1: CardView
    private lateinit var cardDoctorInfo2: CardView
    private lateinit var cardDoctorInfo3: CardView
    private lateinit var cardDoctorInfo4: CardView
    private var treatmentName: String? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_select_dentist)

        cardDoctorInfo1 = findViewById(R.id.cardDoctorInfo1)
        cardDoctorInfo2 = findViewById(R.id.cardDoctorInfo2)
        cardDoctorInfo3 = findViewById(R.id.cardDoctorInfo3)
        cardDoctorInfo4 = findViewById(R.id.cardDoctorInfo4)

        treatmentName = intent.getStringExtra("treatment_name")

        cardDoctorInfo1.setOnClickListener {
            navigateToMain(1, treatmentName)
        }

        cardDoctorInfo2.setOnClickListener {
            navigateToMain(2, treatmentName)
        }

        cardDoctorInfo3.setOnClickListener {
            navigateToMain(3, treatmentName)
        }

        cardDoctorInfo4.setOnClickListener {
            navigateToMain(4, treatmentName)
        }
    }

    private fun navigateToMain(selectedValue: Int, treatmentName: String?) {
        val intent = Intent(this, AppointmentActivity::class.java)
        intent.putExtra("dentist_id", selectedValue)
        intent.putExtra("treatment_name", treatmentName)
        startActivity(intent)
        finish()
    }
}