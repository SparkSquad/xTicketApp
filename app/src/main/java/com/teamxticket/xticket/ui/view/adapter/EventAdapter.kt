package com.teamxticket.xticket.ui.view.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.teamxticket.xticket.R
import com.teamxticket.xticket.data.model.Event
import java.lang.Math.abs
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

class EventAdapter(private var eventList: MutableList<Event>, private val onItemClick: (Event) -> Unit): RecyclerView.Adapter<EventViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EventViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return EventViewHolder(layoutInflater.inflate(R.layout.item_event, parent, false))
    }

    override fun getItemCount(): Int = eventList.size

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: EventViewHolder, position: Int) {
        val item = eventList[position]
        holder.render(item)
        holder.itemView.setOnClickListener { onItemClick(item) }

        with(holder.binding) {
            if(item.saleDates?.isEmpty() == true) {
                tvSaleMonth.text = "---"
                tvSaleDay.text = "--"
            }
            else {
                val currentDate = Date()
                val formatter = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
                val sortedDates = item.saleDates?.sortedBy { kotlin.math.abs(formatter.parse(it.saleDate).time - currentDate.time) }

                val calendar = Calendar.getInstance()
                if (!sortedDates.isNullOrEmpty()) {
                    calendar.time = formatter.parse(sortedDates.first().saleDate)!!
                }
                val month = SimpleDateFormat("MMM", Locale("es", "ES")).format(calendar.time).uppercase()
                val day = calendar.get(Calendar.DAY_OF_MONTH).toString()

                tvSaleMonth.text = month
                tvSaleDay.text = day

                eventPrice.text = sortedDates?.first()!!.price.toString()
            }
        }
    }
}
