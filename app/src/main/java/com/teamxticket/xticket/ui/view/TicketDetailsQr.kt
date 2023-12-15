package com.teamxticket.xticket.ui.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.ViewGroup
import androidx.activity.viewModels
import androidx.lifecycle.viewModelScope
import com.teamxticket.xticket.R
import com.teamxticket.xticket.data.model.Event
import com.teamxticket.xticket.databinding.ActivityPurchaseDetailBinding
import com.teamxticket.xticket.databinding.ActivityTicketDetailsQrBinding
import com.teamxticket.xticket.ui.viewModel.EventViewModel
import com.teamxticket.xticket.ui.viewModel.TicketsViewModel
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class TicketDetailsQr : AppCompatActivity() {

    private lateinit var binding: ActivityTicketDetailsQrBinding
    private val ticketsViewModel: TicketsViewModel by viewModels()
    private val eventViewModel : EventViewModel by viewModels()
    private var eventId: Int? = null
    private var saleDateId: Int? = null
    private var tickets: Int? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTicketDetailsQrBinding.inflate(layoutInflater)
        setContentView(binding.root)
            saleDateId = intent.getIntExtra("SALE_DATE_ID", -1)
            eventId = intent.getIntExtra("EVENT_ID", -1)
            tickets = intent.getIntExtra("TICKETS", -1)
            initObservables()

    }

    override fun onResume() {
        super.onResume()
        ticketsViewModel.getTicketData(eventId!!, saleDateId!!)
    }

    private fun initObservables() {
        ticketsViewModel.eventModel.observe(this) {
            val event = it?.find { event -> event.eventId == eventId }
            if (event != null) {
                binding.eventName.text = event.name
                binding.eventLocation.text = (event.location)
                binding.eventGenre.text = (event.genre)
            }
        }
        ticketsViewModel.saleDateActive.observe(this) { it ->

            val saleDate = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).parse(it!!.saleDate)
            val calendar = Calendar.getInstance()
            if (saleDate != null) {
                calendar.time = saleDate
            }



            val month = SimpleDateFormat("MMM", Locale("es", "ES")).format(calendar.time).uppercase()
            val day = calendar.get(Calendar.DAY_OF_MONTH).toString()

            binding.tvSaleMonth.text = month
            binding.tvSaleDay.text = day

            val startTime = SimpleDateFormat("HH:mm:ss", Locale.getDefault()).parse(it!!.startTime)
            val endTime = SimpleDateFormat("HH:mm:ss", Locale.getDefault()).parse(it!!.endTime)

            val startTimeFormatted = SimpleDateFormat("HH:mm", Locale.getDefault()).format(startTime!!)
            val endTimeFormatted = SimpleDateFormat("HH:mm", Locale.getDefault()).format(endTime!!)

            binding.tvStartHour.text = startTimeFormatted
            binding.tvEndHour.text = endTimeFormatted


            binding.tvPrice.text = buildString {
                append(getString(R.string.price))
                append(it!!.price.toString())
            }
//eteno
            binding.tvTotalTickets.text = buildString {
                append("Entradas compradas: ")
                append(tickets.toString())
            }

            if (it!!.adults == 1) {
                binding.tvOnlyAdults.visibility = ViewGroup.VISIBLE
            }
//esteno

        }
    }




}