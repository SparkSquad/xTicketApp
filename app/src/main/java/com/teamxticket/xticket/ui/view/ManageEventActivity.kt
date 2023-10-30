package com.teamxticket.xticket.ui.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import com.teamxticket.xticket.data.model.EventProvider
import com.teamxticket.xticket.databinding.ActivityManageEventBinding
import com.teamxticket.xticket.ui.view.adapter.EventAdapter
import com.teamxticket.xticket.ui.viewModel.EventViewModel

class ManageEventActivity : AppCompatActivity() {
    private lateinit var binding: ActivityManageEventBinding
    private val eventViewModel : EventViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityManageEventBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.recyclerEvents.layoutManager = LinearLayoutManager(this)
        setupBtnAddEvent()
    }

    override fun onResume() {
        super.onResume()
        // TODO: Load events from user with Shingleton
        eventViewModel.loadEvents(1)
        initObservables()
    }

    private fun setupBtnAddEvent() {
        binding.btnAddEvent.setOnClickListener {
            Intent(this, CreateEventActivity::class.java).apply {
                startActivity(this)
            }
        }
    }

    private fun initObservables() {
        eventViewModel.eventModel.observe(this) {
            val adapter = EventAdapter(EventProvider.eventsList, onItemClick = { event ->
                // TODO: Open event details
                Toast.makeText(this, "Clicked on ${event.name}", Toast.LENGTH_SHORT).show()
            })
            binding.recyclerEvents.adapter = adapter
        }
        eventViewModel.showLoader.observe(this) { visible ->
            binding.progressBar.isVisible = visible
            binding.overlayView.isVisible = visible
        }
    }
}