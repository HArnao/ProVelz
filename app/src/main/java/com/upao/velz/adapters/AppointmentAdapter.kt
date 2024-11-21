package com.upao.velz.adapters

import android.content.Intent
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.upao.velz.R
import com.upao.velz.activities.EditAppointmentActivity
import com.upao.velz.models.responseModel.AppDetailResponse

class AppointmentAdapter(
    private val appointments: List<AppDetailResponse>
) : RecyclerView.Adapter<AppointmentAdapter.AppointmentViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AppointmentViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.activity_item_detail_appointment, parent, false)
        return AppointmentViewHolder(view)
    }

    override fun onBindViewHolder(holder: AppointmentViewHolder, position: Int) {
        val appointment = appointments[position]
        holder.bind(appointment)
    }

    override fun getItemCount(): Int = appointments.size

    inner class AppointmentViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val dayTextView: TextView = itemView.findViewById(R.id.tv_date_day)
        private val monthTextView: TextView = itemView.findViewById(R.id.tv_date_month)
        private val titleTextView: TextView = itemView.findViewById(R.id.tv_title)
        private val timeTextView: TextView = itemView.findViewById(R.id.tv_time)
        private val statusTextView: TextView = itemView.findViewById(R.id.tv_status)
        private val date_container_app: LinearLayout = itemView.findViewById(R.id.date_container_app)
        private val buttonContainer: LinearLayout = itemView.findViewById(R.id.button_edit_app)
        private val buttonEdit: Button = itemView.findViewById(R.id.btn_appointment)

        fun bind(appointment: AppDetailResponse) {
            val dateParts = appointment.dateAppointment.split("/")
            val day = dateParts[0] // Día
            val monthNumber = dateParts[1].toInt()

            val monthNames = listOf("ENE", "FEB", "MAR", "ABR", "MAY", "JUN", "JUL", "AGO", "SEP", "OCT", "NOV", "DIC")
            val monthAbbreviation = monthNames[monthNumber - 1]

            dayTextView.text = day
            monthTextView.text = monthAbbreviation
            titleTextView.text = appointment.treatment.name
            timeTextView.text = appointment.timeAppointment
            statusTextView.text = appointment.status

            buttonEdit.setOnClickListener {
                val intent = Intent(itemView.context, EditAppointmentActivity::class.java).apply {
                    putExtra("EXTRA_ID", appointment.id)
                    putExtra("EXTRA_USER_ID", appointment.userId)
                    putExtra("EXTRA_TREATMENT_ID", appointment.treatmentId)
                }
                itemView.context.startActivity(intent)
            }

            if (appointment.status == "Pendiente") {
                date_container_app.setBackgroundResource(R.drawable.fondo_verde)
                val textContainer = itemView.findViewById<LinearLayout>(R.id.text_container_new)
                textContainer.setBackgroundResource(R.drawable.fondo_casi_verde)
                val oldTextView = itemView.findViewById<TextView>(R.id.tv_old)
                oldTextView.text = "NEW"
                oldTextView.setTextColor(ContextCompat.getColor(itemView.context, R.color.green))
                buttonContainer.visibility = View.VISIBLE
            } else {
                date_container_app.setBackgroundResource(R.drawable.fondo_rojo)
                val textContainer = itemView.findViewById<LinearLayout>(R.id.text_container_new)
                textContainer.setBackgroundResource(R.drawable.fondo_casi_rojo)
                val oldTextView = itemView.findViewById<TextView>(R.id.tv_old)
                oldTextView.text = "OLD"
                oldTextView.setTextColor(Color.parseColor("#FF5252"))
                buttonContainer.visibility = View.GONE
            }

            if (appointment.status == "Pendiente") {
                timeTextView.setTextColor(ContextCompat.getColor(itemView.context, R.color.green))
            } else {
                timeTextView.setTextColor(Color.parseColor("#FF5252"))
            }
        }
    }
}
