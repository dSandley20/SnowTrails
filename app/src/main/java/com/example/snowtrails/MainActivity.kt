package com.example.snowtrails

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.android.volley.VolleyError
import com.example.snowtrails.activities.LoginActivity
import com.example.snowtrails.api.Api
import com.example.snowtrails.databinding.ActivityMainBinding
import com.example.snowtrails.room.entities.Location
import com.example.snowtrails.utils.getDatabase
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import org.json.JSONArray
import java.util.*


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

        loadLocations()

        //action to switch to login/register screen
        binding.loginButton.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java);
            startActivity(intent)
        }

        //action to switch to locations activity
        binding.goToAppButton.setOnClickListener {
            //val intent = Intent(this, /*Locations Activity */)
            //startActivity(intent)
        }
    }

    //Callback Object to get data from the API class and bring it into main
    private val obj = object : Api.VolleyArrayResponseListener {
        override fun onResponse(result: JSONArray) {
            setLocationsDB(result)
        }

        override fun onError(result: VolleyError) {
            println(result)
        }
    }

    //makes API request to get locations from API
    private fun loadLocations() {
        var api = Api(this)
        api.getRequestArray(api.getRequestType("GET"), "/locations", obj)
    }

    //set the locations table in Room DB
    private fun setLocationsDB(response: JSONArray) = runBlocking {
        val db = getDatabase().returnDB(this@MainActivity)

        val job = launch {
            val locationsArray: MutableList<Location> = mutableListOf()
            for (i in 0 until response.length()) {
                val location = response.getJSONObject(i)
                val newEntry = Location(
                    location.get("id").toString(),
                    location.get("name").toString(),
                    location.get("country").toString(),
                    location.get("city").toString(),
                    location.get("state").toString(),
                    location.get("zipcode").toString()
                )
            }
            db.getLocationDao().insertAll(locationsArray.toList())
        }

        job.join()
        println(db.getLocationDao().getAll())
    }


}