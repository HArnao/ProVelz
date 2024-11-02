package com.upao.velz.activities

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.util.Log
import android.view.MotionEvent
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.upao.velz.MainActivity
import com.upao.velz.R
import com.upao.velz.controllers.UserController
import com.upao.velz.databinding.ActivityLoginBinding
import java.util.regex.Pattern


class LoginActivity : AppCompatActivity() {

    private val userController = UserController(this)

    // declaracion del binding
    private lateinit var binding: ActivityLoginBinding
    private var isPasswordVisible = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

         binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(R.layout.activity_login)

        val buttonLogin : Button = findViewById(R.id.btnLogin)

        buttonLogin.setOnClickListener{

            var password: String = findViewById<EditText>(R.id.passwordLogin).text.toString()
            var email:String = findViewById<EditText>(R.id.emailLogin).text.toString()

            if (!isEmailValid(email)) {
                binding.emailLogin.error = "Correo electrónico no válido"
                Toast.makeText(this, "Correo electrónico no válido", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (!isPasswordValid(password)) {
                binding.passwordLogin.error = "La contraseña es incorrecta"
                Toast.makeText(this, "La contraseña es incorrecta", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            userController.loginUser(email, password) { success, error ->
                if (success) {
                    val intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)
                    finish()
                } else {
                    Toast.makeText(this, error ?: "Error al iniciar sesión", Toast.LENGTH_SHORT).show()
                }
            }

        }

        // se extrae la variable de la vista
        val register = findViewById<TextView>(R.id.textRegister)

        register.setOnClickListener{
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }

        val PasswordEditText = findViewById<EditText>(R.id.passwordLogin)
        setupPasswordToggle(PasswordEditText)

    }

    private fun isEmailValid(email: String): Boolean {
        val emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+"
        return Pattern.matches(emailPattern, email)
    }

    private fun isPasswordValid(password: String): Boolean {
        val passwordPattern = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@\$!%*?&])[A-Za-z\\d@\$!%*?&]{8,}\$"
        return Pattern.matches(passwordPattern, password)
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun setupPasswordToggle(editText: EditText){
        val drawableEnd = ContextCompat.getDrawable(this, R.drawable.baseline_visibility)
        val drawableEndOff = ContextCompat.getDrawable(this, R.drawable.baseline_visibility_off)

        editText.setOnTouchListener { v, event ->
            val DRAWABLE_END = 2

            if (event.action == MotionEvent.ACTION_UP) {
                if (event.rawX >= (editText.right - editText.compoundDrawables[DRAWABLE_END].bounds.width())) {
                    if (isPasswordVisible) {
                        editText.transformationMethod = PasswordTransformationMethod.getInstance()
                        editText.setCompoundDrawablesWithIntrinsicBounds(null, null, drawableEndOff, null)
                    } else {

                        editText.transformationMethod = HideReturnsTransformationMethod.getInstance()
                        editText.setCompoundDrawablesWithIntrinsicBounds(null, null, drawableEnd, null)
                    }

                    isPasswordVisible = !isPasswordVisible

                    editText.setSelection(editText.text.length)

                    return@setOnTouchListener true
                }
            }
            false
        }
    }





}