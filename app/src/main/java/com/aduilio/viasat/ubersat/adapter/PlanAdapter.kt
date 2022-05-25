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
        const val TYPE_CABLE = "cable"
        const val TYPE_RADIO = "radio"
        const val TYPE_SATELLITE = "sat"
        const val TYPE_WIRE = "wire"

        const val COMPOUND_DRAWABLE_LEFT_INDEX = 0
    }

    private val plans: MutableList<Plan> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.plan_item, parent, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        val context = viewHolder.itemView.context

        plans[position].apply {
            viewHolder.binding.tvPlanName.text = isp

            dataCapacity?.let {
                viewHolder.binding.tvPlanCapacity.text =
                    String.format(context.getString(R.string.data_capacity_value), dataCapacity)
            } ?: run {
                viewHolder.binding.tvPlanCapacity.text = context.getString(R.string.unlimited)
            }

            when (typeOfInternet) {
                TYPE_CABLE -> viewHolder.binding.ivPlanType.setImageResource(R.drawable.ic_cable)
                TYPE_RADIO -> viewHolder.binding.ivPlanType.setImageResource(R.drawable.ic_radio)
                TYPE_SATELLITE -> viewHolder.binding.ivPlanType.setImageResource(R.drawable.ic_satellite)
                TYPE_WIRE -> viewHolder.binding.ivPlanType.setImageResource(R.drawable.ic_wire)
            }

            viewHolder.binding.tvPlanDownload.text =
                String.format(context.getString(R.string.download_speed_value), downloadSpeed)
            viewHolder.binding.tvPlanUpload.text =
                String.format(context.getString(R.string.upload_speed_value), uploadSpeed)
            viewHolder.binding.tvPlanPrice.text =
                String.format(context.getString(R.string.price_value), pricePerMonth)

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
            binding.tvPlanDownload.compoundDrawables[COMPOUND_DRAWABLE_LEFT_INDEX].setTint(
                Color.parseColor(
                    "#016949"
                )
            )
            binding.tvPlanUpload.compoundDrawables[COMPOUND_DRAWABLE_LEFT_INDEX].setTint(
                Color.parseColor(
                    "#03489c"
                )
            )
            binding.tvPlanCapacity.compoundDrawables[COMPOUND_DRAWABLE_LEFT_INDEX].setTint(
                Color.parseColor(
                    "#F57C00"
                )
            )
        }
    }
}