package com.upao.velz.activities

import android.content.Intent
import android.os.Bundle
import android.widget.ImageButton
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.upao.velz.MainActivity
import com.upao.velz.R
import com.upao.velz.adapters.DentistAdapter
import com.upao.velz.controllers.DentistController

class DentistActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: DentistAdapter
    private val controller = DentistController(this)
    private lateinit var searchView: SearchView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dentist)

        recyclerView = findViewById(R.id.rv_dentist)
        recyclerView.layoutManager = GridLayoutManager(this, 2)

        controller.getDentistController { dentists ->
            adapter = DentistAdapter(dentists)
            recyclerView.adapter = adapter
        }

        val back = findViewById<ImageButton>(R.id.btnBack)
        back.setOnClickListener {
            finish()
        }

        searchView = findViewById(R.id.search_view_dentist)
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                val filteredList = adapter.currentList.filter {
                    it.name.contains(newText.orEmpty(), ignoreCase = true) ||
                            it.specialty.contains(newText.orEmpty(), ignoreCase = true)
                }
                adapter.updateList(filteredList)
                return true
            }
        })
    }
}