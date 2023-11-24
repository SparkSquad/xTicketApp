package com.teamxticket.xticket.ui.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.teamxticket.xticket.R
import com.teamxticket.xticket.data.model.User

class EventPlannerAdapter(private var eventPlannerList: MutableList<User>,
                          private val onItemClick: (User) -> Unit):
              RecyclerView.Adapter<EventPlannerViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EventPlannerViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return EventPlannerViewHolder(layoutInflater.inflate(R.layout.item_event_planner, parent, false))
    }

    override fun onBindViewHolder(holder: EventPlannerViewHolder, position: Int) {
        val item = eventPlannerList[position]
        holder.render(item)
        holder.itemView.setOnClickListener { onItemClick(item) }
    }

    override fun getItemCount(): Int = eventPlannerList.size
}

