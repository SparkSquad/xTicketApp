package com.teamxticket.xticket.ui.view

import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import com.google.android.material.datepicker.CalendarConstraints
import com.google.android.material.datepicker.DateValidatorPointForward
import com.google.android.material.datepicker.MaterialDatePicker
import com.teamxticket.xticket.R
import com.teamxticket.xticket.data.model.NewSaleDate
import com.teamxticket.xticket.databinding.ActivityRegisterSaleDateBinding
import com.teamxticket.xticket.ui.viewModel.SaleDateViewModel
import java.sql.Date
import java.text.SimpleDateFormat
import java.util.Locale

class RegisterSaleDateActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegisterSaleDateBinding
    private lateinit var newSaleDate : NewSaleDate
    private val saleDateViewModel : SaleDateViewModel by viewModels()
    private var eventId : Int = 0
    private var selectedDate: Date = Date(0)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterSaleDateBinding.inflate(layoutInflater)
        setContentView(binding.root)

        eventId = intent.getIntExtra("eventId", 0)
        initListeners()
        initObservables()
    }

    private fun initObservables() {
        saleDateViewModel.showLoader.observe(this) { visible ->
            binding.progressBar.isVisible = visible
            binding.overlayView.isVisible = visible
        }

        saleDateViewModel.successfulRegister.observe(this) { successful ->
            if (successful) {
                Toast.makeText(this, "Fecha de venta registrada exitosamente", Toast.LENGTH_SHORT).show()
                finish()
            } else {
                Toast.makeText(this, "Error al registrar la fecha de venta", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun initListeners() {
        binding.btnCreateSaleDate.setOnClickListener {
            if (validateForm()) {
                val startTime = binding.tpStart.hour.toString() + ":" + binding.tpStart.minute.toString()
                val endTime = binding.tpEnd.hour.toString() + ":" + binding.tpEnd.minute.toString()
                val numberOfTickets = binding.etNumberOfTickets.text.toString().toInt()
                val price = binding.etPrice.text.toString().toDouble()
                val maxTickets = binding.etMaxTickets.text.toString().toInt()
                val adults = if (binding.switchOnlyAdults.isActivated) 1 else 0

                newSaleDate = NewSaleDate(
                    selectedDate,
                    price,
                    numberOfTickets,
                    maxTickets,
                    adults,
                    startTime,
                    endTime,
                    eventId
                )

                saleDateViewModel.registerSaleDate(newSaleDate)

            } else {
                Toast.makeText(this, "Error al registrar la fecha de venta", Toast.LENGTH_SHORT).show()
            }
        }
        binding.btnPickDate.setOnClickListener {
            pickDate()
        }
    }

    private fun pickDate() {
        val constraintsBuilder = CalendarConstraints.Builder().setValidator(
            DateValidatorPointForward.now()
        )
        val datePicker = MaterialDatePicker.Builder.datePicker()
            .setTitleText(resources.getString(R.string.eventDate))
            .setSelection(MaterialDatePicker.todayInUtcMilliseconds())
            .setCalendarConstraints(constraintsBuilder.build())
            .build().apply {
                addOnPositiveButtonClickListener {
                    selectedDate = Date(it)
                    binding.eventDate.text =
                        SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(selectedDate)
                }
            }

        datePicker.show(supportFragmentManager, "Fecha del evento")
    }

    private fun validateForm(): Boolean {
        if (binding.eventDate.text.isEmpty()) {
            Toast.makeText(this, "Debe seleccionar una fecha", Toast.LENGTH_SHORT).show()
            return false
        }
        if (binding.etNumberOfTickets.text.toString().toInt() <= 0) {
            Toast.makeText(this, "Debe ingresar un número de tickets válido", Toast.LENGTH_SHORT).show()
            return false
        }
        if (binding.etPrice.text.toString().toDouble() <= 0.0) {
            Toast.makeText(this, "Debe ingresar un precio válido", Toast.LENGTH_SHORT).show()
            return false
        }
        if (binding.etMaxTickets.text.toString().toInt() <= 0) {
            Toast.makeText(this, "Debe ingresar un número máximo de tickets válido", Toast.LENGTH_SHORT).show()
            return false
        }
        if (!isEndTimeAfterStartTime()) {
            return false
        }
        return true
    }

    private fun isEndTimeAfterStartTime(): Boolean {
        val timeStart = binding.tpStart
        val timeEnd = binding.tpEnd
        val hour = timeStart.hour
        val minute = timeStart.minute
        val hour2 = timeEnd.hour
        val minute2 = timeEnd.minute

        if (hour2 < hour || (hour2 == hour && minute2 <= minute)) {
            Toast.makeText(this, "La hora de finalización debe ser posterior a la hora de inicio", Toast.LENGTH_SHORT).show()
            return false
        }
        return true
    }
}