package com.teamxticket.xticket.ui.view

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.content.res.ColorStateList
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Gravity
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
import com.teamxticket.xticket.core.ActiveUser
import com.teamxticket.xticket.data.model.BandArtistProvider
import com.teamxticket.xticket.data.model.Event
import com.teamxticket.xticket.databinding.ActivityCreateEventBinding
import com.teamxticket.xticket.ui.view.adapter.BandArtistAdapter
import com.teamxticket.xticket.ui.viewModel.EventViewModel
import com.thecode.aestheticdialogs.AestheticDialog
import com.thecode.aestheticdialogs.DialogAnimation
import com.thecode.aestheticdialogs.DialogStyle
import com.thecode.aestheticdialogs.DialogType
import com.thecode.aestheticdialogs.OnDialogClickListener
import java.util.UUID

class CreateEventActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCreateEventBinding
    private val eventViewModel : EventViewModel by viewModels()
    private var activeUser = ActiveUser.getInstance().getUser()
    private var darkDialog = ActiveUser.getInstance().getDarkMode()
    private lateinit var preferences: SharedPreferences
    private lateinit var ticketTakerCode: String


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCreateEventBinding.inflate(layoutInflater)
        setContentView(binding.root)

        preferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
        val savedCode = preferences.getString("ticketTakerCode", null)
        val eventId = preferences.getInt("eventId", 0)
        ticketTakerCode = savedCode ?: generateRandomCode(eventId)
        binding.etTicketTakerCode.text.clear()
        binding.etTicketTakerCode.setText(ticketTakerCode)

        if (savedCode == null) {
            preferences.edit().putString("ticketTakerCode", ticketTakerCode).apply()
        }

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

            } else if(BandArtistProvider.bandArtistList.any { it == bandArtistName }) {
                setElementView(binding.bandOrArtist, binding.btnAddBandOrArtist, true, getString(R.string.bandOrArtistAlreadyAdded))

            } else {
                setElementView(binding.bandOrArtist, binding.btnAddBandOrArtist, false, "")
                adapter.addItem(bandArtistName)
                binding.bandOrArtist.text.clear()

            }
        }
    }

    private fun setupBtnCreateEvent() {

        binding.btnCreateEvent.setOnClickListener {
            val eventName = binding.eventName.text.toString().replace("\\s+".toRegex(), " ").uppercase().trim()
            val musicalGenres = binding.musicalGenres
            val eventDescription = binding.eventDescription.text.toString().replace("\\s+".toRegex(), " ").uppercase().trim()
            val eventLocation = binding.eventLocation.text.toString().replace("\\s+".toRegex(), " ").uppercase().trim()
            val bandsAndArtists = BandArtistProvider.bandArtistList
            val eventId = preferences.getInt("eventId", 0)
            ticketTakerCode = generateRandomCode(eventId)


            setElementView(binding.eventName, eventName.isEmpty(), getString(R.string.emptyField))
            setElementView(binding.musicalGenres, (musicalGenres.selectedItemPosition == 0), getString(R.string.emptyField))
            setElementView(binding.eventDescription, eventDescription.isEmpty(), getString(R.string.emptyField))
            setElementView(binding.eventLocation, eventLocation.isEmpty(), getString(R.string.emptyField))
            setElementView(binding.bandOrArtist, binding.btnAddBandOrArtist, bandsAndArtists.isEmpty(), getString(R.string.emptyBandOrArtistList))

            if (eventName.isEmpty() || musicalGenres.selectedItemPosition == 0 || eventDescription.isEmpty() || eventLocation.isEmpty() || bandsAndArtists.isEmpty()) {
                Toast.makeText(this, getString(R.string.emptyFields), Toast.LENGTH_SHORT).show()

            } else {
                val event = Event(0, eventName, musicalGenres.selectedItem.toString(), eventDescription, eventLocation, activeUser!!.userId, ticketTakerCode, bandsAndArtists, null, null, null)
                eventViewModel.registerEvent(event)
                
            }
        }
    }

    private fun initObservables() {
        eventViewModel.showLoaderRegister.observe(this) { visible ->
            binding.progressBar.isVisible = visible
            binding.overlayView.isVisible = visible
        }

        eventViewModel.successfulRegister.observe(this) { result ->
            if (result == -1) {
                AestheticDialog.Builder(this, DialogStyle.FLAT, DialogType.ERROR)
                    .setTitle(getString(R.string.failure))
                    .setMessage(getString(R.string.eventWasNotCreated))
                    .setCancelable(true)
                    .setDarkMode(darkDialog)
                    .setGravity(Gravity.CENTER)
                    .setAnimation(DialogAnimation.SHRINK)
                    .show()
            } else {
                AestheticDialog.Builder(this, DialogStyle.FLAT, DialogType.SUCCESS)
                    .setTitle(getString(R.string.success))
                    .setMessage(getString(R.string.eventCreated))
                    .setCancelable(true)
                    .setDarkMode(darkDialog)
                    .setGravity(Gravity.CENTER)
                    .setAnimation(DialogAnimation.SHRINK)
                    .setOnClickListener(object : OnDialogClickListener {
                        override fun onClick(dialog: AestheticDialog.Builder) {
                            dialog.dismiss()
                            Intent(this@CreateEventActivity, ManageSaleDateActivity::class.java).apply {
                                putExtra("eventId", result)
                                startActivity(this)
                            }
                            finish()
                        }
                    })
                    .show()
            }
        }
    }

    private fun setElementView(editText: EditText, isError: Boolean, message: String) {
        val darkMode = ActiveUser.getInstance().getDarkMode()
        val errorColor = if (isError) Color.RED else (if (darkMode) Color.WHITE else Color.BLACK)
        editText.apply {
            error = if (isError) message else null
            setHintTextColor(errorColor)
            backgroundTintList = ColorStateList.valueOf(errorColor)
        }
    }

    private fun setElementView(spinner: Spinner, isError: Boolean, message: String) {
        val darkMode = ActiveUser.getInstance().getDarkMode()
        val selectedView = spinner.selectedView as TextView
        val errorColor = if (isError) Color.RED else (if (darkMode) Color.WHITE else Color.BLACK)
        val backgroundResource = if (isError) R.drawable.spinner_border_on_error else (if (darkMode) R.drawable.spinner_border_dark else R.drawable.spinner_border )

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
                    (binding.musicalGenres.selectedView as TextView).setTextColor(if (ActiveUser.getInstance().getDarkMode()) Color.WHITE else Color.BLACK)
                binding.musicalGenres.background = AppCompatResources.getDrawable(this@CreateEventActivity, (if (ActiveUser.getInstance().getDarkMode()) R.drawable.spinner_border_dark else R.drawable.spinner_border ))
            }
            override fun onNothingSelected(parent: AdapterView<*>) {

            }
        }
    }

    private fun initRecyclerViewBandsAndArtists(adapter: BandArtistAdapter) {
        binding.recyclerBandsAndArtists.layoutManager = LinearLayoutManager(this)
        binding.recyclerBandsAndArtists.adapter = adapter
    }

    private fun generateRandomCode(eventId: Int): String {
        val allowedChars = ('A'..'Z') + ('0'..'9')
        val randomCode = (1..10)
            .map { allowedChars.random() }
            .joinToString("")
        return "EVENT${eventId}$randomCode"
    }
}
