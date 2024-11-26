package com.upao.velz.activities

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.view.MotionEvent
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.upao.velz.MainActivity
import com.upao.velz.R
import com.upao.velz.controllers.UserController
import com.upao.velz.databinding.ActivityRegisterBinding
import com.upao.velz.firebase.initializeSecondFirebaseApp
import com.upao.velz.models.User
import java.util.regex.Pattern

class RegisterActivity : AppCompatActivity() {

    private val userController = UserController(this)
    private lateinit var binding: ActivityRegisterBinding
    private var isPasswordVisible = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val registerButton: Button = findViewById(R.id.btnRegistrar)
        val termsCheckbox: CheckBox = findViewById(R.id.checkBoxTerminos)

        registerButton.setOnClickListener {


            if (!termsCheckbox.isChecked) {
                Toast.makeText(this, "Debes aceptar los Términos de servicio y la Política de privacidad", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val nombre = findViewById<EditText>(R.id.inputNameText).text.toString()
            val apellido = findViewById<EditText>(R.id.inputLastNameText).text.toString()
            val email = findViewById<EditText>(R.id.inputEmailText).text.toString()
            val password = findViewById<EditText>(R.id.inputPasswordText).text.toString()
            val confirmPassword = findViewById<EditText>(R.id.inputConfirmPasswordText).text.toString()
            val phone = findViewById<EditText>(R.id.inputPhoneText).text.toString()
            val dni = findViewById<EditText>(R.id.inputDniText).text.toString()

            val user = User(0,nombre, apellido, email, phone, dni, password)

            if (!isEmailValid(email)) {
                binding.inputEmailText.error = "Correo electrónico no válido"
                Toast.makeText(this, "Correo electrónico no válido", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (!isPasswordValid(password)) {
                binding.inputPasswordText.error = "La contraseña debe tener al menos 8 caracteres, una mayúscula, una minúscula y un carácter especial"
                Toast.makeText(this, "La contraseña debe tener al menos 8 caracteres, una mayúscula, una minúscula y un carácter especial", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (password != confirmPassword) {
                binding.inputPasswordText.error = "Las contraseñas no coinciden"
                binding.inputConfirmPasswordText.error = "Las contraseñas no coinciden"
                Toast.makeText(this, "Las contraseñas no coinciden", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (!isPhoneValid(phone)) {
                binding.inputPhoneText.error = "Número de teléfono no válido."
                Toast.makeText(this, "Número de teléfono no válido. Debe tener 9 dígityos y comenzar con un 9.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (!isDniValid(dni)) {
                binding.inputDniText.error = "Número de Dni no válido."
                Toast.makeText(this, "Número de Dni no válido. Debe tener 8 dígitos.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            userController.registerUser(user)
            Toast.makeText(this, "Registro exitoso", Toast.LENGTH_SHORT).show()

            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)

        }

        val login = findViewById<TextView>(R.id.textLogin)

        login.setOnClickListener{
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }

        val passwordEditText = findViewById<EditText>(R.id.inputPasswordText)
        val confirmPasswordEditText = findViewById<EditText>(R.id.inputConfirmPasswordText)
        setupPasswordToggle(passwordEditText)
        setupPasswordToggle(confirmPasswordEditText)

    }

    private fun isEmailValid(email: String): Boolean {
        val emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+"
        return Pattern.matches(emailPattern, email)
    }

    private fun isPasswordValid(password: String): Boolean {
        val passwordPattern = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@\$!%*?&])[A-Za-z\\d@\$!%*?&]{8,}\$"
        return Pattern.matches(passwordPattern, password)
    }

    private fun isPhoneValid(phone: String): Boolean {
        val phonePattern = "^9\\d{8}\$"
        return Pattern.matches(phonePattern, phone) && phone.length == 9
    }

    private fun isDniValid(dni: String): Boolean {
        val dniPattern = "^[0-9]{8}\$"
        return Pattern.matches(dniPattern, dni) && dni.length == 8
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