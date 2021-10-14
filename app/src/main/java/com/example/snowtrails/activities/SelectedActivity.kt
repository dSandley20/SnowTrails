package com.example.snowtrails.activities

import android.content.Intent
import android.location.Location
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentManager
import com.android.volley.VolleyError
import com.example.snowtrails.adapters.LocationImageAdapter
import com.example.snowtrails.api.Api
import com.example.snowtrails.databinding.ActivitySelectedBinding
import com.example.snowtrails.services.AuthService
import com.example.snowtrails.services.LocationService
import com.example.snowtrails.utils.getDatabase
import kotlinx.android.synthetic.main.activity_selected.*
import kotlinx.coroutines.CoroutineStart
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.json.JSONArray
import java.util.concurrent.LinkedBlockingQueue
import java.util.concurrent.ThreadPoolExecutor
import java.util.concurrent.TimeUnit


class SelectedActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySelectedBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySelectedBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val db = getDatabase().returnDB(this@SelectedActivity)

        val locationData : com.example.snowtrails.room.entities.Location? = intent.getParcelableExtra("Location")
        val locationImageArray = db.getImageDao().getByLocationID(locationData!!.id)
        println("locationImages: " +locationImageArray)
        val imagesAdapter = LocationImageAdapter(locationImageArray, this)
        locationImageSlider.adapter = imagesAdapter
    }

    //Callback Object to get data from the API class and bring it into main
    private val obj = object : Api.VolleyArrayResponseListener {
        override fun onResponse(result: JSONArray) {
            println("in here")
            //show the location comment fragment

        }
        override fun onError(result: VolleyError) {
            println(result)
        }
    }

    //makes API request to get locations from API
    private fun loadComments(locationId : Int) {
        var api = Api(this)
        api.getRequestArray(api.getRequestType("GET"), "/comments/location/$locationId", obj)
    }
}

