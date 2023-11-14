package com.teamxticket.xticket.ui.view

import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import com.google.android.material.datepicker.CalendarConstraints
import com.google.android.material.datepicker.DateValidatorPointForward
import com.google.android.material.datepicker.MaterialDatePicker
import com.teamxticket.xticket.R
import com.teamxticket.xticket.data.model.SaleDate
import com.teamxticket.xticket.databinding.ActivityRegisterSaleDateBinding
import com.teamxticket.xticket.ui.viewModel.SaleDateViewModel
import com.thecode.aestheticdialogs.AestheticDialog
import com.thecode.aestheticdialogs.DialogAnimation
import com.thecode.aestheticdialogs.DialogStyle
import com.thecode.aestheticdialogs.DialogType
import com.thecode.aestheticdialogs.OnDialogClickListener
import java.io.IOException
import java.sql.Date
import java.text.SimpleDateFormat
import java.util.Locale

class RegisterSaleDateActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegisterSaleDateBinding
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
        setupValidationOnFocusChange(binding.etNumberOfTickets)
        setupValidationOnFocusChange(binding.etPrice)
        setupValidationOnFocusChange(binding.etMaxTickets)
    }

    private fun initObservables() {
        saleDateViewModel.showLoaderRegister.observe(this) { visible ->
            binding.progressBar.isVisible = visible
            binding.overlayView.isVisible = visible
        }

        saleDateViewModel.successfulRegister.observe(this) { successful ->
            if (successful == 200) {
                showMessages(getString(R.string.saleDateCreated), false)
            } else {
                showMessages(getString(R.string.saleDateNotCreated), true)
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
                val adults = if (binding.switchOnlyAdults.isChecked) 1 else 0

                val saleDate = SaleDate (
                    adults,
                    endTime,
                    eventId,
                    maxTickets,
                    price,
                    selectedDate.toString(),
                    0,
                    startTime,
                    numberOfTickets
                )
                saleDateViewModel.registerSaleDate(saleDate)
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
                        SimpleDateFormat("dd/MM/yyyy", Locale.ROOT).format(it)
                }
            }
        datePicker.show(supportFragmentManager, getString(R.string.eventDate))
    }

    private fun validateForm(): Boolean {
        val number = binding.etNumberOfTickets.text.toString().toIntOrNull()
        val price = binding.etPrice.text.toString().toDoubleOrNull()
        val maxTickets = binding.etMaxTickets.text.toString().toIntOrNull()

        if (number == null || number <= 0) {
            setElementView(binding.etNumberOfTickets, true, getString(R.string.emptyField))
            return false
        }
        if (price == null || price <= 0.0) {
            setElementView(binding.etPrice, true, getString(R.string.emptyField))
            return false
        }
        if (maxTickets == null || maxTickets <= 0) {
            setElementView(binding.etMaxTickets, true, getString(R.string.emptyField))
            return false
        }
        if (isEndTimeAfterStartTime()) {
            Toast.makeText(this, getString(R.string.durationError), Toast.LENGTH_SHORT).show()
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
        return (hour2 < hour || (hour2 == hour && minute2 <= minute))
    }

    private fun setElementView(editText: EditText, isError: Boolean, message: String) {
        val errorColor = if (isError) Color.RED else Color.BLACK
        editText.error = if (isError) message else null
        editText.setHintTextColor(errorColor)
        editText.backgroundTintList = ColorStateList.valueOf(errorColor)
    }

    private fun setupValidationOnFocusChange(editText: EditText) {
        editText.onFocusChangeListener = View.OnFocusChangeListener { _, hasFocus ->
            if (!hasFocus) {
                val inputText = editText.text.toString().replace("\\s+".toRegex(), " ").uppercase().trim()
                setElementView(editText, inputText.isEmpty(), getString(R.string.emptyField))
            }
        }
    }

    private fun showMessages(message: String, isError: Boolean) {
        if (!isError) {
            AestheticDialog.Builder(this, DialogStyle.FLAT, DialogType.SUCCESS)
                .setTitle(getString(R.string.success))
                .setMessage(message)
                .setCancelable(true)
                .setGravity(Gravity.CENTER)
                .setAnimation(DialogAnimation.SHRINK)
                .setOnClickListener(object : OnDialogClickListener {
                    override fun onClick(dialog: AestheticDialog.Builder) {
                        dialog.dismiss()
                        finish()
                    }
                })
                .show()
        } else {
            AestheticDialog.Builder(this, DialogStyle.FLAT, DialogType.ERROR)
                .setTitle(getString(R.string.failure))
                .setMessage(message)
                .setCancelable(true)
                .setGravity(Gravity.CENTER)
                .setAnimation(DialogAnimation.SHRINK)
                .show()
        }
    }
}