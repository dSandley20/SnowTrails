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

    private suspend fun checkHelper(context: Context) : Boolean  {
        Thread.sleep(1000L)
        val db = getDatabase().returnDB(context).getAuthUserDao()
        return db.getAll().size > 0
    }

    suspend fun checkIsMe(context: Context, scheduler: ThreadPoolExecutor ,userId: Int) = coroutineScope{
        withContext(scheduler.asCoroutineDispatcher()){
            val a = async { checkIsMeHelper(context, userId) }
            a.await()
        }
    }
    private suspend fun checkIsMeHelper(context: Context, userId : Int) : Boolean{
        val user = getDatabase().returnDB(context).getAuthUserDao().getAll()[0]
        return user.id == userId
    }
}