package com.teamxticket.xticket.ui.view

import android.content.Intent
import android.content.res.ColorStateList
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
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
import com.airbnb.lottie.utils.Logger
import com.teamxticket.xticket.R
import com.teamxticket.xticket.core.ActiveUser
import com.teamxticket.xticket.data.model.BandArtistProvider
import com.teamxticket.xticket.data.model.Event
import com.teamxticket.xticket.databinding.ActivityEventDetailBinding
import com.teamxticket.xticket.ui.view.adapter.BandArtistAdapter
import com.teamxticket.xticket.ui.viewModel.EventViewModel

class EventDetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityEventDetailBinding
    private val eventViewModel : EventViewModel by viewModels()
    private var activeUser = ActiveUser.getInstance().getUser()
    private var eventId = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEventDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val adapter = BandArtistAdapter(BandArtistProvider.bandArtistList)
        eventId = intent.getIntExtra("eventId", -1)
        eventViewModel.getEvent(eventId)

        initSpinnerMusicalGenres()
        initRecyclerViewBandsAndArtists(adapter)
        setupValidationOnFocusChange(binding.eventName)
        setupValidationOnFocusChange(binding.eventDescription)
        setupValidationOnFocusChange(binding.eventLocation)
        setupBtnAddBandOrArtist(adapter)
        setupButtons()
        initObservables()
    }

    private fun setupBtnAddBandOrArtist(adapter: BandArtistAdapter) {
        binding.btnAddBandOrArtist.setOnClickListener {
            val bandArtistName = binding.bandOrArtist.text.toString().replace("\\s+".toRegex(), " ").uppercase().trim()

            if (bandArtistName.isEmpty()) {
                setElementView(binding.bandOrArtist, binding.btnAddBandOrArtist, true, getString(R.string.emptyField))

            } else if(BandArtistProvider.bandArtistList.any { it == bandArtistName }) {
                setElementView(binding.bandOrArtist, binding.btnAddBandOrArtist, true, getString(R.string.bandOrArtistAlreadyAdded))

            } else {
                setElementView(binding.bandOrArtist, binding.btnAddBandOrArtist, false, "")
                adapter.addItem(bandArtistName)
                binding.bandOrArtist.text.clear()

            }
        }
    }

    private fun setupButtons() {
        binding.btnSaveEvent.setOnClickListener {
            val eventName = binding.eventName.text.toString().replace("\\s+".toRegex(), " ").uppercase().trim()
            val musicalGenres = binding.musicalGenres
            val eventDescription = binding.eventDescription.text.toString().replace("\\s+".toRegex(), " ").uppercase().trim()
            val eventLocation = binding.eventLocation.text.toString().replace("\\s+".toRegex(), " ").uppercase().trim()
            val bandsAndArtists = BandArtistProvider.bandArtistList

            setElementView(binding.eventName, eventName.isEmpty(), getString(R.string.emptyField))
            setElementView(binding.musicalGenres, (musicalGenres.selectedItemPosition == 0), getString(R.string.emptyField))
            setElementView(binding.eventDescription, eventDescription.isEmpty(), getString(R.string.emptyField))
            setElementView(binding.eventLocation, eventLocation.isEmpty(), getString(R.string.emptyField))
            setElementView(binding.bandOrArtist, binding.btnAddBandOrArtist, bandsAndArtists.isEmpty(), getString(R.string.emptyBandOrArtistList))

            if (eventName.isEmpty() || musicalGenres.selectedItemPosition == 0 || eventDescription.isEmpty() || eventLocation.isEmpty() || bandsAndArtists.isEmpty()) {
                Toast.makeText(this, getString(R.string.emptyFields), Toast.LENGTH_SHORT).show()

            } else {
                val event = Event(eventId, eventName, musicalGenres.selectedItem.toString(), eventDescription, eventLocation, activeUser!!.userId, bandsAndArtists, null, null)
                eventViewModel.updateEvent(event)

            }
        }

        binding.btnManageTickets.setOnClickListener {
            Intent(this, ManageSaleDateActivity::class.java).apply {
                putExtra("eventId", eventId)
                startActivity(this)
            }
        }

        binding.btnRegisterTicketTaker.setOnClickListener {

        }

        binding.btnCancelEvent.setOnClickListener {

        }
    }

    private fun initObservables() {
        eventViewModel.eventModel.observe(this) {
            val event = it?.find { event -> event.eventId == eventId }
            if (event != null) {
                Log.d("event", event.toString())
                binding.eventName.setText(event.name)
                binding.eventDescription.setText(event.description)
                binding.eventLocation.setText(event.location)
                binding.musicalGenres.setSelection((binding.musicalGenres.adapter as ArrayAdapter<String>).getPosition(event.genre))
                for(artist in event.bandsAndArtists!!) {
                    BandArtistProvider.bandArtistList.add(artist)
                }
            }
        }

        eventViewModel.showLoaderRegister.observe(this) { visible ->
            binding.progressBar.isVisible = visible
            binding.overlayView.isVisible = visible
        }

        eventViewModel.successfulUpdate.observe(this) { result ->
            if (result == -1) {
                Toast.makeText(this, getString(R.string.eventWasNotUpdated), Toast.LENGTH_SHORT).show()

            } else {
                Toast.makeText(this, getString(R.string.eventUpdated), Toast.LENGTH_SHORT).show()
                Intent(this, ManageSaleDateActivity::class.java).apply {
                    putExtra("eventId", result)
                    startActivity(this)
                }

            }
        }
    }

    private fun setElementView(editText: EditText, isError: Boolean, message: String) {
        val errorColor = if (isError) Color.RED else Color.BLACK

        editText.apply {
            error = if (isError) message else null
            setHintTextColor(errorColor)
            backgroundTintList = ColorStateList.valueOf(errorColor)
        }
    }

    private fun setElementView(spinner: Spinner, isError: Boolean, message: String) {
        val selectedView = spinner.selectedView as TextView
        val errorColor = if (isError) Color.RED else Color.BLACK
        val backgroundResource = if (isError) R.drawable.spinner_border_on_error else R.drawable.spinner_border

        selectedView.apply {
            error = if (isError) message else null
            setTextColor(errorColor)
        }
        spinner.background = AppCompatResources.getDrawable(this, backgroundResource)
    }

    private fun setElementView(editText: EditText, button: ImageButton, isError: Boolean, message: String) {
        if(isError) {
            editText.apply {
                error = message
                setHintTextColor(Color.RED)
                backgroundTintList = ColorStateList.valueOf(Color.RED)
            }
            button.backgroundTintList = ColorStateList.valueOf(Color.RED)
        } else {
            editText.apply {
                error = null
                setHintTextColor(Color.GRAY)
                backgroundTintList = ColorStateList.valueOf(Color.BLACK)
            }
            button.backgroundTintList = ColorStateList.valueOf(resources.getColor(R.color.darkBlue, null))
        }
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

    override fun onPause() {
        super.onPause()
        BandArtistProvider.bandArtistList.clear()
        finish()
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
                if(position == 0)
                    (binding.musicalGenres.selectedView as TextView).setTextColor(Color.GRAY)
                else
                    (binding.musicalGenres.selectedView as TextView).setTextColor(Color.BLACK)
                binding.musicalGenres.background = AppCompatResources.getDrawable(this@EventDetailActivity, R.drawable.spinner_border)
            }
            override fun onNothingSelected(parent: AdapterView<*>) {

            }
        }
    }

    private fun initRecyclerViewBandsAndArtists(adapter: BandArtistAdapter) {
        binding.recyclerBandsAndArtists.layoutManager = LinearLayoutManager(this)
        binding.recyclerBandsAndArtists.adapter = adapter
    }
}