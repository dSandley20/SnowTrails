package com.example.snowtrails.adapters

import android.app.Activity
import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView
import com.example.snowtrails.R
import com.example.snowtrails.room.entities.Location

class LocationAdapter(private val context: Activity, private val locations: List<Location>) :
    ArrayAdapter<Location>(context, R.layout.location_list, locations) {
    override fun getView(position: Int, view: View?, parent: ViewGroup): View {
       val inflater = context.layoutInflater
       val rowView = inflater.inflate(R.layout.location_list, null, true)
        val titleText = rowView.findViewById(R.id.title) as TextView
        val imageView = rowView.findViewById(R.id.icon) as ImageView
        val descriptionText = rowView.findViewById(R.id.description) as TextView

        titleText.text = locations[position].name
        //TODO set image icon
        descriptionText.text = locations[position].country

        return rowView
    }
}