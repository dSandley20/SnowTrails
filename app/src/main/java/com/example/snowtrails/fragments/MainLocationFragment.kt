package com.example.snowtrails.fragments

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.android.volley.VolleyError
import com.example.snowtrails.R
import com.example.snowtrails.adapters.LocationImageAdapter
import com.example.snowtrails.api.Api
import com.example.snowtrails.room.entities.Location
import com.example.snowtrails.utils.getDatabase
import com.example.snowtrails.viewmodels.MainLocationViewModel
import kotlinx.android.synthetic.main.main_location_fragment.*
import org.json.JSONArray

class MainLocationFragment : Fragment(R.layout.main_location_fragment) {

    companion object {
        fun newInstance() = MainLocationFragment()
    }

    private lateinit var viewModel: MainLocationViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.main_location_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(MainLocationViewModel::class.java)
        // TODO: Use the ViewModel

        val locationData : Location = requireArguments().get("location_data") as Location
        val db = getDatabase().returnDB(requireContext())
        val locationImageArray = db.getImageDao().getByLocationID(locationData!!.id)
        val imagesAdapter = LocationImageAdapter(locationImageArray, requireContext())
        locationImageSlider.adapter = imagesAdapter

        loadComments(locationData!!.id)

    }

    //Callback Object to get data from the API class and bring it into main
    private val obj = object : Api.VolleyArrayResponseListener {
        override fun onResponse(result: JSONArray) {
            //populate location comment list view

        }
        override fun onError(result: VolleyError) {
            println(result)
        }
    }

    //makes API request to get locations from API
    private fun loadComments(locationId : Int) {
        var api = Api(requireContext())
        api.getRequestArray(api.getRequestType("GET"), "/comments/location/$locationId", obj)
    }

}