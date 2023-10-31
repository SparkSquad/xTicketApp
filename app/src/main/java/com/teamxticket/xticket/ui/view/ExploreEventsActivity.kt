package com.teamxticket.xticket.ui.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.Toast
import androidx.activity.viewModels
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import com.teamxticket.xticket.R
import com.teamxticket.xticket.data.model.Event
import com.teamxticket.xticket.databinding.ActivityExploreEventsBinding
import com.teamxticket.xticket.databinding.ActivityTicketsListBinding
import com.teamxticket.xticket.ui.view.adapter.EventAdapter
import com.teamxticket.xticket.ui.viewModel.EventViewModel

class ExploreEventsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityExploreEventsBinding;
    private val eventsViewModel: EventViewModel by viewModels();

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityExploreEventsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.rvSearchResults.layoutManager = LinearLayoutManager(this)

        eventsViewModel.searchEvents("", null, 100, 1)
        initListeners()
        initObservables()
    }

    private fun initObservables() {
        eventsViewModel.eventModel.observe(this) { eventsList ->
            val eventsData : MutableList<Event> = eventsList?.toMutableList() ?: arrayListOf()
            val adapter = EventAdapter(eventsData) { eventData -> onItemSelected(eventData) }
            binding.rvSearchResults.adapter = adapter
        }

        eventsViewModel.showLoader.observe(this) { visible ->
            binding.progressBar.isVisible = visible
            binding.overlayView.isVisible = visible
        }

        eventsViewModel.error.observe(this) { errorCode ->
            Toast.makeText(this, errorCode, Toast.LENGTH_SHORT).show()
        }
    }

    private fun initListeners() {
        // update search results when user types in search bar
        binding.etSearchEvents.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                eventsViewModel.searchEvents(s.toString(), null, 100, 1)
            }
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) { }
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) { }
        })
    }

    private fun onItemSelected(eventData: Event) {
//        Intent(this, EventDetailsActivity::class.java).apply {
//            putExtra("event", eventData)
//            startActivity(this)
//        }
        Toast.makeText(this, "Event selected", Toast.LENGTH_SHORT).show()
    }
}