package com.teamxticket.xticket.ui.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import com.teamxticket.xticket.data.model.TicketData
import com.teamxticket.xticket.databinding.ActivityRefundBinding
import com.teamxticket.xticket.ui.view.adapter.TicketAdapter
import com.teamxticket.xticket.ui.viewModel.TicketsViewModel

class RefundActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRefundBinding
    private val ticketsViewModel: TicketsViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRefundBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.rvTicketsList.layoutManager = LinearLayoutManager(this)

        ticketsViewModel.loadRefundTickets()
        initObservables()
    }

    private fun initObservables() {
        ticketsViewModel.refundList.observe(this) { ticketsList ->
            val ticketsData : List<TicketData> = ticketsList ?: emptyList()
            val adapter = TicketAdapter(ticketsData) { ticketData -> onItemSelected(ticketData) }
            binding.rvTicketsList.adapter = adapter
        }

        ticketsViewModel.showLoader.observe(this) { visible ->
            binding.progressBar.isVisible = visible
            binding.overlayView.isVisible = visible
        }

        ticketsViewModel.error.observe(this) { errorCode ->
            Toast.makeText(this, errorCode, Toast.LENGTH_SHORT).show()
        }
    }

    private fun onItemSelected(ticketData: TicketData) {

    }
}