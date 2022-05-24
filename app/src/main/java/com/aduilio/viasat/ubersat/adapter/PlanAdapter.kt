package com.aduilio.viasat.ubersat.adapter

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.aduilio.viasat.ubersat.R
import com.aduilio.viasat.ubersat.activity.PlanActivity
import com.aduilio.viasat.ubersat.databinding.PlanItemBinding
import com.aduilio.viasat.ubersat.entity.Plan

class PlanAdapter : RecyclerView.Adapter<PlanAdapter.ViewHolder>() {

    companion object {
        const val MEGA_BYTES = "MB"

        const val TYPE_CABLE = "cable"
        const val TYPE_RADIO = "radio"
        const val TYPE_SATELLITE = "sat"
        const val TYPE_WIRE = "wire"

    }

    private val plans: MutableList<Plan> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.plan_item, parent, false)

        return ViewHolder(view)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        plans[position].apply {
            viewHolder.binding.tvPlanName.text = isp

            dataCapacity?.let {
                viewHolder.binding.tvPlanCapacity.visibility = View.VISIBLE
                viewHolder.binding.tvPlanCapacity.text = "$dataCapacity$MEGA_BYTES"
            } ?: run {
                viewHolder.binding.tvPlanCapacity.visibility = View.INVISIBLE
            }

            when (typeOfInternet) {
                TYPE_CABLE -> viewHolder.binding.ivPlanType.setImageResource(R.drawable.ic_cable)
                TYPE_RADIO -> viewHolder.binding.ivPlanType.setImageResource(R.drawable.ic_radio)
                TYPE_SATELLITE -> viewHolder.binding.ivPlanType.setImageResource(R.drawable.ic_satellite)
                TYPE_WIRE -> viewHolder.binding.ivPlanType.setImageResource(R.drawable.ic_wire)
            }

            viewHolder.binding.tvPlanDownload.text = "$downloadSpeed$MEGA_BYTES"
            viewHolder.binding.tvPlanUpload.text = "$uploadSpeed$MEGA_BYTES"
            viewHolder.binding.tvPlanPrice.text = "$$pricePerMonth"

            val context = viewHolder.itemView.context
            viewHolder.itemView.setOnClickListener {
                val intent = Intent(
                    context,
                    PlanActivity::class.java
                ).putExtra(PlanActivity.PLAN_PARAM, this)
                context.startActivity(intent)
            }
        }
    }

    override fun getItemCount() = plans.count()

    @SuppressLint("NotifyDataSetChanged")
    fun setPlans(plans: List<Plan>) {
        this.plans.clear()
        this.plans.addAll(plans)

        notifyDataSetChanged()
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val binding: PlanItemBinding = PlanItemBinding.bind(view)

        init {
            binding.tvPlanDownload.compoundDrawables[0].setTint(Color.parseColor("#016949"))
            binding.tvPlanUpload.compoundDrawables[0].setTint(Color.parseColor("#03489c"))
            binding.tvPlanCapacity.compoundDrawables[0].setTint(Color.parseColor("#F34646"))
        }
    }
}