package com.aduilio.viasat.ubersat.activity

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.aduilio.viasat.ubersat.R
import com.aduilio.viasat.ubersat.databinding.ActivityQuoteFormBinding
import com.google.android.material.snackbar.Snackbar

class QuoteFormActivity : AppCompatActivity() {

    private lateinit var binding: ActivityQuoteFormBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityQuoteFormBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        binding.btConfirm.setOnClickListener {
            Snackbar.make(binding.root, R.string.quote_sent, Snackbar.LENGTH_LONG)
                .show()
            Handler(Looper.getMainLooper()).postDelayed({
                onBackPressed()
            }, 1000)
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            this.onBackPressed()
        }

        return super.onOptionsItemSelected(item)
    }
}