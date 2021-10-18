package com.example.snowtrails.utils

import android.provider.Settings.System.getString
import com.example.snowtrails.R
import com.pubnub.api.PNConfiguration
import com.pubnub.api.PubNub

class getPubnub {
    fun returnPubNub(subKey: String, pubKey: String, uuidParam : String): PubNub {
        val config = PNConfiguration().apply {
            subscribeKey =  subKey
            publishKey = pubKey
            uuid = uuidParam
        }
        return PubNub(config)
    }
}