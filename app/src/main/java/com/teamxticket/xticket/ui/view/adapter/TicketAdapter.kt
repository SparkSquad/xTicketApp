package com.teamxticket.xticket.ui.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.teamxticket.xticket.data.model.TicketData
import com.teamxticket.xticket.databinding.ItemTicketBinding
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class TicketAdapter(val ticketList: List<TicketData>, val onClickListener: (TicketData) -> Unit ):
    RecyclerView.Adapter<TicketAdapter.ViewHolder>() {

    inner class ViewHolder(val binding: ItemTicketBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding: ItemTicketBinding = ItemTicketBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        print(ticketList.size)
        return ticketList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentTicket = ticketList[position]
        with(holder.binding) {
            tvEventName.text = currentTicket.eventName

            val saleDate = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).parse(currentTicket.saleDate.saleDate)
            val calendar = Calendar.getInstance()
            if (saleDate != null) {
                calendar.time = saleDate
            }
            val month = SimpleDateFormat("MMM", Locale("es", "ES")).format(calendar.time).uppercase()
            val day = calendar.get(Calendar.DAY_OF_MONTH).toString()
            tvSaleMonth.text = month
            tvSaleDay.text = day

            val startTime = SimpleDateFormat(
                "HH:mm:ss",
                Locale.getDefault()
            ).parse(currentTicket.saleDate.startTime)
            val endTime = SimpleDateFormat("HH:mm:ss", Locale.getDefault()).parse(currentTicket.saleDate.endTime)
            val startTimeFormatted = SimpleDateFormat("HH:mm", Locale.getDefault()).format(startTime!!)
            val endTimeFormatted = SimpleDateFormat("HH:mm", Locale.getDefault()).format(endTime!!)
            tvStartHour.text = startTimeFormatted
            tvEndHour.text = endTimeFormatted

            tvPrice.text = "Precio: " + currentTicket.ticket.price.toString()
            tvTicketPurchased.text = "Boletos comprados: " + currentTicket.ticket.totalTickets.toString()
            if (currentTicket.saleDate.adults == 1) {
                tvOnlyAdults.visibility = ViewGroup.VISIBLE
            }
            tvPurchaseDate.text = "Fecha de compra: " + currentTicket.ticket.purchaseDate

            holder.binding.root.setOnClickListener {
                onClickListener(currentTicket)
            }
        }
    }
}