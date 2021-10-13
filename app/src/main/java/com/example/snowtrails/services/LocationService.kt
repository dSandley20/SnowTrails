package com.example.snowtrails.services

import android.content.Context
import com.example.snowtrails.room.entities.Location
import com.example.snowtrails.utils.getDatabase
import kotlinx.coroutines.asCoroutineDispatcher
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.withContext
import java.util.concurrent.ThreadPoolExecutor

class LocationService {

    suspend fun returnLocation (context: Context, scheduler: ThreadPoolExecutor, locationId: String)  = coroutineScope {
        withContext(scheduler.asCoroutineDispatcher()){
            val a = async { getLocation(context, locationId) }
            a.await()
        }
    }

    suspend fun getLocation(context: Context, locationId: String) : Location{
        Thread.sleep(1000L)
        val db = getDatabase().returnDB(context).getLocationDao()
        return db.getByID(locationId)
    }

}