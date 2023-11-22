package com.teamxticket.xticket.ui.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Gravity
import android.view.ViewGroup
import androidx.activity.viewModels
import androidx.core.view.isVisible
import com.teamxticket.xticket.R
import com.teamxticket.xticket.core.ActiveUser
import com.teamxticket.xticket.data.model.Event
import com.teamxticket.xticket.data.model.SaleDate
import com.teamxticket.xticket.data.model.Ticket
import com.teamxticket.xticket.databinding.ActivityPurchaseDetailBinding
import com.teamxticket.xticket.ui.viewModel.TicketsViewModel
import com.thecode.aestheticdialogs.AestheticDialog
import com.thecode.aestheticdialogs.DialogAnimation
import com.thecode.aestheticdialogs.DialogStyle
import com.thecode.aestheticdialogs.DialogType
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

class PurchaseDetailActivity : AppCompatActivity() {

    private lateinit var eventActive: Event
    private lateinit var saleDateActive: SaleDate
    private lateinit var binding: ActivityPurchaseDetailBinding
    private val ticketsViewModel: TicketsViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPurchaseDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        eventActive = intent.getSerializableExtra("event") as Event
        saleDateActive = intent.getSerializableExtra("saleDate") as SaleDate

        setTicketData()
        initListeners()
    }

    private fun setTicketData() {

        binding.eventName.text = eventActive.name
        binding.eventGenre.text = eventActive.genre
        binding.eventLocation.text = eventActive.location

        val saleDate = SimpleDateFormat("yyyy-MM-dd", java.util.Locale.getDefault()).parse(saleDateActive.saleDate)
        val calendar = java.util.Calendar.getInstance()
        if (saleDate != null) {
            calendar.time = saleDate
        }

        val month = SimpleDateFormat("MMM", Locale("es", "ES")).format(calendar.time).uppercase()
        val day = calendar.get(java.util.Calendar.DAY_OF_MONTH).toString()

        binding.tvSaleMonth.text = month
        binding.tvSaleDay.text = day

        val startTime = SimpleDateFormat("HH:mm:ss", java.util.Locale.getDefault()).parse(saleDateActive.startTime)
        val endTime = SimpleDateFormat("HH:mm:ss", java.util.Locale.getDefault()).parse(saleDateActive.endTime)

        val startTimeFormatted = SimpleDateFormat("HH:mm", java.util.Locale.getDefault()).format(startTime!!)
        val endTimeFormatted = SimpleDateFormat("HH:mm", java.util.Locale.getDefault()).format(endTime!!)

        binding.tvStartHour.text = startTimeFormatted
        binding.tvEndHour.text = endTimeFormatted


        binding.tvPrice.text = "Precio:" + saleDateActive.price.toString()

        binding.tvTotalTickets.text = "Boletos disponibles: "+ saleDateActive.tickets.toString()

        if (saleDateActive.adults == 1) {
            binding.tvOnlyAdults.visibility = android.view.ViewGroup.VISIBLE
        }

        binding.tvMaxTickets.text = "Maximo de tickets por persona: " + saleDateActive.maxTickets.toString()
    }

    private fun initListeners() {
       binding.btnCancel.setOnClickListener {
           finish()
       }

        binding.btnBuy.setOnClickListener {
            purchaseTicket()
        }
    }

    private fun purchaseTicket() {
        if (binding.etTickets.text.toString().isNotEmpty()) {
            binding.etTickets.error = "Please enter the number of tickets you want to purchase"
            var total = saleDateActive.price * binding.etTickets.text.toString().toInt()
            var userId = ActiveUser.getInstance().getUser()!!.userId
            var ticket = Ticket (
                total,
                Date().toString(),
                saleDateActive.saleDateId,
                0,
                binding.etTickets.text.toString().toInt(),
                userId,
                ""
            )
            ticketsViewModel.purchaseTicket(ticket)
        }
    }

    private fun initObservables() {
        ticketsViewModel.successfulPurchase.observe(this) { it ->
            if (it == 1) {
                AestheticDialog.Builder(this, DialogStyle.FLAT, DialogType.SUCCESS)
                    .setTitle("Exito")
                    .setMessage("Compra realizada con exito")
                    .setCancelable(true)
                    .setGravity(Gravity.CENTER)
                    .setAnimation(DialogAnimation.SHRINK)
                    .show()
            }
        }
        ticketsViewModel.showLoaderPurchase.observe(this) { it ->
            binding.progressBar.isVisible = it
            binding.overlayView.isVisible = it
        }
    }
}