package com.teamxticket.xticket.ui.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.teamxticket.xticket.databinding.ActivityCreateEventBinding
import com.teamxticket.xticket.ui.view.adapter.BandArtistAdapter

class CreateEvent : AppCompatActivity() {
    private lateinit var binding: ActivityCreateEventBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCreateEventBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initRecyclerView()
    }

    private fun initRecyclerView() {
        binding.recyclerBandsAndArtists.layoutManager = LinearLayoutManager(this)
        binding.recyclerBandsAndArtists.adapter = BandArtistAdapter(BandArtistProvider.bandArtistList)
    }
}