package com.example.snowtrails.adapters

import android.app.Activity
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.example.snowtrails.R
import com.example.snowtrails.room.entities.Chat
import com.squareup.picasso.Picasso

class ChatAdapter(private val context: Activity, private val chats: MutableList<Chat>) :
    ArrayAdapter<Chat>(context, R.layout.chat_list, chats) {
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val inflater = context.layoutInflater
        val rowView = inflater.inflate(R.layout.chat_list, null, true)

        val chatContainer = rowView.findViewById(R.id.chat_container) as LinearLayout
        val backgroundResource = rowView.findViewById(R.id.chat_bubble_layout) as LinearLayout
        val titleText = rowView.findViewById(R.id.title) as TextView
        val descriptionText = rowView.findViewById(R.id.description) as TextView

        titleText.text = chats[position].userName.substring(1,chats[position].userName.length-1 )
        descriptionText.text = chats[position].content.substring(1,chats[position].content.length-1 )

        if(chats[position].sentUserId == chats[position].storedUserId){

            backgroundResource.setBackgroundResource(R.drawable.shape_bg_outgoing)
        }else{

            backgroundResource.setBackgroundResource(R.drawable.shape_bg_incoming)
        }


        return rowView
    }
}