package com.teamxticket.xticket.ui.view

import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.ImageButton
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.teamxticket.xticket.R
import com.teamxticket.xticket.data.model.BandArtistProvider
import com.teamxticket.xticket.data.model.Event
import com.teamxticket.xticket.databinding.FragmentEventDetailBinding
import com.teamxticket.xticket.ui.view.adapter.BandArtistAdapter
import com.teamxticket.xticket.ui.viewModel.EventViewModel

class EventDetailFragment : Fragment() {
    private var _binding: FragmentEventDetailBinding? = null
    private val binding get() = _binding!!
    private val eventViewModel: EventViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentEventDetailBinding.inflate(inflater, container, false)

        val adapter = BandArtistAdapter(BandArtistProvider.bandArtistList)

        initSpinnerMusicalGenres()
        initRecyclerViewBandsAndArtists(adapter)
        setupValidationOnFocusChange(binding.eventName)
        setupValidationOnFocusChange(binding.eventDescription)
        setupValidationOnFocusChange(binding.eventLocation)
        setupBtnAddBandOrArtist(adapter)
        setupBtnCreateEvent()
        initObservables()

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun setupBtnAddBandOrArtist(adapter: BandArtistAdapter) {
        binding.btnAddBandOrArtist.setOnClickListener {
            val bandArtistName = binding.bandOrArtist.text.toString().replace("\\s+".toRegex(), " ").uppercase().trim()

            if (bandArtistName.isEmpty()) {
                setElementView(binding.bandOrArtist, binding.btnAddBandOrArtist, true, getString(R.string.emptyField))

            } else if(BandArtistProvider.bandArtistList.any { it == bandArtistName }) {
                setElementView(binding.bandOrArtist, binding.btnAddBandOrArtist, true, getString(R.string.bandOrArtistAlreadyAdded))

            } else {
                setElementView(binding.bandOrArtist, binding.btnAddBandOrArtist, false, "")
                adapter.addItem(bandArtistName)
                binding.bandOrArtist.text.clear()

            }
        }
    }

    private fun setupBtnCreateEvent() {
        binding.btnSaveEvent.setOnClickListener {
            val eventName = binding.eventName.text.toString().replace("\\s+".toRegex(), " ").uppercase().trim()
            val musicalGenres = binding.musicalGenres
            val eventDescription = binding.eventDescription.text.toString().replace("\\s+".toRegex(), " ").uppercase().trim()
            val eventLocation = binding.eventLocation.text.toString().replace("\\s+".toRegex(), " ").uppercase().trim()
            val bandsAndArtists = BandArtistProvider.bandArtistList

            setElementView(binding.eventName, eventName.isEmpty(), getString(R.string.emptyField))
            setElementView(binding.musicalGenres, (musicalGenres.selectedItemPosition == 0), getString(R.string.emptyField))
            setElementView(binding.eventDescription, eventDescription.isEmpty(), getString(R.string.emptyField))
            setElementView(binding.eventLocation, eventLocation.isEmpty(), getString(R.string.emptyField))
            setElementView(binding.bandOrArtist, binding.btnAddBandOrArtist, bandsAndArtists.isEmpty(), getString(R.string.emptyBandOrArtistList))

            if (eventName.isEmpty() || musicalGenres.selectedItemPosition == 0 || eventDescription.isEmpty() || eventLocation.isEmpty() || bandsAndArtists.isEmpty()) {
                Toast.makeText(activity, getString(R.string.emptyFields), Toast.LENGTH_SHORT).show()

            } else {
                // TODO: Mandar id de usuario usando Shingleton
                val event = Event(0, eventName, musicalGenres.selectedItem.toString(), eventDescription, eventLocation, 1, bandsAndArtists)
                // TODO: Llamar a actualizar evento
                //eventViewModel.registerEvent(event)
            }
        }

        binding.btnManageTickets.setOnClickListener {
            val fragmentManager = requireActivity().supportFragmentManager
            val fragmentTransaction = fragmentManager.beginTransaction()
            /*
            fragmentTransaction.replace(
                binding.root.id,
                ManageSaleDateFragment()
            ).addToBackStack(null).commit()
            */
        }

        binding.btnRegisterTicketTaker.setOnClickListener {
            val fragmentManager = requireActivity().supportFragmentManager
            val fragmentTransaction = fragmentManager.beginTransaction()
            /*
            fragmentTransaction.replace(
                binding.root.id,
                RegisterTicketTakerFragment()
            ).addToBackStack(null).commit()
            */
        }

        binding.btnCancelEvent.setOnClickListener {
            // TODO: Cancelar evento
        }
    }

