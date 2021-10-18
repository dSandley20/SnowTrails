package com.example.snowtrails.utils

import android.content.Context
import android.provider.Settings.System.getString
import com.example.snowtrails.R
import com.google.gson.JsonObject
import com.pubnub.api.PNConfiguration
import com.pubnub.api.PubNub
import kotlinx.coroutines.*
import java.util.concurrent.ThreadPoolExecutor

class getPubnub {
    fun returnPubNub(subKey: String, pubKey: String, uuidParam: String): PubNub {
        val config = PNConfiguration().apply {
            subscribeKey = subKey
            publishKey = pubKey
            uuid = uuidParam
        }
        return PubNub(config)
    }

    fun returnLocationChannel(locationId: Int): String {
        return "all-chat-location-$locationId"
    }

    suspend fun createMessage(context: Context, scheduler: ThreadPoolExecutor, content: String) =
        coroutineScope {
            withContext(scheduler.asCoroutineDispatcher()) {
                val a = async { createMessageHelper(content, context) }
                a.await()
            }
        }

    suspend fun createMessageHelper(content: String, context: Context): JsonObject {
        val user = getDatabase().returnDB(context).getAuthUserDao().getAll()[0]
        return JsonObject().apply {
            addProperty("msg", content)
            addProperty("userId", user.id)
            addProperty("userName", user.userName)
        }
    }

    fun subscribeToChannels(pubnub: PubNub, locationChannel: String) {
        pubnub.subscribe(
            channels = listOf(locationChannel)
        )
    }

    fun sendMessage(
        context: Context,
        pubnub: PubNub,
        locationChannel: String,
        singlePool: ThreadPoolExecutor,
        content: String
    ) {
        GlobalScope.launch(Dispatchers.Main, CoroutineStart.DEFAULT) {
            pubnub.publish(
                channel = locationChannel,
                message = getPubnub().createMessage(context, singlePool, content)
            ).async { result, status ->
                println(status)
                if (!status.error) {
                    println("Message sent, timetoken: ${result!!.timetoken}")
                } else {
                    println("Error while publishing")
                    status.exception?.printStackTrace()
                }
            }
        }
    }
}