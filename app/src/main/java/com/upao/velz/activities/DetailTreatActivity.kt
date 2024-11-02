package com.upao.velz.activities

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.view.View
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.MediaController
import android.widget.TextView
import android.widget.Toast
import android.widget.VideoView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.upao.velz.R
import com.upao.velz.controllers.TreatmentController
import com.upao.velz.databinding.ActivityDetailTreatBinding
import com.upao.velz.models.Treatment

class DetailTreatActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailTreatBinding
    private var treatmentName: String? = null
    private val treatmentController = TreatmentController(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailTreatBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.scrollView.post {
            binding.scrollView.scrollTo(0, 0)
        }

        treatmentName = intent.getStringExtra("treatment_name")
        treatmentController.getTreatmentByName(treatmentName.toString()) { treatment ->
            if (treatment != null) {
                setupUI(treatment)
            } else {
                Toast.makeText(this, "Error al obtener el tratamiento", Toast.LENGTH_SHORT).show()
                finish()
            }
        }

        val back = findViewById<ImageButton>(R.id.btnBack)
        back.setOnClickListener {
            val intent = Intent(this,TreatmentActivity::class.java)
            startActivity(intent)
            finish()
        }

    }

    private fun setupUI(treatment: Treatment) {
        binding.tvTreatmentTitle.text = treatment.name
        binding.tvTreatmentDescription.text = treatment.description

        val imageView: ImageView = findViewById(R.id.iv_treatment_image)
        imageView.setImageResource(treatment.imageResId)

        val stepsContainer = binding.stepsContainer
        stepsContainer.removeAllViews()

        treatment.procedure.forEach { procedure ->
            val spannableString = SpannableString(procedure)
            val pointIndex = procedure.indexOf('.')
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

            stepsContainer.addView(textView)
        }

        val benefitsGrid = binding.benefitsGrid
        treatment.benefits.forEachIndexed { index, benefit ->
            if (index < 4) {
                val benefitLayout = benefitsGrid.getChildAt(index) as LinearLayout
                val benefitDescription = benefitLayout.findViewById<TextView>(R.id.benefit1 + index)
                benefitDescription.text = benefit
            }
        }

        val videoView = findViewById<VideoView>(R.id.videoView)
        val videoPath = treatment.videoUri
        val videoUri = Uri.parse(videoPath)
        videoView.setVideoURI(videoUri)
        val mediaController = MediaController(this)
        videoView.setMediaController(mediaController)

        val playIcon: ImageView = findViewById(R.id.playIcon)
        playIcon.setOnClickListener {
            videoView.start()
            playIcon.visibility = View.GONE
        }

        videoView.setOnCompletionListener {
            playIcon.visibility = View.VISIBLE
        }
    }


}