package com.example.snowtrails.activities

import android.R
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.snowtrails.adapters.LocationAdapter
import com.example.snowtrails.databinding.ActivityLocationsBinding
import com.example.snowtrails.utils.getDatabase

class LocationActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLocationsBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLocationsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //get DB instance
        val db = getDatabase().returnDB(this@LocationActivity)

        val locationAdapter = LocationAdapter(this, db.getLocationDao().getAll())
        binding.locationsListView.adapter = locationAdapter

        binding.locationsListView.setOnItemClickListener{ parent, view, position, id ->
            //TODO redirect to site activity
            val intent = Intent(this, SelectedActivity::class.java)
            intent.putExtra("LocationID", id)
            startActivity(intent)
        }
    }
}