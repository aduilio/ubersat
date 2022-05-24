package com.aduilio.viasat.ubersat.activity

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.MenuItem
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.aduilio.viasat.ubersat.R
import com.aduilio.viasat.ubersat.adapter.InstallerAdapter
import com.aduilio.viasat.ubersat.databinding.ActivityInstallerBinding
import com.aduilio.viasat.ubersat.viewmodel.InstallerViewModel
import com.google.android.material.snackbar.Snackbar

class InstallerActivity : AppCompatActivity() {

    private lateinit var binding: ActivityInstallerBinding
    private lateinit var installerAdapter: InstallerAdapter
    private val installerViewModel: InstallerViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityInstallerBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        setupComponents()
        setupViewModel()
        getInstallers()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            this.onBackPressed()
        }

        return super.onOptionsItemSelected(item)
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
            Snackbar.make(binding.root, R.string.fail_get_plans, Snackbar.LENGTH_SHORT)
                .show()
        }
    }
}