package com.example.snowtrails.activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.VolleyError
import com.example.snowtrails.api.Api
import com.example.snowtrails.databinding.ActivityLoginBinding
import com.example.snowtrails.room.entities.AuthenticatedUser
import com.example.snowtrails.utils.getDatabase
import com.google.android.material.dialog.MaterialDialogs
import org.json.JSONObject
import com.google.gson.JsonElement
import com.google.gson.JsonParser
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    var test: String = "text"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //TODO add validation for password and username

        //logic for button on clicks
        binding.logInSubmit.setOnClickListener {
            submitLogin()
        }

        binding.goToRegisterButton.setOnClickListener {
            goToRegister()
        }
    }

    //Callback Object to get data from the API class and bring it into main
    private val obj = object : Api.VolleyResponseLister {
        override fun onResponse(result: JSONObject) {
            setAuthUser(result, )
        }

        override fun onError(result: VolleyError) {
            println(result)
        }
    }

    private fun submitLogin() {
        var api = Api(this)
        var loginData = JSONObject()
        loginData.put("username", binding.usernameLoginInput.text.toString())
        loginData.put("password", binding.passwordLoginInput.text.toString())
        api.makeRequest(api.getRequestType("POST"), "/authenticate", loginData, obj)

    }

    private fun goToRegister() {
        var intent = Intent(this, RegisterActivity::class.java)
        startActivity(intent)
    }

    private fun setAuthUser(result: JSONObject) = runBlocking {
        //TODO set JWT and move to locations activity and error messages
        val db = getDatabase().returnDB(this@LoginActivity)
        val stringJWT: String = result.get("jwt").toString()
        val userJson = JsonParser.parseString(result.get("user").toString()).asJsonObject

        //kotlin couritines job
        val job = launch {
            db.getAuthUserDao()
                .setAuthUser(
                    AuthenticatedUser(
                        userJson.get("id").toString().toInt(),
                        userJson.get("firstName").toString(),
                        userJson.get("lastName").toString(),
                        userJson.get("email").toString(),
                        userJson.get("userName").toString(),
                        stringJWT
                    )
                )
        }
        job.join()

        //goes to the main locations activity
        var intent = Intent(this@LoginActivity, LocationActivity::class.java )
        startActivity(intent)
    }


}