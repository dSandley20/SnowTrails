package com.example.snowtrails.activities


import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.snowtrails.api.Api
import com.example.snowtrails.databinding.ActivityRegisterBinding

class RegisterActivity : AppCompatActivity (){
    private lateinit var binding : ActivityRegisterBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    fun createAccount(){
        var api = Api(this)
        var createAccountData = HashMap<String,String>()
        createAccountData.put("firstName", binding.nameRegisterInput.text.toString().split(" ")[0])
        createAccountData.put("lastName", binding.nameRegisterInput.text.toString().split(" ")[1])
        createAccountData.put("email", binding.emailRegisterInput.text.toString())
        createAccountData.put("userName", binding.usernameRegisterInput.text.toString())
        createAccountData.put("password", binding.passwordRegisterInput.text.toString())
        api.makeRequest(api.getRequestType("POST"),"/createUser", createAccountData)
        //TODO get response and set JWT and error messages
    }
}