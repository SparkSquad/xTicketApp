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
import com.teamxticket.xticket.data.model.SaleDate
import com.teamxticket.xticket.databinding.ActivityUpdateDeleteSaleDateBinding
import com.teamxticket.xticket.ui.viewModel.SaleDateViewModel
import com.thecode.aestheticdialogs.AestheticDialog
import com.thecode.aestheticdialogs.DialogAnimation
import com.thecode.aestheticdialogs.DialogStyle
import com.thecode.aestheticdialogs.DialogType
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class UpdateDeleteSaleDateActivity : AppCompatActivity() {

    private val saleDateViewModel : SaleDateViewModel by viewModels()
    private lateinit var binding : ActivityUpdateDeleteSaleDateBinding
    private var saleDate: SaleDate? = null
    private var selectedDate: Date = Date(0)
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
        val formattedDate = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(date!!)

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
                Toast.makeText(this, "Fecha de venta actualizada exitosamente", Toast.LENGTH_SHORT).show()
                finish()
            } else {
                Toast.makeText(this, "Error al actualizar la fecha de venta", Toast.LENGTH_SHORT).show()
            }
        }

        saleDateViewModel.successfulDelete.observe(this) {successful ->
            if (successful == 200) {
                AestheticDialog.Builder(this, DialogStyle.FLAT, DialogType.SUCCESS)
                    .setMessage("Fecha Eliminada satisfactoriamente")
                    .setCancelable(true)
                    .setGravity(Gravity.CENTER)
                    .setAnimation(DialogAnimation.SHRINK)
                    .show()
                finish()
            } else {
                Toast.makeText(this, "Error al eliminar la fecha de venta", Toast.LENGTH_SHORT).show()
                AestheticDialog.Builder(this, DialogStyle.FLAT, DialogType.WARNING)
                    .setTitle("Atencion")
                    .setMessage("No se pudo eliminar la fecha de venta")
                    .setCancelable(true)
                    .setGravity(Gravity.CENTER)
                    .setAnimation(DialogAnimation.SHRINK)
                    .show()
            }
        }

        saleDateViewModel.errorCode.observe(this) { errorCode ->
            AestheticDialog.Builder(this, DialogStyle.FLAT, DialogType.ERROR)
                .setTitle("Atencion")
                .setMessage(errorCode)
                .setCancelable(true)
                .setGravity(Gravity.CENTER)
                .setAnimation(DialogAnimation.SHRINK)
                .show()
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
        } else {
            Toast.makeText(this, "Error al registrar la fecha de venta", Toast.LENGTH_SHORT).show()
        }
    }

    private fun deleteSaleDate() {
        saleDateViewModel.deleteSaleDate(saleDate!!.saleDateId)
    }

    private fun validateForm(): Boolean {
        if (binding.etNumberOfTickets.text.isEmpty() || binding.etNumberOfTickets.text.toString().toInt() <= 0) {
            Toast.makeText(this, "Debe ingresar un número de tickets válido", Toast.LENGTH_SHORT).show()
            return false
        }
        if (binding.etPrice.text.isEmpty() || binding.etPrice.text.toString().toDouble() <= 0.0) {
            Toast.makeText(this, "Debe ingresar un precio válido", Toast.LENGTH_SHORT).show()
            return false
        }
        if (binding.etMaxTickets.text.isEmpty() || binding.etMaxTickets.text.toString().toInt() <= 0) {
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
                        SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(selectedDate)
                }
            }

        datePicker.show(supportFragmentManager, "Fecha del evento")
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
}