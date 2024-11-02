package com.upao.velz.adapters

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.upao.velz.R
import com.upao.velz.activities.AppointmentActivity
import com.upao.velz.activities.DetailTreatActivity
import com.upao.velz.models.Treatment

class TreatmentAdapter(private var treatments: List<Treatment>) : RecyclerView.Adapter<TreatmentAdapter.ViewHolder>()  {

    class ViewHolder(view: View): RecyclerView.ViewHolder(view){
        private var btnAppointment: Button = itemView.findViewById(R.id.btn_appointment)
        val nameTextView:TextView = view.findViewById(R.id.tv_treatment_title)
        val descriptionTextView: TextView = view.findViewById(R.id.tv_treatment_description)
        private val imageView: ImageView = view.findViewById(R.id.iv_treatment_image)
        private var btnDetail: Button = itemView.findViewById(R.id.btn_details)


        fun bing(treatment: Treatment){

            imageView.setImageResource(treatment.imageResId)

            btnAppointment.setOnClickListener {
                val intent = Intent(itemView.context, AppointmentActivity::class.java)
                // para extraer el id del intent
                intent.putExtra("treatment_id", treatment.id)
                intent.putExtra("treatment_name", treatment.name)
                itemView.context.startActivity(intent)
            }

            btnDetail.setOnClickListener {
                val intent = Intent(itemView.context, DetailTreatActivity::class.java)
                intent.putExtra("treatment_name", treatment.name)
                itemView.context.startActivity(intent)
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.activity_item_treatment, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return treatments.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.nameTextView.text = treatments[position].name
        holder.descriptionTextView.text = treatments[position].description

        holder.bing(treatments[position])
    }


    // actualiza la lista de tratamientos
    fun updateList(newTreatments: List<Treatment>) {
        treatments = newTreatments
        notifyDataSetChanged()
    }


}