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
import org.json.JSONObject

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
            setAuthUser(result)
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

    private fun setAuthUser(result: JSONObject) {
        //TODO set JWT and move to locations activity and error messages
        val db = getDatabase().returnDB(this)
        val stringJWT: String = result.get("jwt").toString()
        db.getAuthUserDao()
            .setAuthUser(AuthenticatedUser(1, "dan", "sand", "dan@gmail.com", "dan1", stringJWT))
        //goes to the main locations activity
        //var intent = Intent(this, /*Whatever activity this is going to be called */ )
        //startActivity(intent)
    }
}