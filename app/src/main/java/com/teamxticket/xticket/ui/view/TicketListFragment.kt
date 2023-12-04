package com.teamxticket.xticket.ui.view

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.teamxticket.xticket.core.ActiveUser
import com.teamxticket.xticket.data.model.TicketData
import com.teamxticket.xticket.databinding.FragmentTicketListBinding
import com.teamxticket.xticket.ui.view.adapter.TicketAdapter
import com.teamxticket.xticket.ui.viewModel.TicketsViewModel

class TicketListFragment : Fragment() {

    private var _binding: FragmentTicketListBinding? = null
    private val binding get() = _binding!!
    private val ticketsViewModel: TicketsViewModel by viewModels()
    private var activeUser = ActiveUser.getInstance().getUser()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View {
        _binding = FragmentTicketListBinding.inflate(inflater, container, false)
        val rootView = binding.root
        binding.rvTicketsList.layoutManager = LinearLayoutManager(requireContext())

        initListeners()
        initObservables()
        return rootView
    }

    override fun onResume() {
        super.onResume()
        ticketsViewModel.loadTickets(activeUser!!.userId)
    }

    private fun initObservables() {
        ticketsViewModel.ticketsModel.observe(viewLifecycleOwner) { ticketsList ->
            val ticketsData: List<TicketData> = ticketsList ?: emptyList()
            val adapter = TicketAdapter(ticketsData) { ticketData -> onItemSelected(ticketData) }
            binding.rvTicketsList.adapter = adapter
        }

        ticketsViewModel.showLoader.observe(viewLifecycleOwner) { visible ->
            binding.progressBar.isVisible = visible
            binding.overlayView.isVisible = visible
        }

        ticketsViewModel.error.observe(viewLifecycleOwner) { errorCode ->
            Toast.makeText(requireContext(), errorCode, Toast.LENGTH_SHORT).show()
        }
    }

    private fun initListeners() {
        binding.btnRefund.setOnClickListener {
            Intent(requireContext(), RefundActivity::class.java).apply {
                startActivity(this)
            }
        }
    }

    private fun onItemSelected(ticketData: TicketData) {
        val content =
            "xTicket/${ticketData.ticket.uuid}/EventId/${ticketData.saleDate.eventId}/Date/${ticketData.saleDate.saleDate}${ticketData.ticket.price}/Total/${ticketData.ticket.totalTickets}/Purchase/${ticketData.ticket.purchaseDate}"
        val fragment = TicketQrFragment.newInstance(content)
        fragment.show(parentFragmentManager, "ticketQrFragment")
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
