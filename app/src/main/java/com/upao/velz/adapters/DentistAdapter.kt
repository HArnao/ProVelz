package com.upao.velz.adapters

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.upao.velz.R
import com.upao.velz.activities.DetailsDentistActivity
import com.upao.velz.models.Dentist


class DentistAdapter(private var dentists: List<Dentist>) : RecyclerView.Adapter<DentistAdapter.ViewHolder>() {

    var currentList: List<Dentist> = dentists

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private var imageDentist: ImageView = view.findViewById(R.id.ivProfilePictureDentist)
        private var nameDentist: TextView = view.findViewById(R.id.tv_dentist_name)
        private var specialtyDentist: TextView = view.findViewById(R.id.tv_dentist_specialty)
        private var cardDoctorInfo: CardView = view.findViewById(R.id.cardDoctorInfo)

        fun bind(dentist: Dentist){
            nameDentist.text = dentist.name + " " + dentist.lastname
            specialtyDentist.text = dentist.specialty
            imageDentist.setImageResource(dentist.imageResId)

            cardDoctorInfo.setOnClickListener {
                val intent = Intent(itemView.context, DetailsDentistActivity::class.java)
                intent.putExtra("dentist_id", dentist.id)
                itemView.context.startActivity(intent)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.activity_item_dentist, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(dentists[position])
    }

    override fun getItemCount(): Int {
        return dentists.size
    }

    fun updateList(newDentist: List<Dentist>) {
        dentists = newDentist
        currentList = newDentist
        notifyDataSetChanged()
    }
}


