package com.aduilio.viasat.ubersat.adapter

import android.content.Intent
import android.net.Uri
import android.view.ContextMenu
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.aduilio.viasat.ubersat.R
import com.aduilio.viasat.ubersat.databinding.QuoteItemBinding

class QuoteAdapter : RecyclerView.Adapter<QuoteAdapter.ViewHolder>(),
    View.OnCreateContextMenuListener {

    private val quotes: MutableList<String> = mutableListOf()

    init {
        quotes.add(
            "Samuel Silva\nRua das Margaridas, 25\nSão Paulo\n11 2233445566\nsamuel@acme.com\n\n" +
                    "StirWay 70/35"
        )

        quotes.add(
            "Jaqueline Souza\nRua Machado de Assis, 100\nBelo Horizonte\n31 55667788\njaqueline@acme.com\n\n" +
                    "Viasat 20/30"
        )

        quotes.add(
            "Renan Garcia\nRua 21 de Abril, 40\nRio de Janeiro\n21 43546576\nrenan@acme.com\n\n" +
                    "Viva 600/600"
        )
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.quote_item, parent, false)

        view.setOnCreateContextMenuListener(this)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        val context = viewHolder.itemView.context

        quotes[position].apply {
            viewHolder.binding.tvClientInfo.text = this

            viewHolder.binding.ivPhone.setOnClickListener {
                val intent = Intent(Intent.ACTION_CALL, Uri.parse("tel:" + "11223344"))
                context.startActivity(intent)
            }

            viewHolder.binding.ivEmail.setOnClickListener {
                val intent =
                    Intent(Intent.ACTION_SENDTO, Uri.parse("mailto:" + "client@acme.com"))
                context.startActivity(intent)
            }
        }
    }

    override fun getItemCount() = quotes.count()

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val binding: QuoteItemBinding = QuoteItemBinding.bind(view)
    }

    override fun onCreateContextMenu(
        menu: ContextMenu?,
        view: View?,
        info: ContextMenu.ContextMenuInfo?
    ) {
        menu?.add(R.string.close_quote)?.setOnMenuItemClickListener {
            removeItem()
        }
    }

    private fun removeItem(): Boolean {
        this.quotes.removeAt(0)
        notifyItemRemoved(0)

        return true
    }
}