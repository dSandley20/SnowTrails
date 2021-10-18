package com.example.snowtrails.adapters

import android.app.Activity
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView
import com.example.snowtrails.R
import com.example.snowtrails.room.entities.Comment

class LocationCommentAdapter(private val context: Activity, private val comments: List<Comment>) :
    ArrayAdapter<Comment>(context, R.layout.comment_list, comments) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val inflater = context.layoutInflater
        val rowView = inflater.inflate(R.layout.comment_list, null, true)

        val titleText = rowView.findViewById(R.id.title) as TextView
        //val imageView = rowView.findViewById(R.id.icon) as ImageView
        val descriptionText = rowView.findViewById(R.id.description) as TextView

        titleText.text = "user name goes here"
        descriptionText.text = comments[position].content

        return rowView
    }
}