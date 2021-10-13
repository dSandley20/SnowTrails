package com.example.snowtrails.activities

import android.content.Intent
import android.location.Location
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.snowtrails.databinding.ActivitySelectedBinding
import com.example.snowtrails.services.AuthService
import com.example.snowtrails.services.LocationService
import kotlinx.coroutines.CoroutineStart
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.util.concurrent.LinkedBlockingQueue
import java.util.concurrent.ThreadPoolExecutor
import java.util.concurrent.TimeUnit


class SelectedActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySelectedBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySelectedBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val locationID : com.example.snowtrails.room.entities.Location? = intent.getParcelableExtra("Location")
            //intent.getIntExtra("Location", 0)
        println("locationID: " +locationID)


    }
}

