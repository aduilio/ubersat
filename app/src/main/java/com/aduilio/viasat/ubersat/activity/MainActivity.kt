package com.aduilio.viasat.ubersat.activity

import android.location.Location
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.aduilio.viasat.ubersat.R
import com.aduilio.viasat.ubersat.adapter.PlanAdapter
import com.aduilio.viasat.ubersat.databinding.ActivityMainBinding
import com.aduilio.viasat.ubersat.entity.AdminArea
import com.aduilio.viasat.ubersat.helper.LocationHelper
import com.aduilio.viasat.ubersat.viewmodel.AdminAreaViewModel
import com.aduilio.viasat.ubersat.viewmodel.PlanViewModel
import com.google.android.material.snackbar.Snackbar

class MainActivity : AppCompatActivity(), LocationHelper.LocationHelperListener {

    private lateinit var binding: ActivityMainBinding
    private lateinit var planAdapter: PlanAdapter
    private val planViewModel: PlanViewModel by viewModels()
    private val adminAreaViewModel: AdminAreaViewModel by viewModels()
    private lateinit var locationHelper: LocationHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupComponents()
        setupViewModel()

        locationHelper = LocationHelper(this, this)
    }

    override fun locationChange(location: Location) {
        adminAreaViewModel.get(location, this)
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

        planViewModel.showProgress.observe(this) { show ->
            if (show) {
                binding.cpiPlans.visibility = View.VISIBLE
            } else {
                binding.cpiPlans.visibility = View.GONE
            }
        }

        planViewModel.resultSuccess.observe(this) {
            showErrorMessage(it)
        }

        adminAreaViewModel.adminAreaCode.observe(this) {
            getPlans(it)
        }
    }

    private fun getPlans(adminArea: AdminArea? = null) {
        adminArea?.let {
            binding.tvLocationInfo.visibility = View.VISIBLE
            binding.tvLocationInfo.text =
                String.format(getString(R.string.admin_area_location_info), adminArea.name)
            planViewModel.get(adminArea.code)
        } ?: run {
            binding.tvLocationInfo.visibility = View.GONE
            planViewModel.get()
        }
    }

    private fun showErrorMessage(success: Boolean) {
        if (!success) {
            Snackbar.make(binding.root, R.string.fail_get_plans, Snackbar.LENGTH_SHORT)
                .show()
        }
    }
}