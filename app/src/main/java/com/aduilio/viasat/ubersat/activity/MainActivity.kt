package com.aduilio.viasat.ubersat.activity

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
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

        binding.tvNoPlans.visibility = View.GONE

        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED || ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED || ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.CALL_PHONE
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                this@MainActivity,
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.CALL_PHONE), 1
            )
        } else {
            locationHelper = LocationHelper(this, this)
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED || ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED || ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.CALL_PHONE
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            onBackPressed()
        } else {
            locationHelper = LocationHelper(this, this)
        }

        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.plans_menu, menu)

        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_change_state -> showStateDialog()
            R.id.action_installer -> showLogin()
        }

        return super.onOptionsItemSelected(item)
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

            if (it.isEmpty()) {
                binding.tvNoPlans.visibility = View.VISIBLE
            } else {
                binding.tvNoPlans.visibility = View.GONE
            }
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

    private fun showStateDialog() {
        val states = AdminAreaViewModel.getStates()

        AlertDialog.Builder(this)
            .setSingleChoiceItems(states, -1, null)
            .setPositiveButton(
                R.string.search
            ) { dialog, _ ->
                val selectedPosition: Int =
                    (dialog as AlertDialog).listView.checkedItemPosition
                if (selectedPosition >= 0) {
                    val adminAreaName = states[selectedPosition].toString()
                    val adminAreaCode = AdminAreaViewModel.getAdminAreaCode(adminAreaName)
                    getPlans(AdminArea(adminAreaName, adminAreaCode!!))
                }
            }
            .setNegativeButton(R.string.cancel, null)
            .show()
    }

    private fun showLogin() {
        AlertDialog.Builder(this)
            .setView(R.layout.dialog_login)
            .setPositiveButton(R.string.send) { _, _ ->
                startActivity(
                    Intent(
                        this@MainActivity,
                        QuoteActivity::class.java
                    )
                )
            }
            .setNegativeButton(R.string.cancel, null)
            .create().show()
    }
}