package com.teamxticket.xticket.ui.view.adapter

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.teamxticket.xticket.databinding.ItemBandArtistBinding

class BandArtistViewHolder(view: View): RecyclerView.ViewHolder(view) {
    private val binding = ItemBandArtistBinding.bind(view)
    private lateinit var adapter: BandArtistAdapter

    fun render(bandArtist: String) {
        binding.buttonBandArtist.text = bandArtist
        binding.buttonBandArtist.setOnClickListener {
            adapter.removeItem(absoluteAdapterPosition)
        }
    }

    fun linkAdapter(adapter: BandArtistAdapter) {
        this.adapter = adapter
    }
}