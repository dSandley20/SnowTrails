package com.example.snowtrails

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.snowtrails.activities.LoginActivity
import com.example.snowtrails.databinding.ActivityMainBinding
import com.example.snowtrails.utils.getDatabase

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //get db instance
        val db = getDatabase().returnDB(this)

        //check for authenticated user already logged in
//        if(db.getAuthUserDao().getAll().size > 0){
//            println("noticed a user is logged in")
//        }

        //action to switch to login/register screen
        binding.loginButton.setOnClickListener{
            val intent = Intent(this, LoginActivity::class.java);
            startActivity(intent)
        }

        //action to switch to locations activity
        binding.goToAppButton.setOnClickListener{
            //val intent = Intent(this, /*Locations Activity */)
            //startActivity(intent)
        }
    }


}