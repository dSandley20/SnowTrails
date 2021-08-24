package com.example.snowtrails

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.snowtrails.activities.LoginActivity
import com.example.snowtrails.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //action to switch to login/register screen
        binding.loginButton.setOnClickListener{
            val intent = Intent(this, LoginActivity::class.java);
            startActivity(intent)
        }
    }
}