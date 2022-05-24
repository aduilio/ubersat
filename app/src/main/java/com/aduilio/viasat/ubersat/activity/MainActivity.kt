package com.aduilio.viasat.ubersat.activity

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.aduilio.viasat.ubersat.R
import com.aduilio.viasat.ubersat.adapter.PlanAdapter
import com.aduilio.viasat.ubersat.databinding.ActivityMainBinding
import com.aduilio.viasat.ubersat.viewmodel.PlanViewModel
import com.google.android.material.snackbar.Snackbar

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var planAdapter: PlanAdapter
    private val planViewModel: PlanViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupComponents()
        setupViewModel()
        getPlans()
    }

    private fun setupComponents() {
        planAdapter = PlanAdapter()
        binding.rvPlans.setHasFixedSize(true)
        binding.rvPlans.adapter = planAdapter
    }

    private fun setupViewModel() {
        planViewModel.plans.observe(this) {
            planAdapter.setPlans(it)
        }

//        planViewModel.showProgress.observe(this, {
//            binding.srlMatches.isRefreshing = it
//        })

        planViewModel.resultSuccess.observe(this) {
            showErrorMessage(it)
        }
    }

    private fun getPlans() {
        planViewModel.get()
    }

    private fun showErrorMessage(success: Boolean) {
        if (!success) {
            Snackbar.make(binding.root, R.string.fail_get_plans, Snackbar.LENGTH_SHORT)
                .show()
        }
    }
}