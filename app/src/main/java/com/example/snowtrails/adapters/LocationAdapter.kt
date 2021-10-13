package com.example.snowtrails.adapters

import android.app.Activity
import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView
import com.example.snowtrails.R
import com.example.snowtrails.api.Api
import com.example.snowtrails.room.entities.Location
import com.squareup.picasso.Picasso

class LocationAdapter(private val context: Activity, private val locations: List<Location>) :
    ArrayAdapter<Location>(context, R.layout.location_list, locations) {
    override fun getView(position: Int, view: View?, parent: ViewGroup): View {
        val inflater = context.layoutInflater
        val rowView = inflater.inflate(R.layout.location_list, null, true)

        val titleText = rowView.findViewById(R.id.title) as TextView
        val imageView = rowView.findViewById(R.id.icon) as ImageView
        val descriptionText = rowView.findViewById(R.id.description) as TextView

        val imageId = locations[position].locationImage

        titleText.text = locations[position].name
        Picasso.get().invalidate("http://10.0.2.2:8080/images/$imageId")
        Picasso.get().load("http://10.0.2.2:8080/images/$imageId").resize(80,80).centerCrop().placeholder(R.drawable.ic_launcher_background).into(imageView)
        descriptionText.text = locations[position].country

        return rowView
    }

}