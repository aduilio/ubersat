package com.aduilio.viasat.ubersat.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.aduilio.viasat.ubersat.R
import com.aduilio.viasat.ubersat.adapter.PlanAdapter
import com.aduilio.viasat.ubersat.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.rvPlans.setHasFixedSize(true)
        binding.rvPlans.adapter = PlanAdapter()
    }
}