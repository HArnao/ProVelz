package com.upao.velz.activities

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.upao.velz.R

class WelcomeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_welcome)

        val nextButton: Button = findViewById(R.id.btnNext)
        val skipButton: Button = findViewById(R.id.btnSkip)

        nextButton.setOnClickListener {
            val intent = Intent(this, WelcomeTwoActivity::class.java)
            startActivity(intent)
            finish()
        }
        skipButton.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}