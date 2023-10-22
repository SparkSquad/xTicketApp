package com.teamxticket.xticket.ui.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.teamxticket.xticket.R
import com.teamxticket.xticket.databinding.ActivityCreateEventBinding
import com.teamxticket.xticket.ui.view.adapter.BandArtistAdapter

class CreateEvent : AppCompatActivity() {
    private lateinit var binding: ActivityCreateEventBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCreateEventBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initRecyclerView()

        binding.btnAddBandOrArtist.setOnClickListener {
            val bandArtistName = binding.bandOrArtist.text.toString()
            if (bandArtistName.isNotEmpty()) {
                BandArtistProvider.bandArtistList.add(BandArtist(bandArtistName))
                binding.recyclerBandsAndArtists.adapter?.notifyItemInserted(BandArtistProvider.bandArtistList.size - 1)
                binding.bandOrArtist.text.clear()
            } else {
                Toast.makeText(this, getString(R.string.emptyBandOrArtist), Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun initRecyclerView() {
        binding.recyclerBandsAndArtists.layoutManager = LinearLayoutManager(this)
        binding.recyclerBandsAndArtists.adapter = BandArtistAdapter(BandArtistProvider.bandArtistList)
    }
}