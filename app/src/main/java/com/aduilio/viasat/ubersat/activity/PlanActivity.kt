package com.aduilio.viasat.ubersat.activity

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.aduilio.viasat.ubersat.R
import com.aduilio.viasat.ubersat.adapter.PlanAdapter
import com.aduilio.viasat.ubersat.databinding.ActivityPlanBinding
import com.aduilio.viasat.ubersat.entity.Plan

class PlanActivity : AppCompatActivity() {

    companion object {
        const val PLAN_PARAM = "plan-param"
    }

    private lateinit var binding: ActivityPlanBinding
    private var planId: Long = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPlanBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        loadPLan()

        binding.btSeeInstallers.setOnClickListener {
            val intent = Intent(
                this@PlanActivity,
                InstallerActivity::class.java
            ).putExtra(PLAN_PARAM, planId)
            startActivity(intent)
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            this.onBackPressed()
        }

        return super.onOptionsItemSelected(item)
    }

    private fun loadPLan() {
        intent?.extras?.getParcelable<Plan>(PLAN_PARAM)?.let {
            planId = it.id
            setDetails(it)
        }
    }

    private fun setDetails(plan: Plan) {
        plan.apply {
            val value = StringBuilder()
            value.append(isp).append("\n\n")
                .append(description).append("\n\n")
                .append(getCapacity(dataCapacity)).append("\n")
                .append(String.format(getString(R.string.download_speed_label), downloadSpeed))
                .append("\n")
                .append(String.format(getString(R.string.upload_speed_label), uploadSpeed))
                .append("\n")
                .append(String.format(getString(R.string.price_per_month_label), pricePerMonth))
                .append("\n")
                .append(
                    String.format(
                        getString(R.string.type_of_internet_label),
                        getType(typeOfInternet)
                    )
                )
                .append("\n")

            binding.tvPlanDetails.text = value.toString()
        }
    }

    private fun getCapacity(dataCapacity: Double?): String {
        return dataCapacity?.let {
            String.format(
                getString(R.string.data_capacity_label), it
            )
        } ?: run {
            getString(R.string.data_capacity_label_unlimited)
        }
    }

    private fun getType(type: String): String {
        if (type == PlanAdapter.TYPE_SATELLITE) {
            return getString(R.string.satellite)
        }

        return type.replaceFirstChar { it.uppercase() }
    }
}