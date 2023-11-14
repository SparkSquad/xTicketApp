package com.teamxticket.xticket.ui.view

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.teamxticket.xticket.core.ActiveUser
import com.teamxticket.xticket.data.model.Event
import com.teamxticket.xticket.data.model.EventProvider
import com.teamxticket.xticket.databinding.FragmentManageEventBinding
import com.teamxticket.xticket.ui.view.adapter.EventAdapter
import com.teamxticket.xticket.ui.viewModel.EventViewModel

class ManageEventFragment : Fragment() {

    private var _binding: FragmentManageEventBinding? = null
    private val eventViewModel : EventViewModel by viewModels()
    private val binding get() = _binding!!
    private var activeUser = ActiveUser.getInstance().getUser()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentManageEventBinding.inflate(inflater, container, false)
        val rootView = binding.root

        binding.recyclerEvents.layoutManager = LinearLayoutManager(requireContext())
        setupBtnAddEvent()

        return rootView
    }
    override fun onResume() {
        super.onResume()
        eventViewModel.loadEvents(activeUser!!.userId)
        initObservables()
    }

    private fun setupBtnAddEvent() {
        binding.btnAddEvent.setOnClickListener {
            Intent(requireContext(), CreateEventActivity::class.java).apply {
                startActivity(this)
            }
        }
    }

    private fun initObservables() {
        eventViewModel.eventModel.observe(viewLifecycleOwner) {
            val adapter = EventAdapter(EventProvider.eventsList, onItemClick = { event ->
                onEventClick(event)
            })
            binding.recyclerEvents.adapter = adapter
        }

        eventViewModel.showLoader.observe(viewLifecycleOwner) { visible ->
            binding.progressBar.isVisible = visible
            binding.overlayView.isVisible = visible
        }
    }

    private fun onEventClick(event: Event) {
        Intent(requireContext(), EventDetailActivity::class.java).apply {
            putExtra("eventId", event.eventId)
            startActivity(this)
        }
    }
}