package com.aduilio.viasat.ubersat.adapter

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Color
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.aduilio.viasat.ubersat.R
import com.aduilio.viasat.ubersat.databinding.InstallerItemBinding
import com.aduilio.viasat.ubersat.entity.Installer


class InstallerAdapter : RecyclerView.Adapter<InstallerAdapter.ViewHolder>() {

    companion object {
        const val COMPOUND_DRAWABLE_TOP_INDEX = 1
    }

    private val installers: MutableList<Installer> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.installer_item, parent, false)

        return ViewHolder(view)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        val context = viewHolder.itemView.context

        installers[position].apply {
            viewHolder.binding.tvInstallerName.text = name
            viewHolder.binding.tvInstallerRating.text = rating.toString()
            viewHolder.binding.tvInstallerPrice.text =
                String.format(context.getString(R.string.price_per_km_label), pricePerKm)
            viewHolder.binding.tvInstallerDistance.text =
                String.format(context.getString(R.string.distance_km_label), pricePerKm)

            viewHolder.binding.ivPhone.setOnClickListener {
                val intent = Intent(Intent.ACTION_CALL, Uri.parse("tel:" + "11223344"))
                context.startActivity(intent)
            }

            viewHolder.binding.ivEmail.setOnClickListener {
                val intent =
                    Intent(Intent.ACTION_SENDTO, Uri.parse("mailto:" + "installer@viasat.com"))
                context.startActivity(intent)
            }
        }
    }

    override fun getItemCount() = installers.count()

    @SuppressLint("NotifyDataSetChanged")
    fun setInstallers(installers: List<Installer>) {
        this.installers.clear()
        this.installers.addAll(installers)

        notifyDataSetChanged()
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val binding: InstallerItemBinding = InstallerItemBinding.bind(view)

        init {
            binding.tvInstallerRating.compoundDrawables[COMPOUND_DRAWABLE_TOP_INDEX].setTint(
                Color.parseColor(
                    "#D39702"
                )
            )
        }
    }
}