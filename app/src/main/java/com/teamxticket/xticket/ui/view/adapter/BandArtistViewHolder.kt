package com.teamxticket.xticket.ui.view.adapter

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.teamxticket.xticket.databinding.ItemBandArtistBinding
import com.teamxticket.xticket.ui.view.BandArtist

class BandArtistViewHolder(view: View): RecyclerView.ViewHolder(view) {
    private val binding = ItemBandArtistBinding.bind(view)

    fun render(bandArtist: BandArtist) {
        binding.buttonBandArtist.text = bandArtist.name
    }
}