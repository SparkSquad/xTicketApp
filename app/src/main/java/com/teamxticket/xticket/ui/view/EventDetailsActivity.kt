package com.teamxticket.xticket.ui.view

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import com.teamxticket.xticket.R
import com.teamxticket.xticket.core.ActiveUser
import com.teamxticket.xticket.databinding.ActivityEventDetailsBinding
import com.teamxticket.xticket.ui.viewModel.EventViewModel
import com.teamxticket.xticket.ui.viewModel.UserViewModel
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

class EventDetailsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityEventDetailsBinding
    private var eventId: Int? = null
    private val eventViewModel: EventViewModel by viewModels()
    private val userViewModel: UserViewModel by viewModels()
    private val activeUser: ActiveUser = ActiveUser.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEventDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        eventId = intent.extras?.getInt("eventId")

        eventViewModel.getEvent(eventId!!)
        initListeners()
        initObservables()
    }

    private fun initObservables() {
        eventViewModel.eventModel.observe(this) {
            val event = it?.find { event -> event.eventId == eventId }
            if (event != null) {
                binding.tvEventName.text = event.name
                binding.tvEventLocation.text = event.location
                binding.tvEventDescription.text = event.description

                val currentDate = Date()
                val formatter = SimpleDateFormat("dd-MM-yyyy", Locale("es", "ES"))
                val sortedDates = event.saleDates?.sortedBy { kotlin.math.abs(formatter.parse(it.saleDate).time - currentDate.time) }
                val calendar = Calendar.getInstance()

                if (sortedDates != null) {
                    var maxTickets = 0
                    sortedDates.forEach { saleDate ->
                        calendar.time = formatter.parse(saleDate.saleDate)!!
                        val date = formatter.format(calendar.time)

                        val startTime = SimpleDateFormat("HH:mm:ss", Locale.getDefault()).parse(saleDate.startTime)
                        val startTimeFormatted = SimpleDateFormat("HH:mm", Locale.getDefault()).format(startTime!!)

                        val textView = TextView(this)
                        textView.text = "$date - $startTimeFormatted"
                        textView.textSize = 16f

                        binding.llEventDates.addView(textView)

                        maxTickets += saleDate.tickets
                    }
                    binding.tvEventPrice.text = getString(R.string.event_ticket_price_format).format(sortedDates.first().price)
                    binding.tvEventMaxTicketsPerPerson.text = getString(R.string.event_max_tickets_per_person_format).format(sortedDates.first().maxTickets)
                    binding.tvEventMaxCapacity.text = getString(R.string.event_capacity_format).format(maxTickets)

                    if(sortedDates.first().adults == 0) {
                        binding.tvAgeRestriction.text = getString(R.string.no_age_restriction)
                    }
                    else {
                        binding.tvAgeRestriction.text = getString(R.string.age_restriction)
                    }
                }

                if(event.eventPlanner != null) {
                    binding.tvEventPlannerName.text = "${event.eventPlanner.name} ${event.eventPlanner.surnames}"
                }
            }
        }
    }

    private fun initListeners() {
        binding.btnBuyTickets.setOnClickListener {
            Intent(this, PurchaseDetailActivity::class.java).apply {
                startActivity(this)
            }
        }

        binding.fbFollowEvent.setOnClickListener {
            activeUser.getUser()?.let { it1 -> userViewModel.followEvent(it1.userId, eventId!!) }
        }
    }
}