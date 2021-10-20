package com.example.snowtrails.services

import android.content.Context
import com.example.snowtrails.room.entities.Comment
import com.example.snowtrails.room.entities.Location
import com.example.snowtrails.utils.getDatabase
import kotlinx.coroutines.asCoroutineDispatcher
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.withContext
import org.json.JSONArray
import org.json.JSONObject
import java.util.*
import java.util.concurrent.ThreadPoolExecutor

class LocationService {

    suspend fun returnLocation(
        context: Context,
        scheduler: ThreadPoolExecutor,
        locationId: String
    ) = coroutineScope {
        withContext(scheduler.asCoroutineDispatcher()) {
            val a = async { getLocation(context, locationId) }
            a.await()
        }
    }

    suspend fun getLocation(context: Context, locationId: String): Location {
        Thread.sleep(1000L)
        val db = getDatabase().returnDB(context).getLocationDao()
        return db.getByID(locationId)
    }

    fun createLocationEntry(data: JSONObject): Location {
        val images = data.get("images") as JSONArray
        val firstImage = images.getJSONObject(0)
        return Location(
            data.get("id").toString().toInt(),
            data.get("name").toString(),
            data.get("country").toString(),
            data.get("city").toString(),
            data.get("state").toString(),
            data.get("zipcode").toString(),
            firstImage.get("id").toString().toInt()
        )
    }

    fun createCommentArray(data: JSONArray, locationId: Int): List<Comment> {
        var comments: MutableList<Comment> = mutableListOf()
        for (i in 0 until data.length()) {
            val tempData = data.getJSONObject(i)

            comments.add(
                Comment(
                    tempData.get("id").toString().toInt(),
                    locationId,
                    tempData.get("content").toString()
                )
            )
        }

        return Collections.unmodifiableList(comments)
    }

}