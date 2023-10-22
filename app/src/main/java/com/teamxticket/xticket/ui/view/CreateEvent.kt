package com.teamxticket.xticket.ui.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.datepicker.CalendarConstraints
import com.google.android.material.datepicker.DateValidatorPointForward
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.timepicker.MaterialTimePicker
import com.google.android.material.timepicker.TimeFormat
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

        binding.btnPickDate.setOnClickListener {
            val constraintsBuilder = CalendarConstraints.Builder().setValidator(
                DateValidatorPointForward.now())
            val datePicker = MaterialDatePicker.Builder.datePicker()
                .setTitleText(resources.getString(R.string.eventDate))
                .setSelection(MaterialDatePicker.todayInUtcMilliseconds())
                .setCalendarConstraints(constraintsBuilder.build())
                .build().apply {
                    addOnPositiveButtonClickListener {
                        val selectedDate = this.headerText
                        binding.eventDate.text = selectedDate
                    }
                }

            datePicker.show(supportFragmentManager, "Fecha del evento")
        }

        binding.btnPickDuration.setOnClickListener {
            val timePicker = MaterialTimePicker.Builder()
                .setTimeFormat(TimeFormat.CLOCK_24H)
                .setHour(0)
                .setMinute(0)
                .setTitleText(resources.getString(R.string.eventDuration))
                .build().apply {
                    addOnPositiveButtonClickListener {

                        val hours = if (this.hour < 10)
                            "0${this.hour}"
                        else
                            this.hour.toString()

                        val minutes = if (this.minute < 10)
                            "0${this.minute}"
                        else
                            this.minute.toString()

                        val selectedTime = "${hours}:${minutes} horas"

                        if (this.hour < 2)
                            Toast.makeText(this@CreateEvent, resources.getString(R.string.minDuration), Toast.LENGTH_SHORT).show()
                        else
                            binding.eventDuration.text = selectedTime
                    }
                }

            timePicker.show(supportFragmentManager, "Duración del evento")
        }
    }

    private fun initRecyclerView() {
        binding.recyclerBandsAndArtists.layoutManager = LinearLayoutManager(this)
        binding.recyclerBandsAndArtists.adapter = BandArtistAdapter(BandArtistProvider.bandArtistList)
    }
}