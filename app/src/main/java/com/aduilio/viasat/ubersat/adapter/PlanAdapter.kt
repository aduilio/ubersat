package com.aduilio.viasat.ubersat.adapter

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.aduilio.viasat.ubersat.R
import com.aduilio.viasat.ubersat.databinding.PlanItemBinding

class PlanAdapter : RecyclerView.Adapter<PlanAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.plan_item, parent, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        if (position %2 == 0){
            viewHolder.binding.tvPlanName.text = "Plan Name"
        } else {
            viewHolder.binding.tvPlanName.text = "Plan Name with large value to test"
        }

        if (position %3 == 0){
            viewHolder.binding.tvPlanCapacity.visibility = View.VISIBLE
            viewHolder.binding.tvPlanCapacity.text = "300MB"
        }else{
            viewHolder.binding.tvPlanCapacity.visibility = View.INVISIBLE
        }

        if (position %4 == 0){
            viewHolder.binding.ivPlanType.setImageResource(R.drawable.ic_satellite)
        } else if (position %3 == 0){
            viewHolder.binding.ivPlanType.setImageResource(R.drawable.ic_cable)
        } else if (position %2 == 0){
            viewHolder.binding.ivPlanType.setImageResource(R.drawable.ic_wire)
        } else {
            viewHolder.binding.ivPlanType.setImageResource(R.drawable.ic_radio)
        }

        viewHolder.binding.tvPlanDownload.text = "100MB"
        viewHolder.binding.tvPlanUpload.text = "30MB"
        viewHolder.binding.tvPlanPrice.text = "$100"
    }

    override fun getItemCount() = 10

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val binding: PlanItemBinding = PlanItemBinding.bind(view)

        init {
            binding.tvPlanDownload.compoundDrawables[0].setTint(Color.parseColor("#016949"))
            binding.tvPlanUpload.compoundDrawables[0].setTint(Color.parseColor("#03489c"))
            binding.tvPlanCapacity.compoundDrawables[0].setTint(Color.parseColor("#F34646"))
        }
    }
}