    private fun initObservables() {
        eventViewModel.showLoaderRegister.observe(viewLifecycleOwner) { visible ->
            binding.progressBar.isVisible = visible
            binding.overlayView.isVisible = visible
        }

        eventViewModel.successfulRegister.observe(viewLifecycleOwner) { result ->
            if (result == -1) {
                Toast.makeText(activity, getString(R.string.eventWasNotCreated), Toast.LENGTH_SHORT).show()

            } else {
                Toast.makeText(activity, getString(R.string.eventCreated), Toast.LENGTH_SHORT).show()
                // TODO: Go to Manage Sale Date Fragment
                /*
                Intent(this, ManageSaleDateActivity::class.java).apply {
                    putExtra("eventId", result)
                    startActivity(this)
                }*/

            }
        }
    }

    private fun setElementView(editText: EditText, isError: Boolean, message: String) {
        val errorColor = if (isError) Color.RED else Color.BLACK

        editText.apply {
            error = if (isError) message else null
            setHintTextColor(errorColor)
            backgroundTintList = ColorStateList.valueOf(errorColor)
        }
    }

    private fun setElementView(spinner: Spinner, isError: Boolean, message: String) {
        val selectedView = spinner.selectedView as TextView
        val errorColor = if (isError) Color.RED else Color.BLACK
        val backgroundResource = if (isError) R.drawable.spinner_border_on_error else R.drawable.spinner_border

        selectedView.apply {
            error = if (isError) message else null
            setTextColor(errorColor)
        }
        spinner.background = AppCompatResources.getDrawable(requireContext(), backgroundResource)
    }

    private fun setElementView(editText: EditText, button: ImageButton, isError: Boolean, message: String) {
        if(isError) {
            editText.apply {
                error = message
                setHintTextColor(Color.RED)
                backgroundTintList = ColorStateList.valueOf(Color.RED)
            }
            button.backgroundTintList = ColorStateList.valueOf(Color.RED)
        } else {
            editText.apply {
                error = null
                setHintTextColor(Color.GRAY)
                backgroundTintList = ColorStateList.valueOf(Color.BLACK)
            }
            button.backgroundTintList = ColorStateList.valueOf(resources.getColor(R.color.darkBlue, null))
        }
    }

    private fun setupValidationOnFocusChange(editText: EditText) {
        editText.onFocusChangeListener = View.OnFocusChangeListener { _, hasFocus ->
            if (!hasFocus) {
                val inputText = editText.text.toString().replace("\\s+".toRegex(), " ").uppercase().trim()
                setElementView(editText, inputText.isEmpty(), getString(R.string.emptyField))
            }
        }
    }

    override fun onResume() {
        super.onResume()
        eventViewModel.loadGenres()
        initSpinnerMusicalGenres()
    }

    override fun onPause() {
        super.onPause()
        BandArtistProvider.bandArtistList.clear()
    }

    private fun initSpinnerMusicalGenres() {
        val musicalGenres: ArrayList<String> = ArrayList()
        musicalGenres.add(getString(R.string.pickMusicalGenre))

        eventViewModel.genresModel.observe(viewLifecycleOwner) { genres ->
            genres?.forEach { musicalGenres.add(it) }
        }
        eventViewModel.showLoaderGenres.observe(viewLifecycleOwner) { visible ->
            binding.progressBar.isVisible = visible
            binding.overlayView.isVisible = visible
        }

        val arrayAdapter = ArrayAdapter(requireContext(), R.layout.spinner_item, musicalGenres)
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        binding.musicalGenres.adapter = arrayAdapter

        binding.musicalGenres.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
                (binding.musicalGenres.selectedView as TextView).error = null
                if(position == 0)
                    (binding.musicalGenres.selectedView as TextView).setTextColor(Color.GRAY)
                else
                    (binding.musicalGenres.selectedView as TextView).setTextColor(Color.BLACK)
                binding.musicalGenres.background = AppCompatResources.getDrawable(requireContext(), R.drawable.spinner_border)
            }
            override fun onNothingSelected(parent: AdapterView<*>) {

            }
        }
    }

    private fun initRecyclerViewBandsAndArtists(adapter: BandArtistAdapter) {
        binding.recyclerBandsAndArtists.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerBandsAndArtists.adapter = adapter
    }
}