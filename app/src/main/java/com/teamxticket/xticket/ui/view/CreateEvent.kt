package com.teamxticket.xticket.ui.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
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

        val adapter = BandArtistAdapter(BandArtistProvider.bandArtistList)

        initSpinnerMusicalGenres()
        initRecyclerViewBandsAndArtists(adapter)

        binding.btnAddBandOrArtist.setOnClickListener {
            var bandArtistName = binding.bandOrArtist.text.toString()
            bandArtistName = bandArtistName.replace("\\s+".toRegex(), " ").uppercase().trim()

            if (bandArtistName.isNotEmpty()) {
                if(BandArtistProvider.bandArtistList.isEmpty()) {
                    adapter.addItem(BandArtist(bandArtistName))
                    binding.bandOrArtist.text.clear()

                } else {
                    var sameBandArtist = false
                    for(bandArtist in BandArtistProvider.bandArtistList) {
                        if (bandArtist.name == bandArtistName) {
                            sameBandArtist = true
                            break

                        }
                    }

                    if (sameBandArtist) {
                        Toast.makeText(this, getString(R.string.bandOrArtistAlreadyAdded), Toast.LENGTH_SHORT).show()

                    } else {
                        adapter.addItem(BandArtist(bandArtistName))
                        binding.bandOrArtist.text.clear()

                    }
                }
            } else {
                Toast.makeText(this, getString(R.string.emptyBandOrArtist), Toast.LENGTH_SHORT).show()

            }
        }
    }

    private fun initSpinnerMusicalGenres() {
        val musicalGenres: ArrayList<String> = ArrayList()
        musicalGenres.add(getString(R.string.pickMusicalGenre))

        val arrayAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, musicalGenres)
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        binding.musicalGenres.adapter = arrayAdapter
    }

    private fun initRecyclerViewBandsAndArtists(adapter: BandArtistAdapter) {
        binding.recyclerBandsAndArtists.layoutManager = LinearLayoutManager(this)
        binding.recyclerBandsAndArtists.adapter = adapter
    }
}