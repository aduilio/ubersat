package com.aduilio.viasat.ubersat.activity

import android.location.Address
import android.location.Geocoder
import android.location.Location
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.aduilio.viasat.ubersat.R
import com.aduilio.viasat.ubersat.adapter.InstallerAdapter
import com.aduilio.viasat.ubersat.databinding.ActivityInstallerBinding
import com.aduilio.viasat.ubersat.helper.LocationHelper
import com.aduilio.viasat.ubersat.viewmodel.AdminAreaViewModel
import com.aduilio.viasat.ubersat.viewmodel.InstallerViewModel
import com.google.android.material.snackbar.Snackbar
import java.util.*


class InstallerActivity : AppCompatActivity(), LocationHelper.LocationHelperListener {

    private lateinit var binding: ActivityInstallerBinding
    private lateinit var installerAdapter: InstallerAdapter
    private val installerViewModel: InstallerViewModel by viewModels()
    private lateinit var locationHelper: LocationHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityInstallerBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        setupComponents()
        setupViewModel()

        locationHelper = LocationHelper(this, this)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.installers_menu, menu)

        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> this.onBackPressed()
            R.id.action_sort_distance -> installerAdapter.sortInstallers(compareBy { installer -> installer.distance })
            R.id.action_sort_price -> installerAdapter.sortInstallers(compareBy { installer -> installer.pricePerKm })
            R.id.action_sort_rating -> installerAdapter.sortInstallers(compareByDescending { installer -> installer.rating })
        }

        return super.onOptionsItemSelected(item)
    }

    override fun locationChange(location: Location) {
        installerAdapter.setCurrentLocation(location)

        val geocoder = Geocoder(this, Locale.getDefault())
        val addresses: List<Address> =
            geocoder.getFromLocation(location.latitude, location.longitude, 1)

        val adminAreaCode = AdminAreaViewModel.getAdminAreaCode(addresses[0].adminArea)
        adminAreaCode?.let {
            getInstallers()
        } ?: run {
            getInstallers()
        }
    }

    private fun setupComponents() {
        installerAdapter = InstallerAdapter()
        binding.rvInstallers.setHasFixedSize(true)
        binding.rvInstallers.adapter = installerAdapter
    }

    private fun setupViewModel() {
        installerViewModel.installers.observe(this) {
            if (it.isEmpty()) {
                Snackbar.make(binding.root, R.string.no_installers, Snackbar.LENGTH_LONG)
                    .show()
                Handler(Looper.getMainLooper()).postDelayed({
                    onBackPressed()
                }, 1000)
            } else {
                installerAdapter.setInstallers(it)
            }
        }

        installerViewModel.showProgress.observe(this) { show ->
            if (show) {
                binding.cpiInstallers.visibility = View.VISIBLE
            } else {
                binding.cpiInstallers.visibility = View.GONE
            }
        }

        installerViewModel.resultSuccess.observe(this) {
            showErrorMessage(it)
        }
    }

    private fun getInstallers() {
        intent?.extras?.getLong(PlanActivity.PLAN_PARAM)?.let {
            installerViewModel.get(it)
        } ?: run {
            super.onBackPressed()
        }
    }

    private fun showErrorMessage(success: Boolean) {
        if (!success) {
            Snackbar.make(binding.root, R.string.fail_get_installers, Snackbar.LENGTH_SHORT)
                .show()
        }
    }
}