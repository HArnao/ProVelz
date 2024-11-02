package com.upao.velz.activities

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.ImageButton
import androidx.appcompat.widget.SearchView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.upao.velz.MainActivity
import com.upao.velz.R
import com.upao.velz.adapters.TreatmentAdapter
import com.upao.velz.controllers.TreatmentController

class TreatmentActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: TreatmentAdapter
    private val controller = TreatmentController(this)

    // variable de busqueda
    private lateinit var searchView: SearchView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_treatment)

        recyclerView = findViewById(R.id.rv_treatments)
        recyclerView.layoutManager = LinearLayoutManager(this)

        controller.getTreatmentController { treatments ->
            Log.d("TreatmentActivity12345", "Tratamientos recuperados: $treatments")
            adapter = TreatmentAdapter(treatments)
            recyclerView.adapter = adapter
        }


        val back = findViewById<ImageButton>(R.id.btnBack)

        back.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }


        // Configurar el SearchView
        searchView = findViewById(R.id.search_view)
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                val filteredList = adapter.currentList.filter {
                    it.name.contains(newText.orEmpty(), ignoreCase = true) ||
                            it.description.contains(newText.orEmpty(), ignoreCase = true)
                }
                adapter.updateList(filteredList)
                return true
            }
        })

    }



}