package com.aduilio.viasat.ubersat.activity

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.aduilio.viasat.ubersat.adapter.QuoteAdapter
import com.aduilio.viasat.ubersat.databinding.ActivityQuoteBinding

class QuoteActivity : AppCompatActivity() {

    private lateinit var binding: ActivityQuoteBinding
    private lateinit var quoteAdapter: QuoteAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityQuoteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        setupComponents()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> this.onBackPressed()
        }

        return super.onOptionsItemSelected(item)
    }

    private fun setupComponents() {
        quoteAdapter = QuoteAdapter()
        binding.rvQuotes.setHasFixedSize(true)
        binding.rvQuotes.adapter = quoteAdapter

        registerForContextMenu(binding.rvQuotes)
    }
}