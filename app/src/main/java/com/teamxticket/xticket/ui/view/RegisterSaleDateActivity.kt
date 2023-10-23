package com.teamxticket.xticket.ui.view

import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.datepicker.CalendarConstraints
import com.google.android.material.datepicker.DateValidatorPointForward
import com.google.android.material.datepicker.MaterialDatePicker
import com.teamxticket.xticket.R
import com.teamxticket.xticket.data.model.SaleDate
import com.teamxticket.xticket.databinding.ActivityRegisterSaleDateBinding
import com.teamxticket.xticket.ui.viewModel.RegisterSaleDateViewModel

class RegisterSaleDateActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegisterSaleDateBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterSaleDateBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initListeners()
    }

    private fun initListeners() {
        binding.btnCreateSaleDate.setOnClickListener {
            val eventDate = binding.eventDate.text.toString()
            val startTime = binding.tpStart.toString()
            val endTime = binding.tpEnd.toString()
            val numberOfTickets = binding.etNumberOfTickets.text.toString().toInt()
            val price = binding.etPrice.text.toString().toFloat()
            val maxTickets = binding.etMaxTickets.text.toString().toInt()
            val onlyAdults = binding.switchOnlyAdults.isChecked


            if (validateForm()) {
                Toast.makeText(this, "Fecha de venta registrada", Toast.LENGTH_SHORT).show()
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

    fun validateForm(): Boolean {
        if (binding.eventDate.text.isEmpty()) {
            return false
            println("validacion fallida 1")
        }

        if (numberOfTickets <= 0) {
            println("validacion fallida 2")
            return false
        }

        if (price <= 0.0) {
            println("validacion fallida 3")
            return false
        }

        if (maxTickets <= 0) {
            println("validacion fallida 4")
            return false
        }

        if (!isEndTimeAfterStartTime()) {
            return false
        }
        println("validacion hecha")
        return true
    }

    private fun isEndTimeAfterStartTime(): Boolean {
        val startTimeParts = binding.tpStart.toString.split(":")
        val endTimeParts = endTime.split(":")

        if (startTimeParts.size != 2 || endTimeParts.size != 2) {
            return false
        }

        val startHour = startTimeParts[0].toInt()
        val startMinute = startTimeParts[1].toInt()
        val endHour = endTimeParts[0].toInt()
        val endMinute = endTimeParts[1].toInt()

        if (endHour < startHour) {
            return false
        } else if (endHour == startHour && endMinute <= startMinute) {
            return false
        }
        println("validacion hecha")
        return true
    }
}