package com.example.snowtrails.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.add
import androidx.fragment.app.commit
import androidx.fragment.app.replace
import com.example.snowtrails.R
import com.example.snowtrails.databinding.ActivitySelectedBinding
import com.example.snowtrails.fragments.ChatLocationFragment
import com.example.snowtrails.fragments.MainLocationFragment
import com.example.snowtrails.fragments.MapLocationFragment
import com.example.snowtrails.fragments.NoAuthFragment
import com.example.snowtrails.services.AuthService
import com.example.snowtrails.services.PoolService
import kotlinx.coroutines.CoroutineStart
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch


class SelectedActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySelectedBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySelectedBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //getting data from the intent
        val locationData: com.example.snowtrails.room.entities.Location? =
            intent.getParcelableExtra("Location")
        val isLoggedIn = intent.getBooleanExtra("isLoggedIn", false)
        val authUser: com.example.snowtrails.room.entities.AuthenticatedUser? =
            intent.getParcelableExtra("authUser")

        //setting bundle outside of check so other function can use data
        val bundle = bundleOf(
            "location_data" to locationData,
            "isLoggedIn" to isLoggedIn,
            "authUser" to authUser
        )

        if (savedInstanceState == null) {
            supportFragmentManager.commit {
                setReorderingAllowed(true)
                add<MainLocationFragment>(R.id.location_fragment_container, args = bundle)
            }
        }

        binding.locationMainButton.setOnClickListener {
            goToMainFragment(bundle)
        }

        binding.mapLocationButton.setOnClickListener {
            goToMapFragment(bundle)
        }

        binding.locationChatButton.setOnClickListener {
            goToChatFragment(bundle, isLoggedIn)
        }
    }

    private fun goToMainFragment(bundle: Bundle) {
        supportFragmentManager.commit {
            setReorderingAllowed(true)
            replace<MainLocationFragment>(R.id.location_fragment_container, args = bundle)
        }
    }

    private fun goToMapFragment(bundle: Bundle) {
        supportFragmentManager.commit {
            setReorderingAllowed(true)
            replace<MapLocationFragment>(R.id.location_fragment_container, args = bundle)
        }
    }

    private fun goToChatFragment(bundle: Bundle, isLoggedIn: Boolean) {
        supportFragmentManager.commit {
            setReorderingAllowed(true)
            if (isLoggedIn) {
                replace<ChatLocationFragment>(R.id.location_fragment_container, args = bundle)
            } else {
                replace<NoAuthFragment>(R.id.location_fragment_container, args = bundle)
            }
        }
    }

}