package com.teamxticket.xticket.ui.view.adapter

import android.content.DialogInterface.OnClickListener
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.teamxticket.xticket.data.model.SaleDate
import com.teamxticket.xticket.databinding.ItemSaleDateBinding


class SaleDateAdapter(val datesList: List<SaleDate>, val onClickListener: (SaleDate) -> Unit): RecyclerView.Adapter<SaleDateAdapter.ViewHolder>() {

    inner class ViewHolder(val binding: ItemSaleDateBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding: ItemSaleDateBinding = ItemSaleDateBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return datesList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentSaleDate = datesList[position]
        with(holder.binding) {
            tvPrice.text = currentSaleDate.price.toString()
            tvStartHour.text = currentSaleDate.startTime
            tvEndHour.text = currentSaleDate.endTime
            tvSaleMonth.text = currentSaleDate.eventDate

            holder.binding.root.setOnClickListener {
                onClickListener(currentSaleDate)
            }
        }
    }


}