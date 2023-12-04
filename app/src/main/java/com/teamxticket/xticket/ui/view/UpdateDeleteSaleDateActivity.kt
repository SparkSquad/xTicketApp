package com.teamxticket.xticket.ui.view

import android.content.res.ColorStateList
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.activity.viewModels
import androidx.core.view.isVisible
import com.google.android.material.datepicker.CalendarConstraints
import com.google.android.material.datepicker.DateValidatorPointForward
import com.google.android.material.datepicker.MaterialDatePicker
import com.teamxticket.xticket.R
import com.teamxticket.xticket.core.ActiveUser
import com.teamxticket.xticket.data.model.SaleDate
import com.teamxticket.xticket.databinding.ActivityUpdateDeleteSaleDateBinding
import com.teamxticket.xticket.ui.viewModel.SaleDateViewModel
import com.thecode.aestheticdialogs.AestheticDialog
import com.thecode.aestheticdialogs.DialogAnimation
import com.thecode.aestheticdialogs.DialogStyle
import com.thecode.aestheticdialogs.DialogType
import com.thecode.aestheticdialogs.OnDialogClickListener
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class UpdateDeleteSaleDateActivity : AppCompatActivity() {

    private val saleDateViewModel : SaleDateViewModel by viewModels()
    private lateinit var binding : ActivityUpdateDeleteSaleDateBinding
    private var saleDate: SaleDate? = null
    private var selectedDate: Date = Date()
    private val darkDialog = ActiveUser.getInstance().getDarkMode()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUpdateDeleteSaleDateBinding.inflate(layoutInflater)
        setContentView(binding.root)

        this.saleDate = intent.getParcelableExtra("saleDate")
        setSaleDate()
        initListeners()
        initObservables()
        setupValidationOnFocusChange(binding.etNumberOfTickets)
        setupValidationOnFocusChange(binding.etPrice)
        setupValidationOnFocusChange(binding.etMaxTickets)
    }

    private fun setSaleDate() {
        val date = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).parse(saleDate?.saleDate.toString())
        val formattedDate = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(date!!)

        selectedDate = date
        binding.tvEventDate.text = formattedDate

        val startTime = SimpleDateFormat("HH:mm:ss", Locale.getDefault()).parse(saleDate!!.startTime)
        val endTime = SimpleDateFormat("HH:mm:ss", Locale.getDefault()).parse(saleDate!!.endTime)

        binding.tpStart.hour = startTime?.hours ?: 0
        binding.tpStart.minute = startTime?.minutes ?: 0
        binding.tpEnd.hour = endTime?.hours?: 0
        binding.tpEnd.minute = endTime?.minutes?:0
        binding.etPrice.setText(saleDate!!.price.toString())
        binding.etMaxTickets.setText(saleDate!!.maxTickets.toString())
        binding.etNumberOfTickets.setText(saleDate!!.tickets.toString())

        if (saleDate!!.adults == 1) {
            binding.switchOnlyAdults.isChecked = true
        }
    }

    private fun initObservables() {
        saleDateViewModel.successfulUpdate.observe(this) { successful ->
            if (successful == 200) {
                showMessages(getString(R.string.saleDateUpdated), false)
            } else {
                showMessages(getString(R.string.saleDateNotUpdated), true)
            }
        }

        saleDateViewModel.successfulDelete.observe(this) {successful ->
            if (successful == 200) {
                showMessages(getString(R.string.saleDateDeleted), false)
            } else {
                showMessages(getString(R.string.saleDateNotDeleted), true)
            }
        }

        saleDateViewModel.errorCode.observe(this) { errorCode ->
            showMessages(errorCode, true)
        }

        saleDateViewModel.showLoaderUpdate.observe(this) { visible ->
            binding.progressBar.isVisible = visible
            binding.overlayView.isVisible = visible
        }
    }

    private fun initListeners() {
        binding.btnPickDate.setOnClickListener {
            pickDate()
        }
        binding.btnDeleteSaleSale.setOnClickListener {
            deleteSaleDate()
        }
        binding.btnUpdateSaleDate.setOnClickListener {
            updateSaleDate()
        }
    }

    private fun updateSaleDate() {
        if (validateForm()) {
            val startTime = binding.tpStart.hour.toString() + ":" + binding.tpStart.minute.toString()
            val endTime = binding.tpEnd.hour.toString() + ":" + binding.tpEnd.minute.toString()
            val numberOfTickets = binding.etNumberOfTickets.text.toString().toInt()
            val price = binding.etPrice.text.toString().toDouble()
            val maxTickets = binding.etMaxTickets.text.toString().toInt()
            val adults = if (binding.switchOnlyAdults.isChecked) 1 else 0

            val newSaleDate = SaleDate(
                adults,
                endTime,
                saleDate!!.eventId,
                maxTickets,
                price,
                selectedDate.toString(),
                0,
                startTime,
                numberOfTickets,
            )
            saleDateViewModel.updateSaleDate(saleDate!!.saleDateId, newSaleDate)
        }
    }

    private fun deleteSaleDate() {
        saleDateViewModel.deleteSaleDate(saleDate!!.saleDateId)
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
        if (selectedDate.time < Date().time) {
            Toast.makeText(this, getString(R.string.dateError), Toast.LENGTH_SHORT).show()
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
                    binding.tvEventDate.text =
                        SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(it)
                }
            }

        datePicker.show(supportFragmentManager, getString(R.string.eventDate))
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
                .setDarkMode(darkDialog)
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
                .setDarkMode(darkDialog)
                .setGravity(Gravity.CENTER)
                .setAnimation(DialogAnimation.SHRINK)
                .show()
        }
    }
}