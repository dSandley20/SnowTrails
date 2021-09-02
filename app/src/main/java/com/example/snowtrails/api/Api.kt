package com.example.snowtrails.api

import android.content.Context
import android.util.Log
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.VolleyError
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.RequestFuture
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.snowtrails.activities.LoginActivity
import org.json.JSONObject



class Api(private val context: Context) {
    private val queue = Volley.newRequestQueue(context)
    private val startingUrl: String = "http://10.0.2.2:8080"

    /**
     * Returns the request type -> to be used with the other function in the Api class
     */
    fun getRequestType(methodType: String): Int {
        // Determine what request type to pass in
        val requestType = when (methodType) {
            "GET" -> Request.Method.GET
            "POST" -> Request.Method.POST
            "PUT" -> Request.Method.PUT
            else -> {
                Request.Method.DELETE
            }
        }
        return requestType
    }

    fun getRequest(requestType: Int, endUrl: String) {
        val stringRequest = StringRequest(requestType, (startingUrl + endUrl),
            { response ->
                //console log the response just as proof as concept
                println(response)

            },
            { err -> println(err) })
        // Add the request to the RequestQueue.
        queue.add(stringRequest)
    }

    public interface VolleyResponseLister{
        fun onResponse(result: JSONObject) {}

        fun onError(result: VolleyError){}

        fun setContext(context: Context){}
    }

    /**
     * callback param is a anonymous object that is based off the VolleyResponseListener to create callbaks
     */
    fun makeRequest(
        requestType: Int,
        endUrl: String,
        postData: JSONObject,
        callback: VolleyResponseLister
    ) {
        val jsonObjectRequest = JsonObjectRequest(requestType, (startingUrl + endUrl), postData,
            { response ->
                callback.onResponse(response)
            }
            , { error ->
               callback.onError(error)
            }
        )
        queue.add(jsonObjectRequest)
    }
}