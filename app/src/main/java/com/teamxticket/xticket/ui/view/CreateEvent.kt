package com.teamxticket.xticket.ui.view

import android.content.res.ColorStateList
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.ImageButton
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import com.teamxticket.xticket.R
import com.teamxticket.xticket.data.model.BandArtist
import com.teamxticket.xticket.data.model.Event
import com.teamxticket.xticket.databinding.ActivityCreateEventBinding
import com.teamxticket.xticket.ui.view.adapter.BandArtistAdapter
import com.teamxticket.xticket.ui.viewModel.EventViewModel

class CreateEvent : AppCompatActivity() {
    private lateinit var binding: ActivityCreateEventBinding
    private val eventViewModel : EventViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCreateEventBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val adapter = BandArtistAdapter(BandArtistProvider.bandArtistList)

        initSpinnerMusicalGenres()
        initRecyclerViewBandsAndArtists(adapter)
        setupValidationOnFocusChange(binding.eventName)
        setupValidationOnFocusChange(binding.eventDescription)
        setupValidationOnFocusChange(binding.eventLocation)
        setupBtnAddBandOrArtist(adapter)
        setupBtnCreateEvent()
        initObservables()
    }

    private fun setupBtnAddBandOrArtist(adapter: BandArtistAdapter) {
        binding.btnAddBandOrArtist.setOnClickListener {
            val bandArtistName = binding.bandOrArtist.text.toString().replace("\\s+".toRegex(), " ").uppercase().trim()

            if (bandArtistName.isEmpty()) {
                setElementView(binding.bandOrArtist, binding.btnAddBandOrArtist, true, getString(R.string.emptyField))

            } else if(BandArtistProvider.bandArtistList.any { it.name == bandArtistName }) {
                setElementView(binding.bandOrArtist, binding.btnAddBandOrArtist, true, getString(R.string.bandOrArtistAlreadyAdded))

            } else {
                setElementView(binding.bandOrArtist, binding.btnAddBandOrArtist, false, "")
                adapter.addItem(BandArtist(bandArtistName))
                binding.bandOrArtist.text.clear()

            }
        }
    }

    private fun setupBtnCreateEvent() {
        binding.btnCreateEvent.setOnClickListener {
            val eventName = binding.eventName.text.toString().replace("\\s+".toRegex(), " ").uppercase().trim()
            val musicalGenre = binding.musicalGenres.selectedItemPosition
            val eventDescription = binding.eventDescription.text.toString().replace("\\s+".toRegex(), " ").uppercase().trim()
            val eventLocation = binding.eventLocation.text.toString().replace("\\s+".toRegex(), " ").uppercase().trim()
            val bandAndArtists = BandArtistProvider.bandArtistList

            setElementView(binding.eventName, eventName.isEmpty(), getString(R.string.emptyField))
            setElementView(binding.musicalGenres, (musicalGenre == 0), getString(R.string.emptyField))
            setElementView(binding.eventDescription, eventDescription.isEmpty(), getString(R.string.emptyField))
            setElementView(binding.eventLocation, eventLocation.isEmpty(), getString(R.string.emptyField))
            setElementView(binding.bandOrArtist, binding.btnAddBandOrArtist, bandAndArtists.isEmpty(), getString(R.string.emptyBandOrArtistList))

            if (eventName.isEmpty() || musicalGenre == 0 || eventDescription.isEmpty() || eventLocation.isEmpty() || bandAndArtists.isEmpty()) {
                Toast.makeText(this, getString(R.string.emptyFields), Toast.LENGTH_SHORT).show()

            } else {

                // TODO: Mandar id de usuario usando Shingleton

                val event = Event(0, eventName, "ROCK", eventDescription, eventLocation, 1, bandAndArtists)
                eventViewModel.registerEvent(event)

                BandArtistProvider.bandArtistList.clear()
                Toast.makeText(this, getString(R.string.eventCreated), Toast.LENGTH_SHORT).show()
                finish()

            }
        }
    }

    private fun initObservables() {
        eventViewModel.showLoaderRegister.observe(this) { visible ->
            binding.progressBar.isVisible = visible
            binding.overlayView.isVisible = visible
        }

        eventViewModel.successfulRegister.observe(this) { successful ->
            if (successful == 200) {
                Toast.makeText(this, "Evento registrado exitosamente", Toast.LENGTH_SHORT).show()
                finish()
            } else {
                Toast.makeText(this, "Error al registrar el evento", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun setElementView(editText: EditText, isError: Boolean, message: String) {
        val errorColor = if (isError) Color.RED else Color.BLACK

        editText.error = if (isError) message else null
        editText.setHintTextColor(errorColor)
        editText.backgroundTintList = ColorStateList.valueOf(errorColor)
    }

    private fun setElementView(spinner: Spinner, isError: Boolean, message: String) {
        val selectedView = spinner.selectedView as TextView
        val errorColor = if (isError) Color.RED else Color.BLACK
        val backgroundResource = if (isError) R.drawable.spinner_border_on_error else R.drawable.spinner_border

        selectedView.error = if (isError) message else null
        selectedView.setTextColor(errorColor)
        spinner.background = AppCompatResources.getDrawable(this, backgroundResource)
    }

    private fun setElementView(editText: EditText, button: ImageButton, isError: Boolean, message: String) {
        val errorColor = if (isError) Color.RED else Color.BLACK

        editText.error = if (isError) message else null
        editText.setHintTextColor(if (!isError) Color.GRAY else errorColor)
        editText.backgroundTintList = ColorStateList.valueOf(errorColor)
        button.backgroundTintList = ColorStateList.valueOf(errorColor)
    }

    private fun setupValidationOnFocusChange(editText: EditText) {
        editText.onFocusChangeListener = View.OnFocusChangeListener { _, hasFocus ->
            if (!hasFocus) {
                val inputText = editText.text.toString().replace("\\s+".toRegex(), " ").uppercase().trim()
                setElementView(editText, inputText.isEmpty(), getString(R.string.emptyField))
            }
        }
    }

    override fun onResume() {
        super.onResume()
        eventViewModel.loadGenres()
        initSpinnerMusicalGenres()
    }

    private fun initSpinnerMusicalGenres() {
        val musicalGenres: ArrayList<String> = ArrayList()
        musicalGenres.add(getString(R.string.pickMusicalGenre))

        eventViewModel.genresModel.observe(this) { genres ->
            genres?.forEach { musicalGenres.add(it) }
        }
        eventViewModel.showLoaderGenres.observe(this) { visible ->
            binding.progressBar.isVisible = visible
            binding.overlayView.isVisible = visible
        }

        val arrayAdapter = ArrayAdapter(this, R.layout.spinner_item, musicalGenres)
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        binding.musicalGenres.adapter = arrayAdapter

        binding.musicalGenres.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
                (binding.musicalGenres.selectedView as TextView).error = null
                binding.musicalGenres.background = AppCompatResources.getDrawable(this@CreateEvent, R.drawable.spinner_border)
            }
            override fun onNothingSelected(parent: AdapterView<*>) {
                (binding.musicalGenres.selectedView as TextView).error = getString(R.string.emptyField)
                binding.musicalGenres.background = AppCompatResources.getDrawable(this@CreateEvent, R.drawable.spinner_border_on_error)
            }
        }
    }

    private fun initRecyclerViewBandsAndArtists(adapter: BandArtistAdapter) {
        binding.recyclerBandsAndArtists.layoutManager = LinearLayoutManager(this)
        binding.recyclerBandsAndArtists.adapter = adapter
    }
}