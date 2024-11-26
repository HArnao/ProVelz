package com.upao.velz.activities

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.activity.SystemBarStyle
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.upao.velz.MainActivity
import com.upao.velz.databinding.ActivityNiubizBinding

class NiubizActivity : AppCompatActivity() {

    private lateinit var binding: ActivityNiubizBinding

    @SuppressLint("SetJavaScriptEnabled")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge(
            statusBarStyle = SystemBarStyle.auto(Color.TRANSPARENT, Color.TRANSPARENT),
            navigationBarStyle = SystemBarStyle.light(Color.TRANSPARENT, Color.TRANSPARENT)
        )
        binding = ActivityNiubizBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val appointment_id = intent.getIntExtra("appointment_id", 0)

        binding.webview.settings.javaScriptEnabled = true
        binding.webview.settings.mixedContentMode = WebSettings.MIXED_CONTENT_ALWAYS_ALLOW

        binding.webview.loadUrl("http://192.168.0.14:8000/niubiz/$appointment_id") //LOCAL
        // binding.webview.loadUrl("http://3.141.15.88/niubiz/$appointment_id") //DEPLOY

        binding.webview.webViewClient = object : WebViewClient() {
            @RequiresApi(Build.VERSION_CODES.O)
            override fun onPageFinished(view: WebView?, url: String?) {
                super.onPageFinished(view, url)
               // if(url == "http://3.141.15.88/payment-success") { //DEPLOY
                if(url == "http://192.168.0.14:8000/payment-success") { //LOCAL
                    val intent = Intent(this@NiubizActivity, MainActivity::class.java)
                    intent.putExtra("payment_success_message", "Pago realizado con éxito")
                    startActivity(intent)
                    finish()
                }
            }
        }

    }
}