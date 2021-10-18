package com.example.snowtrails.fragments

import android.content.Intent
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.snowtrails.R
import com.example.snowtrails.activities.LocationActivity
import com.example.snowtrails.databinding.ChatLocationFragmentBinding
import com.example.snowtrails.room.entities.Location
import com.example.snowtrails.services.AuthService
import com.example.snowtrails.services.PoolService
import com.example.snowtrails.utils.getPubnub
import com.example.snowtrails.viewmodels.ChatLocationViewModel
import com.google.gson.JsonObject
import com.pubnub.api.PubNub
import com.pubnub.api.callbacks.SubscribeCallback
import com.pubnub.api.enums.PNStatusCategory
import com.pubnub.api.models.consumer.PNStatus
import com.pubnub.api.models.consumer.pubsub.PNMessageResult
import com.pubnub.api.models.consumer.pubsub.PNPresenceEventResult
import kotlinx.coroutines.CoroutineStart
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.util.concurrent.LinkedBlockingQueue
import java.util.concurrent.ThreadPoolExecutor
import java.util.concurrent.TimeUnit

class ChatLocationFragment : Fragment() {

    companion object {
        fun newInstance() = ChatLocationFragment()
    }

    private lateinit var viewModel: ChatLocationViewModel
    private var _binding: ChatLocationFragmentBinding? = null
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = ChatLocationFragmentBinding.inflate(inflater, container, false)
        val view = binding.root
        val locationData: Location = requireArguments().get("location_data") as Location
        val locationChannel = getPubnub().returnLocationChannel(locationData!!.id)
        //create pubnub object
        val pubnub = createPubNubClient()

        pubnub.addListener(object : SubscribeCallback() {
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
                    GlobalScope.launch(Dispatchers.Main, CoroutineStart.DEFAULT) {
                        if (AuthService().checkIsMe(
                                requireContext(),
                                PoolService().getSinglePool(),
                                pnMessageResult.message.asJsonObject.get("userId").toString()
                                    .toInt()
                            )
                        ) {
                            //add to right side
                        } else {
                            //add to left side
                        }
                    }
                }
            }
        })
        getPubnub().subscribeToChannels(pubnub, locationChannel)

        binding.tempSendMessage.setOnClickListener {
            getPubnub().sendMessage(
                requireContext(),
                pubnub,
                locationChannel,
                PoolService().getSinglePool(),
                binding.tempTextField.text.toString()
            )
        }

        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(ChatLocationViewModel::class.java)
        // TODO: Use the ViewModel
    }

    private fun createPubNubClient(): PubNub {
        return getPubnub().returnPubNub(
            getString(R.string.pubnub_subscribe_key),
            getString(R.string.pubnub_publish_key),
            getString(R.string.pubnub_uuid)
        )
    }

}