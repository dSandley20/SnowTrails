package com.example.snowtrails.services

import android.content.Context
import com.example.snowtrails.utils.getDatabase
import kotlinx.coroutines.*
import java.util.concurrent.ThreadPoolExecutor

class AuthService() {

    suspend fun checkAuthUser(context: Context, scheduler: ThreadPoolExecutor) = coroutineScope {
        withContext(scheduler.asCoroutineDispatcher()){
        val a = async { checkHelper(context) }
        a.await()
        }
    }

    suspend fun checkHelper(context: Context) : Boolean  {
        Thread.sleep(1000L)
        val db = getDatabase().returnDB(context).getAuthUserDao()
        var authBoolean : Boolean = false
        println("All authe: " + db.getAll())
        if(db.getAll().size > 0){
            authBoolean = true
        }
        return authBoolean
    }

}