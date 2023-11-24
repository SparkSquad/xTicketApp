package com.teamxticket.xticket.ui.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.Gravity
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.activity.viewModels
import androidx.core.view.isVisible
import com.teamxticket.xticket.R
import com.teamxticket.xticket.core.ActiveUser
import com.teamxticket.xticket.data.model.BandArtistProvider
import com.teamxticket.xticket.data.model.Event
import com.teamxticket.xticket.data.model.SaleDate
import com.teamxticket.xticket.data.model.Ticket
import com.teamxticket.xticket.databinding.ActivityPurchaseDetailBinding
import com.teamxticket.xticket.ui.viewModel.EventViewModel
import com.teamxticket.xticket.ui.viewModel.SaleDateViewModel
import com.teamxticket.xticket.ui.viewModel.TicketsViewModel
import com.thecode.aestheticdialogs.AestheticDialog
import com.thecode.aestheticdialogs.DialogAnimation
import com.thecode.aestheticdialogs.DialogStyle
import com.thecode.aestheticdialogs.DialogType
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale
import kotlin.properties.Delegates

class PurchaseDetailActivity : AppCompatActivity() {

    private val ticketsViewModel: TicketsViewModel by viewModels()
    private var eventId: Int? = null
    private var saleDateId: Int? = null
    private lateinit var binding: ActivityPurchaseDetailBinding
    private lateinit var dateActive: SaleDate
    private var activeUser = ActiveUser.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPurchaseDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        eventId = intent.getIntExtra("eventId", 1)
        saleDateId = intent.getIntExtra("saleDateId", 1)

        initListeners()
        initObservables()
    }

    override fun onResume() {
        super.onResume()
        ticketsViewModel.getTicketData(eventId!!, saleDateId!!)
    }

    private fun initListeners() {
       binding.btnCancel.setOnClickListener {
           finish()
       }

        binding.btnBuy.setOnClickListener {
            purchaseTicket()
        }

        binding.etTickets.setOnFocusChangeListener { _, hasFocus ->
            if (!hasFocus) {
                if (binding.etTickets.text.toString().isNotEmpty()) {
                    val total = dateActive.price * binding.etTickets.text.toString().toInt()
                    binding.etTotal.setText(buildString {
                        append(getString(R.string.total))
                        append(total.toString())
                    })
                }
            }
        }

        binding.etTickets.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                // No se utiliza en este caso
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                // No se utiliza en este caso
            }

            override fun afterTextChanged(editable: Editable?) {
                editable?.let {
                    val currentValue = it.toString().toIntOrNull() ?: 0
                    if (currentValue > dateActive.maxTickets) {
                        binding.etTickets.setText(dateActive.maxTickets.toString())
                    }
                }
            }
        })
    }

    private fun purchaseTicket() {
        if (binding.etTickets.text.toString().isNotEmpty()) {
            binding.etTickets.error = "Please enter the number of tickets you want to purchase"
            val total = dateActive.price * binding.etTickets.text.toString().toInt()
            val userId = activeUser.getUser()!!.userId
            val ticket = Ticket (
                total,
                Date().toString(),
                dateActive.saleDateId,
                0,
                binding.etTickets.text.toString().toInt(),
                userId,
                ""
            )
            ticketsViewModel.purchaseTicket(ticket)
        }
    }

    private fun initObservables() {
        ticketsViewModel.showLoader.observe(this) {
            binding.progressBar.isVisible = it
            binding.overlayView.isVisible = it
        }

        ticketsViewModel.successfulPurchase.observe(this) { it ->
            if (it == 200) {
                AestheticDialog.Builder(this, DialogStyle.FLAT, DialogType.SUCCESS)
                    .setTitle(getString(R.string.success))
                    .setMessage(getString(R.string.success_purchase))
                    .setCancelable(true)
                    .setDarkMode(activeUser.getDarkMode())
                    .setGravity(Gravity.CENTER)
                    .setAnimation(DialogAnimation.SHRINK)
                    .show()
            }
        }

        ticketsViewModel.error.observe(this) {
            AestheticDialog.Builder(this, DialogStyle.FLAT, DialogType.ERROR)
                .setTitle(getString(R.string.failure))
                .setMessage(it)
                .setCancelable(true)
                .setDarkMode(activeUser.getDarkMode())
                .setGravity(Gravity.CENTER)
                .setAnimation(DialogAnimation.SHRINK)
                .show()
        }

        ticketsViewModel.eventModel.observe(this) {
            val event = it?.find { event -> event.eventId == eventId }
            if (event != null) {
                binding.eventName.text = event.name
                binding.eventLocation.text = (event.location)
                binding.eventGenre.text = (event.genre)
            }
        }

        ticketsViewModel.saleDateActive.observe(this) { it ->
            dateActive = it!!
            val saleDate = SimpleDateFormat("yyyy-MM-dd", java.util.Locale.getDefault()).parse(dateActive.saleDate)
            val calendar = Calendar.getInstance()
            if (saleDate != null) {
                calendar.time = saleDate
            }

            binding.tvMessageTickets.text = dateActive.toString()

            val month = SimpleDateFormat("MMM", Locale("es", "ES")).format(calendar.time).uppercase()
            val day = calendar.get(Calendar.DAY_OF_MONTH).toString()

            binding.tvSaleMonth.text = month
            binding.tvSaleDay.text = day

            val startTime = SimpleDateFormat("HH:mm:ss", Locale.getDefault()).parse(dateActive.startTime)
            val endTime = SimpleDateFormat("HH:mm:ss", Locale.getDefault()).parse(dateActive.endTime)

            val startTimeFormatted = SimpleDateFormat("HH:mm", Locale.getDefault()).format(startTime!!)
            val endTimeFormatted = SimpleDateFormat("HH:mm", Locale.getDefault()).format(endTime!!)

            binding.tvStartHour.text = startTimeFormatted
            binding.tvEndHour.text = endTimeFormatted


            binding.tvPrice.text = buildString {
                append(getString(R.string.price))
                append(dateActive.price.toString())
            }

            binding.tvTotalTickets.text = buildString {
                append(getString(R.string.avalableTickets))
                append(dateActive.tickets.toString())
            }

            if (dateActive.adults == 1) {
                binding.tvOnlyAdults.visibility = ViewGroup.VISIBLE
            }

            binding.tvMaxTickets.text = buildString {
                append(getString(R.string.max_tickets))
                append(dateActive.maxTickets.toString())
            }
        }
    }
}