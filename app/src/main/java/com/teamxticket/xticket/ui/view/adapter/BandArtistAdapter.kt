package com.teamxticket.xticket.ui.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.teamxticket.xticket.R
import com.teamxticket.xticket.ui.view.BandArtist

class BandArtistAdapter(private val bandArtistList: ArrayList<BandArtist>): RecyclerView.Adapter<BandArtistViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BandArtistViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return BandArtistViewHolder(layoutInflater.inflate(R.layout.item_band_artist, parent, false))
    }

    override fun getItemCount(): Int = bandArtistList.size

    override fun onBindViewHolder(holder: BandArtistViewHolder, position: Int) {
        val item = bandArtistList[position]
        holder.render(item)
    }
}