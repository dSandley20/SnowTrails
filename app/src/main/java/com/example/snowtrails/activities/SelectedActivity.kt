package com.example.snowtrails.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.snowtrails.databinding.ActivitySelectedBinding


class SelectedActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySelectedBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySelectedBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val locationID = intent.getStringExtra("LocationID")
        //get all location data from db wit locationID

    }
}