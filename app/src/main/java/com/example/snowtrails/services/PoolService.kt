package com.example.snowtrails.services

import java.util.concurrent.LinkedBlockingQueue
import java.util.concurrent.ThreadPoolExecutor
import java.util.concurrent.TimeUnit

class PoolService() {
    fun getSinglePool(): ThreadPoolExecutor {
         return ThreadPoolExecutor(1, 1, 3L, TimeUnit.SECONDS, LinkedBlockingQueue())
    }
}