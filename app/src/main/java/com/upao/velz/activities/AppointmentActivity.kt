package com.upao.velz.activities

import android.app.DatePickerDialog
import android.content.Intent
import android.graphics.Typeface
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.HorizontalScrollView
import android.widget.ImageButton
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.children
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.upao.velz.MainActivity
import com.upao.velz.R
import com.upao.velz.controllers.AppointmentController
import com.upao.velz.controllers.TreatmentController
import com.upao.velz.controllers.UserController
import com.upao.velz.databinding.ActivityAppointmentBinding
import com.upao.velz.models.Appointment
import com.upao.velz.models.Treatment
import com.upao.velz.models.User
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class AppointmentActivity : AppCompatActivity() {

    private val appointmentController = AppointmentController(this)
    private lateinit var binding: ActivityAppointmentBinding
    private lateinit var datePickerDialog: DatePickerDialog
    private lateinit var llRecuerdame: LinearLayout
    private lateinit var llTiempoDisponible: LinearLayout
    private var selectedDate: Calendar? = null
    private var selectedDateCalendar: String = ""
    private var selectedTime: String = " "
    private var selectedReminderTime: Int? = null
    private var treatmentName: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAppointmentBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val btnCalendario: Button = findViewById(R.id.btnCalendario)
        btnCalendario.setOnClickListener {
            showDatePickerDialog()
        }

        llRecuerdame = findViewById(R.id.llRecuerdame)
        llTiempoDisponible = findViewById(R.id.llTiempoDisponible)

        val reminderTimes = listOf(10, 20, 30, 40, 50, 60, 90)
        createReminderButtons(reminderTimes)

        val listTimes = listOf("09:00", "10:00", "11:00", "12:00", "01:00", "02:00", "03:00", "04:00", "05:00", "06:00")
        createTimesButtons(listTimes)


        val btnBack: ImageButton = findViewById(R.id.btnBack)
        btnBack.setOnClickListener {
            val intent = Intent(this,TreatmentActivity::class.java)
            startActivity(intent)
            finish()
        }

        val btnConfirmar: Button = findViewById(R.id.btnConfirmar)
        btnConfirmar.setOnClickListener {

            val reminderTime: Int = selectedReminderTime ?: 30

            if (selectedDate == null) {
                Toast.makeText(this, "Por favor selecciona una fecha.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (selectedTime.isBlank()) {
                Toast.makeText(this, "Por favor selecciona una hora.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (selectedReminderTime == null) {
                Toast.makeText(this, "Por favor selecciona un tiempo de recordatorio.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val firebaseUser: FirebaseUser? = FirebaseAuth.getInstance().currentUser

            if (firebaseUser == null) {
                Toast.makeText(this, "Usuario no logueado.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val userEmail = firebaseUser.email ?: "Usuario sin email"
            val userController = UserController(this)
            val user = userController.getUserByEmail(userEmail) ?: User(0, "ABC", "ABC", "ABC", "ABC", "ABC", "ABC")

            treatmentName = intent.getStringExtra("treatment_name")
            val treatmentController = TreatmentController(this)
            val treatment : Treatment = treatmentController.getTreatmentByNameController(treatmentName.toString())!!
            val appointment = Appointment(
                0,
                selectedDateCalendar,
                selectedTime,
                treatment,
                user,
                "Pendiente",
                reminderTime
            )

            if (appointmentController.isAppointmentScheduled(selectedDateCalendar, selectedTime)) {
                Toast.makeText(this, "Ya hay una cita agendada ahí", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            appointmentController.addAppointment(appointment)

            Toast.makeText(this, "Cita Registrada con Éxito", Toast.LENGTH_SHORT).show()

            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)

        }
    }

    private fun createTimesButtons(times: List<String>) {
        for (time in times) {
            val hourString = time.toString()
            val hour = hourString.split(":")[0].toInt()
            val period = if (8 < hour && hour < 12) "AM" else "PM"

            val currentTime = Calendar.getInstance()
            val currentHour = currentTime.get(Calendar.HOUR_OF_DAY)

            // Ajustar la fecha seleccionada si existe
            val adjustedHour = if (period == "PM" && hour != 12) {
                hour + 12 // Convertir a formato 24 horas
            } else if (period == "AM" && hour == 12) {
                0 // 12 AM es 0 horas
            } else {
                hour
            }



            val button = Button(this).apply {
                // Configuración de los LayoutParams
                layoutParams = LinearLayout.LayoutParams(70.dp, 70.dp).apply {
                    setMargins(8.dp, 8.dp, 8.dp, 8.dp)
                }
                background = resources.getDrawable(R.drawable.rounded_button, null)
                textSize = 16f
                text = "$hourString\n$period"
                setTextColor(resources.getColor(R.color.green, null))
                setTypeface(null, Typeface.BOLD)


                setOnClickListener {
                    if (selectedDate != null) {
                        val now = Calendar.getInstance()
                        val currentYear = now.get(Calendar.YEAR)
                        val currentMonth = now.get(Calendar.MONTH)
                        val currentDay = now.get(Calendar.DAY_OF_MONTH)

                        if (selectedDate!!.get(Calendar.YEAR) == currentYear &&
                            selectedDate!!.get(Calendar.MONTH) == currentMonth &&
                            selectedDate!!.get(Calendar.DAY_OF_MONTH) == currentDay) {
                            if (adjustedHour <= currentHour) {
                                Toast.makeText(context, "La hora ya pasó", Toast.LENGTH_SHORT).show()
                            } else {
                                llTiempoDisponible.children.forEach { child ->
                                    if (child is Button) {
                                        child.isSelected = false
                                        child.setTextColor(resources.getColor(R.color.green, null))
                                    }
                                }

                                isSelected = true
                                setTextColor(resources.getColor(R.color.white, null))
                            }
                        } else {
                            // La fecha seleccionada es de mañana o futura, así que se permite
                            llTiempoDisponible.children.forEach { child ->
                                if (child is Button) {
                                    child.isSelected = false
                                    child.setTextColor(resources.getColor(R.color.green, null))
                                }
                            }

                            isSelected = true
                            setTextColor(resources.getColor(R.color.white, null))
                        }
                    } else {
                        // Si no hay una fecha seleccionada, se puede proceder normalmente
                        llTiempoDisponible.children.forEach { child ->
                            if (child is Button) {
                                child.isSelected = false
                                child.setTextColor(resources.getColor(R.color.green, null))
                            }
                        }

                        isSelected = true
                        setTextColor(resources.getColor(R.color.white, null))
                    }

                    selectedTime = "$hourString $period"
                }
            }

            llTiempoDisponible.addView(button)
        }
    }


    private fun createReminderButtons(times: List<Int>) {
        for (time in times) {
            val button = Button(this).apply {
                // Configuración de los LayoutParams
                layoutParams = LinearLayout.LayoutParams(70.dp, 70.dp).apply {
                    setMargins(8.dp, 8.dp, 8.dp, 8.dp)
                }
                background = resources.getDrawable(R.drawable.rounded_button, null)
                textSize = 16f
                text = "$time\nMin"
                setTextColor(resources.getColor(R.color.green, null))
                setTypeface(null, Typeface.BOLD)
                isEnabled = true
                isAllCaps = false

                setOnClickListener {
                    llRecuerdame.children.forEach { child ->
                        if (child is Button) {
                            child.isSelected = false
                            child.setTextColor(resources.getColor(R.color.green, null))
                        }
                    }

                    isSelected = true
                    setTextColor(resources.getColor(R.color.white, null))
                    selectedReminderTime = time
                }
            }

            llRecuerdame.addView(button)
        }
    }

    val Int.dp: Int get() = (this * resources.displayMetrics.density).toInt()

    private fun showDatePickerDialog() {

        val tvFechaSeleccionada: TextView = findViewById(R.id.tvFechaSeleccionada)
        val tvRecuerdame: TextView = findViewById(R.id.tvRecuerdame)
        val tvTiempoDisponible: TextView = findViewById(R.id.tvTiempoDisponible)
        val horizontalScrollView: HorizontalScrollView = findViewById(R.id.horizontalScrollView)
        val horizontalScrollViewRecuerdame: HorizontalScrollView = findViewById(R.id.horizontalScrollViewRecuerdame)
        val btnConfirmar: Button = findViewById(R.id.btnConfirmar)

        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        // Crea el DatePickerDialog
        datePickerDialog = DatePickerDialog(
            this,  R.style.CustomDatePickerDialog,
            { _, selectedYear, selectedMonth, selectedDay ->
                selectedDate = Calendar.getInstance().apply {
                    set(Calendar.YEAR, selectedYear)
                    set(Calendar.MONTH, selectedMonth)
                    set(Calendar.DAY_OF_MONTH, selectedDay)
                }
                val selectedDateString = "$selectedDay/${selectedMonth + 1}/$selectedYear"
                tvFechaSeleccionada.text = selectedDateString

                selectedDateCalendar = selectedDateString

                resetSelections()

                tvTiempoDisponible.visibility = View.VISIBLE
                tvRecuerdame.visibility = View.VISIBLE
                horizontalScrollView.visibility = View.VISIBLE
                horizontalScrollViewRecuerdame.visibility = View.VISIBLE
                btnConfirmar.visibility = View.VISIBLE
            },
            year,
            month,
            day
        )

        datePickerDialog.datePicker.minDate = calendar.timeInMillis
        val nextYear = Calendar.getInstance()
        nextYear.add(Calendar.YEAR, 1)
        datePickerDialog.datePicker.maxDate = nextYear.timeInMillis
        // Muestra el DatePicker
        datePickerDialog.show()
    }

    private fun resetSelections() {
        llTiempoDisponible.children.forEach { child ->
            if (child is Button) {
                child.isSelected = false
                child.setTextColor(resources.getColor(R.color.green, null))
            }
        }

        llRecuerdame.children.forEach { child ->
            if (child is Button) {
                child.isSelected = false
                child.setTextColor(resources.getColor(R.color.green, null))
            }
        }
    }
}