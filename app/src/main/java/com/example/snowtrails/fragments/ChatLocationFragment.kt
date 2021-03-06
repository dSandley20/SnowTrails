package com.example.snowtrails.fragments

import android.content.Intent
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.snowtrails.R
import com.example.snowtrails.activities.LocationActivity
import com.example.snowtrails.adapters.ChatAdapter
import com.example.snowtrails.databinding.ChatLocationFragmentBinding
import com.example.snowtrails.room.entities.AuthenticatedUser
import com.example.snowtrails.room.entities.Chat
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

        getActivity()?.getWindow()?.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        _binding = ChatLocationFragmentBinding.inflate(inflater, container, false)
        val view = binding.root
        val locationData: Location = requireArguments().get("location_data") as Location
        val authData: AuthenticatedUser = requireArguments().get("authUser") as AuthenticatedUser
        val locationChannel = getPubnub().returnLocationChannel(locationData!!.id)
        //setting up chat adapter to display everything properly
        binding.messageList.setHasFixedSize(true)
        val chatAdapter = ChatAdapter(requireContext())
        binding.messageList.adapter = chatAdapter
        //

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
                    getActivity()?.runOnUiThread {
                        val tempMessage = pnMessageResult.message.asJsonObject
                        chatAdapter.addChat(
                            Chat(
                                tempMessage.get("sentUserId").toString().toInt(),
                                authData.id,
                                tempMessage.get("userName").toString(),
                                tempMessage.get("msg").toString()
                            )
                        )

                        binding.messageList.scrollToPosition(chatAdapter.itemCount - 1)
                    }


                }
            }
        })
        getPubnub().subscribeToChannels(pubnub, locationChannel)

        binding.btnSend.setOnClickListener {
            getPubnub().sendMessage(
                requireContext(),
                pubnub,
                locationChannel,
                PoolService().getSinglePool(),
                binding.txtMessage.text.toString()
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