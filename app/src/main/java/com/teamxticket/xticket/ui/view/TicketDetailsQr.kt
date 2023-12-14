package com.teamxticket.xticket.ui.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.viewModelScope
import com.teamxticket.xticket.R
import com.teamxticket.xticket.data.model.Event
import com.teamxticket.xticket.databinding.ActivityPurchaseDetailBinding
import com.teamxticket.xticket.databinding.ActivityTicketDetailsQrBinding
import com.teamxticket.xticket.ui.viewModel.EventViewModel
import com.teamxticket.xticket.ui.viewModel.TicketsViewModel
import kotlinx.coroutines.launch

class TicketDetailsQr : AppCompatActivity() {

    private lateinit var binding: ActivityTicketDetailsQrBinding
    private val ticketsViewModel: TicketsViewModel by viewModels()
    private val eventViewModel : EventViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTicketDetailsQrBinding.inflate(layoutInflater)
        setContentView(binding.root)
        eventViewModel.eventModel.observe(this) { events ->
            // Verificar si hay eventos recibidos
            if (!events.isNullOrEmpty()) {
                val event = events[0]
                val eventId = intent.getIntExtra("EVENT_ID", -1)
                if (eventId != -1) {
                    ticketsViewModel.getTicketData(eventId, -1)
                    updateEventDetails(event)
                    // Utilizar el ID del evento para obtener los detalles del evento
                    // Aquí puedes llamar a la función que use el ID del evento para obtener los datos correspondientes
                } else {
                    // Manejar el caso en el que no se reciba el ID del evento
                }
            }

        }
    }
    fun updateEventDetails(event: Event) {
        binding.eventName.text = event.name
        binding.eventGenre.text = event.genre
        binding.eventLocation.text = event.location
    }


}