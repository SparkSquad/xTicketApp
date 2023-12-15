package com.teamxticket.xticket.ui.view.adapter

import android.content.res.Resources
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.teamxticket.xticket.R
import com.teamxticket.xticket.data.model.SaleDate
import com.teamxticket.xticket.databinding.ItemSaleDateBinding
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class SaleDateAdapter(val datesList: List<SaleDate>, val onClickListener: (SaleDate) -> Unit):
    RecyclerView.Adapter<SaleDateAdapter.ViewHolder>() {

    inner class ViewHolder(val binding: ItemSaleDateBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding: ItemSaleDateBinding =
            ItemSaleDateBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return datesList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentSaleDate = datesList[position]
        with(holder.binding) {
            val saleDate = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).parse(currentSaleDate.saleDate)
            val calendar = Calendar.getInstance()
            if (saleDate != null) {
                calendar.time = saleDate
            }

            val month = SimpleDateFormat("MMM", Locale("es", "ES")).format(calendar.time).uppercase()
            val day = calendar.get(Calendar.DAY_OF_MONTH).toString()

            tvSaleMonth.text = month
            tvSaleDay.text = day

            val startTime = SimpleDateFormat("HH:mm:ss", Locale.getDefault()).parse(currentSaleDate.startTime)
            val endTime = SimpleDateFormat("HH:mm:ss", Locale.getDefault()).parse(currentSaleDate.endTime)

            val startTimeFormatted = SimpleDateFormat("HH:mm", Locale.getDefault()).format(startTime!!)
            val endTimeFormatted = SimpleDateFormat("HH:mm", Locale.getDefault()).format(endTime!!)

            tvStartHour.text = startTimeFormatted
            tvEndHour.text = endTimeFormatted


            tvPrice.text = buildString {
                append("Precio: ")
                append(currentSaleDate.price.toString())
            }

            tvTotalTickets.text = buildString {
                append("Entradas disponibles: ")
                append(currentSaleDate.tickets.toString())
            }

            if (currentSaleDate.adults == 1) {
                tvOnlyAdults.visibility = ViewGroup.VISIBLE
            }

            tvMaxTickets.text = buildString {
                append("Maximo de tickets: ")
                append(currentSaleDate.maxTickets.toString())
            }

            holder.binding.root.setOnClickListener {
                onClickListener(currentSaleDate)
            }
        }
    }
}
