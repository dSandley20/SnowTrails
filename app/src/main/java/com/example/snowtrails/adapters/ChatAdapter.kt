package com.example.snowtrails.adapters

import android.app.Activity
import android.content.Context
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.snowtrails.R
import com.example.snowtrails.room.entities.Chat
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.chat_location_fragment.view.*
import kotlinx.android.synthetic.main.my_chat.view.*
import kotlinx.android.synthetic.main.other_chat.view.*

private const val VIEW_TYPE_MY_MESSAGE = 1
private const val VIEW_TYPE_OTHER_MESSAGE = 2

class ChatAdapter(val context : Context) : RecyclerView.Adapter<MessageViewHolder>(){
    private val messages : ArrayList<Chat> = ArrayList()

    fun addChat(message: Chat){
        messages.add(message)
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return messages.size
    }

    override fun getItemViewType(position: Int): Int {
        val message = messages.get(position)
        return if(message.sentUserId == message.storedUserId){
            VIEW_TYPE_MY_MESSAGE
        }else{
            VIEW_TYPE_OTHER_MESSAGE
        }
    }

    override fun onBindViewHolder(holder: MessageViewHolder, position: Int) {
        val message = messages.get(position)
        holder?.bind(message)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MessageViewHolder {
        return if (viewType == VIEW_TYPE_MY_MESSAGE){
            MyMessageViewHolder(
                LayoutInflater.from(context).inflate(R.layout.my_chat, parent, false)
            )
        }else{
            OtherMessageViewHolder(
                LayoutInflater.from(context).inflate(R.layout.other_chat, parent, false)
            )
        }
    }

    class MyMessageViewHolder(view: View) : MessageViewHolder(view){
        private var messageText : TextView = view.txtMyMessage

        override fun bind(message: Chat) {
            val userName = message.userName.substring(1,message.userName.length-1 )
            val content =  message.content.substring(1,message.content.length-1 )
            messageText.text = "$userName: $content "
        }
}
    class OtherMessageViewHolder(view: View) : MessageViewHolder(view){
        private var messageText: TextView = view.txtOtherMessage

        override fun bind(message: Chat) {
            val userName = message.userName.substring(1,message.userName.length-1 )
            val content =  message.content.substring(1,message.content.length-1 )
            messageText.text = "$userName: $content "
        }
    }
}


open class MessageViewHolder (view: View) : RecyclerView.ViewHolder(view) {
    open fun bind(message:Chat) {}
}