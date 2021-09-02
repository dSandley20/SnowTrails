package com.example.snowtrails.activities


import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.VolleyError
import com.example.snowtrails.api.Api
import com.example.snowtrails.databinding.ActivityRegisterBinding
import org.json.JSONObject


class RegisterActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRegisterBinding
    val registerInterface = this
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    //Callback Object to get data from the API class and bring it into main
    private val registerListern = object : Api.VolleyResponseLister {
        override fun onResponse(result: JSONObject) {
            println(result)
        }

        override fun onError(result: VolleyError) {
            println(result)
        }
    }

    fun createAccount() {
        var api = Api(this)
        var createAccountData = JSONObject()
        createAccountData.put("firstName", binding.nameRegisterInput.text.toString().split(" ")[0])
        createAccountData.put("lastName", binding.nameRegisterInput.text.toString().split(" ")[1])
        createAccountData.put("email", binding.emailRegisterInput.text.toString())
        createAccountData.put("userName", binding.usernameRegisterInput.text.toString())
        createAccountData.put("password", binding.passwordRegisterInput.text.toString())
        api.makeRequest(api.getRequestType("POST"),"/createUser", createAccountData, registerListern)
        //TODO get response and set JWT and error messages
    }
}