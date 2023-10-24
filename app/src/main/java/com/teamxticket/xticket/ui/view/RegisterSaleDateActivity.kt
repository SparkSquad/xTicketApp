package com.teamxticket.xticket.ui.view

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.datepicker.CalendarConstraints
import com.google.android.material.datepicker.DateValidatorPointForward
import com.google.android.material.datepicker.MaterialDatePicker
import com.teamxticket.xticket.R
import com.teamxticket.xticket.databinding.ActivityRegisterSaleDateBinding

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
            Toast.makeText(this, "Debe seleccionar una fecha", Toast.LENGTH_SHORT).show()
            return false
        }

        if (binding.etNumberOfTickets.text.toString().toInt() <= 0) {
            Toast.makeText(this, "Debe ingresar un número de tickets válido", Toast.LENGTH_SHORT).show()
            return false
        }

        if (binding.etPrice.text.toString().toFloat() <= 0.0) {
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

        Toast.makeText(this, "hora de inicio: $hour:$minute", Toast.LENGTH_SHORT).show()
        Toast.makeText(this, "hora de finalización: $hour2:$minute2", Toast.LENGTH_SHORT).show()


        if (hour2 < hour || (hour2 == hour && minute2 <= minute)) {
           Toast.makeText(this, "La hora de finalización debe ser posterior a la hora de inicio", Toast.LENGTH_SHORT).show()
            return true
        } else {
            Toast.makeText(this, "validacion hecha", Toast.LENGTH_SHORT).show()
            return false
        }
    }
}