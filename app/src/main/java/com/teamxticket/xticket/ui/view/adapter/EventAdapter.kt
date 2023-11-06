package com.teamxticket.xticket.ui.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.teamxticket.xticket.R
import com.teamxticket.xticket.data.model.Event

class EventAdapter(private var eventList: MutableList<Event>, private val onItemClick: (Event) -> Unit): RecyclerView.Adapter<EventViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EventViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return EventViewHolder(layoutInflater.inflate(R.layout.item_event, parent, false))
    }

    override fun getItemCount(): Int = eventList.size

    override fun onBindViewHolder(holder: EventViewHolder, position: Int) {
        val item = eventList[position]
        holder.render(item)
        holder.itemView.setOnClickListener { onItemClick(item) }
    }
}