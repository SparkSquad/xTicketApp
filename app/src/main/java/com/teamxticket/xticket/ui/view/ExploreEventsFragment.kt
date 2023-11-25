package com.teamxticket.xticket.ui.view

import android.content.Intent
import android.graphics.PorterDuff
import android.graphics.PorterDuffColorFilter
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.chip.Chip
import com.teamxticket.xticket.R
import com.teamxticket.xticket.core.ActiveUser
import com.teamxticket.xticket.data.model.Event
import com.teamxticket.xticket.databinding.FragmentExploreEventsBinding
import com.teamxticket.xticket.ui.view.adapter.EventAdapter
import com.teamxticket.xticket.ui.viewModel.EventViewModel
import com.teamxticket.xticket.ui.viewModel.UserViewModel
import kotlin.math.abs


class ExploreEventsFragment : Fragment() {

    private var _binding: FragmentExploreEventsBinding? = null
    private val binding get() = _binding!!
    private val eventsViewModel: EventViewModel by viewModels()
    private val usersViewModel: UserViewModel by viewModels()
    private val activeUser: ActiveUser = ActiveUser.getInstance()
    private var filterFollowedEvents: Boolean = false

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

        usersViewModel.followedEvents.observe(lifecycle) { followedList ->
            eventsViewModel.filterFollowedEvents(filterFollowedEvents, followedList)
        }
    }

    private fun setTextViewDrawableColor(textView: TextView, color: Int) {
        for (drawable in textView.compoundDrawablesRelative) {
            if (drawable != null) {
                drawable.colorFilter =
                    PorterDuffColorFilter(
                        ContextCompat.getColor(textView.context, color),
                        PorterDuff.Mode.SRC_IN
                    )
            }
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

        binding.tvFavoriteEvents.setOnClickListener {
            if(!filterFollowedEvents) {
                binding.tvFavoriteEvents.setTextColor(resources.getColor(R.color.yellow))
                setTextViewDrawableColor(binding.tvFavoriteEvents, R.color.yellow)
                filterFollowedEvents = true
                activeUser.getUser()?.let { it1 -> usersViewModel.loadFollowedEvent(it1.userId) }
            }
            else {
                binding.tvFavoriteEvents.setTextColor(resources.getColor(R.color.white))
                setTextViewDrawableColor(binding.tvFavoriteEvents, R.color.white)
                filterFollowedEvents = false
                eventsViewModel.filterFollowedEvents(false, null)
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