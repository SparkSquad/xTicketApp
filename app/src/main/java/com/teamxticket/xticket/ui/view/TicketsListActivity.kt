package com.teamxticket.xticket.ui.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import com.teamxticket.xticket.data.model.TicketData
import com.teamxticket.xticket.databinding.ActivityTicketsListBinding
import com.teamxticket.xticket.ui.view.adapter.TicketAdpater
import com.teamxticket.xticket.ui.viewModel.TicketsViewModel

class TicketsListActivity : AppCompatActivity() {

    private lateinit var binding: ActivityTicketsListBinding
    private val ticketsViewModel: TicketsViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTicketsListBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.rvTicketsList.layoutManager = LinearLayoutManager(this)

        ticketsViewModel.loadTickets(1)
        initListeners()
        initObservables()
    }

    private fun initObservables() {
        ticketsViewModel.ticketsModel.observe(this) { ticketsList ->
            val ticketsData : List<TicketData> = ticketsList ?: emptyList()
            val adapter = TicketAdpater(ticketsData) { ticketData -> onItemSelected(ticketData) }
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

    private fun initListeners() {
        binding.btnRefund.setOnClickListener {
            /*Intent(this, RefundActivity::class.java).apply {
                startActivity(this)
            }*/
        }
    }

    private fun onItemSelected(ticketData: TicketData) {
        /*Intent(this, UpdateDeleteSaleDateActivity::class.java).apply {
            putExtra("saleDate", saleDate)
            startActivity(this)
        }*/
        var dialog = TicketQrFragment(ticketData)
        Toast.makeText(this, "Selected: ${ticketData.ticket.uuid}", Toast.LENGTH_SHORT).show()
    }
}