package com.teamxticket.xticket.ui.view.adapter

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.teamxticket.xticket.databinding.ItemBandArtistBinding
import com.teamxticket.xticket.data.model.BandArtist

class BandArtistViewHolder(view: View): RecyclerView.ViewHolder(view) {
    private val binding = ItemBandArtistBinding.bind(view)
    private lateinit var adapter: BandArtistAdapter

    fun render(bandArtist: BandArtist) {
        binding.buttonBandArtist.text = bandArtist.name
        binding.buttonBandArtist.setOnClickListener {
            adapter.removeItem(absoluteAdapterPosition)
        }
    }

    fun linkAdapter(adapter: BandArtistAdapter) {
        this.adapter = adapter
    }
}