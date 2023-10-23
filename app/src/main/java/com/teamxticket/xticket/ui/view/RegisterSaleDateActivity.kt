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
    private val saleDateViewModel: RegisterSaleDateViewModel by viewModels()
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

            val saleDate = SaleDate (
                eventDate,
                numberOfTickets,
                price,
                maxTickets,
                onlyAdults,
                startTime,
                endTime)

            if (saleDateViewModel.registerSaleDate(saleDate) ) {
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
}