package com.example.snowtrails.fragments

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.snowtrails.R
import com.example.snowtrails.viewmodels.MapLocationViewModel

class MapLocationFragment : Fragment(R.layout.map_location_fragment) {

    companion object {
        fun newInstance() = MapLocationFragment()
    }

    private lateinit var viewModel: MapLocationViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.map_location_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(MapLocationViewModel::class.java)
        // TODO: Use the ViewModel
    }

}