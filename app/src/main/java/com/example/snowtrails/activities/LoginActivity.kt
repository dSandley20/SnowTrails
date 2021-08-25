package com.example.snowtrails.activities

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.snowtrails.api.Api
import com.example.snowtrails.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity (){
    private lateinit var binding : ActivityLoginBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //TODO add validation for password and username

        //logic for button on clicks
        binding.logInSubmit.setOnClickListener{
            submitLogin()
        }

        binding.goToRegisterButton.setOnClickListener {
            goToRegister()
        }
    }


    fun submitLogin(){
        var api = Api(this)
        var loginData = HashMap<String, String>()
        loginData.put("username", binding.usernameLoginInput.text.toString())
        loginData.put("password", binding.passwordLoginInput.text.toString())
        api.makeRequest(api.getRequestType("POST"), "/createuser", loginData)
    }

    fun goToRegister(){
        var intent = Intent(this, RegisterActivity::class.java)
        startActivity(intent)
    }
}