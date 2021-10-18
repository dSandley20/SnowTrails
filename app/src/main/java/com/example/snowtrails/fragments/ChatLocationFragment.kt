package com.example.snowtrails.fragments

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.snowtrails.R
import com.example.snowtrails.room.entities.Location
import com.example.snowtrails.utils.getPubnub
import com.example.snowtrails.viewmodels.ChatLocationViewModel
import com.google.gson.JsonObject
import com.pubnub.api.PubNub
import com.pubnub.api.callbacks.SubscribeCallback
import com.pubnub.api.enums.PNStatusCategory
import com.pubnub.api.models.consumer.PNStatus
import com.pubnub.api.models.consumer.pubsub.PNMessageResult
import com.pubnub.api.models.consumer.pubsub.PNPresenceEventResult

class ChatLocationFragment : Fragment() {

    companion object {
        fun newInstance() = ChatLocationFragment()
    }

    private lateinit var viewModel: ChatLocationViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.chat_location_fragment, container, false)
        val locationData: Location = requireArguments().get("location_data") as Location
        val locationId = locationData!!.id
        //create pubnub object
        val pubnub = getPubnub().returnPubNub(
            getString(R.string.pubnub_subscribe_key),
            getString(R.string.pubnub_publish_key),
            getString(R.string.pubnub_uuid)
        )
        val locationChannel = "all-chat-location-$locationId"

        val myMessage = JsonObject().apply {
            addProperty("msg", "Hello, world")
        }

        pubnub.addListener(object : SubscribeCallback(){
            override fun status(pubnub: PubNub, status: PNStatus) {
                when (status.category) {
                    PNStatusCategory.PNConnectedCategory -> {
                        // Connect event. You can do stuff like publish, and know you'll get it.
                        // Or just use the connected event to confirm you are subscribed for
                        // UI / internal notifications, etc.
                    }
                    PNStatusCategory.PNReconnectedCategory -> {
                        // Happens as part of our regular operation.
                        // This event happens when radio / connectivity is lost, then regained.
                    }
                    PNStatusCategory.PNUnexpectedDisconnectCategory -> {
                        // This event happens when radio / connectivity is lost.
                    }
                }
            }
            override fun message(pubnub: PubNub, pnMessageResult: PNMessageResult) {
                if (pnMessageResult.channel == locationChannel) {
                    println("Received message ${pnMessageResult.message.asJsonObject}")
                }
            }

            override fun presence(pubnub: PubNub, pnPresenceEventResult: PNPresenceEventResult) {
                // handle presence
            }
        })
        pubnub.subscribe(
            channels = listOf(locationChannel)
        )

        pubnub.publish(
            channel = locationChannel,
            message = myMessage
        ).async { result, status ->
            println(status)
            if (!status.error) {
                println("Message sent, timetoken: ${result!!.timetoken}")
            } else {
                println("Error while publishing")
                status.exception?.printStackTrace()
            }
        }
        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(ChatLocationViewModel::class.java)
        // TODO: Use the ViewModel
    }

}