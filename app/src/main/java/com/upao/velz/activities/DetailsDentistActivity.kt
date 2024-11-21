package com.upao.velz.activities

import android.content.Intent
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.widget.ImageButton
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.upao.velz.R
import com.upao.velz.controllers.DentistController
import com.upao.velz.databinding.ActivityDetailsDentistBinding
import com.upao.velz.models.Dentist

class DetailsDentistActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailsDentistBinding
    private var dentistId: Int = 0
    private val dentistController = DentistController(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityDetailsDentistBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.scrollView.post {
            binding.scrollView.scrollTo(0, 0)
        }

        dentistId = intent.getIntExtra("dentist_id", 0)
        dentistController.getDentistId(dentistId){dentist ->
            if(dentist != null){
                setupUI(dentist)
            }else {
                Toast.makeText(this, "Error al obtener la información del dentista", Toast.LENGTH_SHORT).show()
                finish()
            }
        }

        val back = findViewById<ImageButton>(R.id.btnBack)
        back.setOnClickListener {
            val intent = Intent(this,DentistActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    private fun setupUI(dentist: Dentist){
        binding.ivDentistImageDetail.setImageResource(dentist.imageResId)
        binding.tvDentistDetailTitle.text = dentist.name + " " + dentist.lastname
        binding.tvDetailSpeciality.text = dentist.specialty
        binding.tvEmailDentist.text = "Contacto: ${dentist.email}"

        val servicesContainer = binding.servicesContainer
        servicesContainer.removeAllViews()

        dentist.service.forEach{ service ->
            val spannableString = SpannableString(service)
            val pointIndex = service.indexOf('.')
            val numberColor = ContextCompat.getColor(this, R.color.green)

            if (pointIndex != -1) {
                spannableString.setSpan(
                    ForegroundColorSpan(numberColor),
                    0,
                    pointIndex + 1,
                    Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
                )
            }

            val textView = TextView(this).apply {
                textSize = 14f
                val layoutParams = LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
                ).apply {
                    topMargin = 8
                    marginStart = 16
                    marginEnd = 16
                    bottomMargin = 8
                }
                this.layoutParams = layoutParams
                this.text = spannableString
            }

            servicesContainer.addView(textView)
        }

        val experienceContainer = binding.experienceContainer
        experienceContainer.removeAllViews()

        dentist.experience.forEach{ experience ->
            val spannableString = SpannableString(experience)
            val pointIndex = experience.indexOf('.')
            val numberColor = ContextCompat.getColor(this, R.color.green)

            if (pointIndex != -1) {
                spannableString.setSpan(
                    ForegroundColorSpan(numberColor),
                    0,
                    pointIndex + 1,
                    Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
                )
            }

            val textView = TextView(this).apply {
                textSize = 14f
                val layoutParams = LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
                ).apply {
                    topMargin = 8
                    marginStart = 16
                    marginEnd = 16
                    bottomMargin = 8
                }
                this.layoutParams = layoutParams
                this.text = spannableString
            }

            experienceContainer.addView(textView)
        }
    }
}