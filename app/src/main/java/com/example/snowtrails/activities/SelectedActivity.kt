package com.example.snowtrails.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.bundleOf
import androidx.fragment.app.add
import androidx.fragment.app.commit
import androidx.fragment.app.replace
import com.example.snowtrails.R
import com.example.snowtrails.databinding.ActivitySelectedBinding
import com.example.snowtrails.fragments.MainLocationFragment



class SelectedActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySelectedBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySelectedBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val locationData: com.example.snowtrails.room.entities.Location? =
            intent.getParcelableExtra("Location")

        if (savedInstanceState == null) {
            val bundle = bundleOf("location_data" to locationData)
            supportFragmentManager.commit {
                setReorderingAllowed(true)
                add<MainLocationFragment>(R.id.location_fragment_container, args = bundle)
            }
        }
    }

    fun goToMainFragment(){
        supportFragmentManager.commit {
            setReorderingAllowed(true)
            replace<MainLocationFragment>(R.id.location_fragment_container)
        }
    }


}

