package com.upao.velz.activities

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.ImageButton
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.upao.velz.MainActivity
import com.upao.velz.R
import com.upao.velz.adapters.AppointmentAdapter
import com.upao.velz.adapters.PaymentAdapter
import com.upao.velz.controllers.PaymentController
import com.upao.velz.controllers.UserController

class HistoryPayActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    var paymentController = PaymentController(this)
    val firebaseUser: FirebaseUser? = FirebaseAuth.getInstance().currentUser
    val userController = UserController(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_history_pay)

        recyclerView = findViewById(R.id.rv_payments)
        recyclerView.layoutManager = LinearLayoutManager(this)

        val back = findViewById<ImageButton>(R.id.btnBack)
        back.setOnClickListener {
            finish()
        }

        loadPayments()
    }

    private fun loadPayments() {

        if (firebaseUser == null) {
            Toast.makeText(this, "Usuario no logueado.", Toast.LENGTH_SHORT).show()
            return
        }
        val userEmail = firebaseUser.email ?: "Usuario sin email"

        userController.getUserByEmail(userEmail) { user ->
            if (user != null) {
                val userId = user.id
                paymentController.getListPayments(userId) { payments ->
                    if (payments != null) {
                        val adapter = PaymentAdapter(payments, user.name + " " + user.lastname)
                        recyclerView.adapter = adapter
                    } else {
                        Toast.makeText(this, "No se encontró un historial de Pagos.", Toast.LENGTH_SHORT).show()
                    }
                }
            } else {
                Toast.makeText(this, "No se encontró el usuario.", Toast.LENGTH_SHORT).show()
            }
        }

    }
}