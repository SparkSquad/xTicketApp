package com.teamxticket.xticket.ui.view.adapter

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.teamxticket.xticket.data.model.Event
import com.teamxticket.xticket.databinding.ItemEventBinding

class EventViewHolder(view: View): RecyclerView.ViewHolder(view) {
    private val binding = ItemEventBinding.bind(view)

    fun render(event: Event) {
        binding.eventName.text = event.name
        binding.eventGenre.text = event.genre
        binding.eventLocation.text = event.location
    }
}
