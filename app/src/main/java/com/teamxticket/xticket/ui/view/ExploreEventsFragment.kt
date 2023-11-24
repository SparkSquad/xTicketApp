package com.teamxticket.xticket.ui.view

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.appbar.AppBarLayout
import com.google.android.material.chip.Chip
import com.teamxticket.xticket.R
import com.teamxticket.xticket.data.model.Event
import com.teamxticket.xticket.databinding.FragmentExploreEventsBinding
import com.teamxticket.xticket.ui.view.adapter.EventAdapter
import com.teamxticket.xticket.ui.viewModel.EventViewModel
import kotlin.math.abs

class ExploreEventsFragment : Fragment() {

    private var _binding: FragmentExploreEventsBinding? = null
    private val binding get() = _binding!!
    private val eventsViewModel: EventViewModel by viewModels()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentExploreEventsBinding.inflate(inflater, container, false)
        val rootView = binding.root
        binding.rvSearchResults.layoutManager = LinearLayoutManager(rootView.context)

        eventsViewModel.searchEvents("", null, 100, 1)
        eventsViewModel.loadGenres()
        initObservables()
        initListeners()
        return rootView
    }

    private fun initObservables() {
        val lifecycle = this.viewLifecycleOwner

        eventsViewModel.eventModel.observe(lifecycle) { eventsList ->
            var eventsData : MutableList<Event> = eventsList?.toMutableList() ?: arrayListOf()
            eventsData = eventsData.filter { event -> event.saleDates?.isNotEmpty() ?: false }.toMutableList()
            val adapter = EventAdapter(eventsData) { eventData -> onItemSelected(eventData) }
            binding.rvSearchResults.adapter = adapter
        }

        eventsViewModel.showLoader.observe(lifecycle) { visible ->
            binding.progressView.isVisible = visible
            binding.rvSearchResults.isVisible = !visible
            binding.hsvGenresChips.isVisible = !visible
        }

        eventsViewModel.error.observe(lifecycle) { errorCode ->
            Toast.makeText(binding.root.context, errorCode, Toast.LENGTH_SHORT).show()
        }

        eventsViewModel.genresModel.observe(this.viewLifecycleOwner) { genresList ->
            populateGenresChipsGroup(genresList ?: arrayListOf())
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

        binding.appbar.addOnOffsetChangedListener { appBarLayout, verticalOffset ->
            if(abs(verticalOffset) == appBarLayout.totalScrollRange) {
                binding.collapsingToolbar.isTitleEnabled = true
                if(binding.etSearch.text.isNotBlank()) {
                    binding.collapsingToolbar.title = getString(R.string.search_results_title_bar) + "\"${binding.etSearch.text}\""
                }
                else {
                    binding.collapsingToolbar.title = getString(R.string.explore_events_title_bar)
                }
            }
            else {
                binding.collapsingToolbar.isTitleEnabled = false
            }
        }
    }

    private fun populateGenresChipsGroup(genres: List<String>) {
        binding.cgGenres.removeAllViews()
        for(genre in genres) {
            val chip = Chip(binding.root.context)
            chip.text = genre
            chip.isCheckable = true
            chip.isCheckedIconVisible = false
            chip.isChipIconVisible = false
            chip.setOnCheckedChangeListener { buttonView, isChecked ->
                if(isChecked) {
                    eventsViewModel.searchEvents(binding.etSearch.text.toString(), buttonView.text.toString(), 100, 1)
                } else {
                    eventsViewModel.searchEvents(binding.etSearch.text.toString(), null, 100, 1)
                }
            }
            binding.cgGenres.addView(chip)
        }
    }

    private fun onItemSelected(eventData: Event) {
        Intent(requireContext(), EventDetailsActivity::class.java).apply {
            this.putExtra("eventId", eventData.eventId)
            startActivity(this)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}