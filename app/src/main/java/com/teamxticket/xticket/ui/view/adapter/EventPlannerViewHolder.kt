package com.teamxticket.xticket.ui.view.adapter

import android.annotation.SuppressLint
import android.provider.Settings.Global.getString
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.teamxticket.xticket.R
import com.teamxticket.xticket.data.model.User
import com.teamxticket.xticket.databinding.ItemEventPlannerBinding

public class EventPlannerViewHolder(view: View): RecyclerView.ViewHolder(view) {

    private val binding = ItemEventPlannerBinding.bind(view)
    private lateinit var adapter: EventPlannerAdapter
    fun render(eventPlanner: User) {
        binding.tvEventPlanner.text = eventPlanner.name + " " + eventPlanner.surnames
        if(eventPlanner.disabled) {
            binding.tvEventPlanner.text = "DESHABILITADO"
        } else {
            binding.tvEventPlanner.text = "HABILITADO"
        }
    }
    fun linkAdapter(adapter: EventPlannerAdapter) {
        this.adapter = adapter
    }

}