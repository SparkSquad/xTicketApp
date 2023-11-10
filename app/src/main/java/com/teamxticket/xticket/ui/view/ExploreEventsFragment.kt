package com.teamxticket.xticket.ui.view

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.viewModels
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.findViewTreeLifecycleOwner
import androidx.recyclerview.widget.LinearLayoutManager
import com.teamxticket.xticket.R
import com.teamxticket.xticket.data.model.Event
import com.teamxticket.xticket.databinding.ActivityExploreEventsBinding
import com.teamxticket.xticket.databinding.FragmentExploreEventsBinding
import com.teamxticket.xticket.ui.view.adapter.EventAdapter
import com.teamxticket.xticket.ui.viewModel.EventViewModel

class ExploreEventsFragment : Fragment() {

    private var _binding: FragmentExploreEventsBinding? = null
    private val binding get() = _binding!!
    private val eventsViewModel: EventViewModel by viewModels()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentExploreEventsBinding.inflate(inflater, container, false)
        val rootView = binding.root
        binding.rvSearchResults.layoutManager = LinearLayoutManager(rootView.context)

        eventsViewModel.searchEvents("", null, 100, 1)
        initObservables()
        initListeners()
        return rootView
    }

    private fun initObservables() {
        val lifecycle = this.viewLifecycleOwner

        eventsViewModel.eventModel.observe(lifecycle) { eventsList ->
            val eventsData : MutableList<Event> = eventsList?.toMutableList() ?: arrayListOf()
            val adapter = EventAdapter(eventsData) { eventData -> onItemSelected(eventData) }
            binding.rvSearchResults.adapter = adapter
        }

        eventsViewModel.showLoader.observe(lifecycle) { visible ->
            binding.progressBar.isVisible = visible
            binding.rvSearchResults.isVisible = !visible
        }

        eventsViewModel.error.observe(lifecycle) { errorCode ->
            Toast.makeText(binding.root.context, errorCode, Toast.LENGTH_SHORT).show()
        }
    }

    private fun initListeners() {
        // update search results when user types in search bar
        binding.etSearch.addTextChangedListener(object : TextWatcher {
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
        Toast.makeText(binding.root.context, "Event selected", Toast.LENGTH_SHORT).show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}