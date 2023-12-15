package com.teamxticket.xticket.ui.view

import android.app.Activity
import android.app.KeyguardManager
import android.content.Intent
import android.content.pm.PackageManager
import android.content.res.Resources
import android.hardware.biometrics.BiometricPrompt
import android.os.Build
import android.os.Bundle
import android.os.CancellationSignal
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.teamxticket.xticket.R
import com.teamxticket.xticket.core.ActiveUser
import com.teamxticket.xticket.data.model.TicketData
import com.teamxticket.xticket.databinding.FragmentTicketListBinding
import com.teamxticket.xticket.ui.view.adapter.TicketAdapter
import com.teamxticket.xticket.ui.viewModel.TicketsViewModel

class TicketListFragment : Fragment() {

    private var cancellationSignal: CancellationSignal? = null
    private var ticketData: TicketData? = null
    private val authenticationCallback : BiometricPrompt.AuthenticationCallback
        get() =
            @RequiresApi(Build.VERSION_CODES.P)
            object : BiometricPrompt.AuthenticationCallback() {
                override fun onAuthenticationError(errorCode: Int, errString: CharSequence) {
                    super.onAuthenticationError(errorCode, errString)
                    val content =
                        "xTicket/${ticketData!!.ticket.uuid}/EventId/${ticketData!!.saleDate.eventId}/SaleDateId/${ticketData!!.saleDate.saleDateId}/Tickets/${ticketData!!.ticket.totalTickets}"
                    val fragment = TicketQrFragment.newInstance(content)
                    fragment.show(parentFragmentManager, "ticketQrFragment")
                }

                override fun onAuthenticationSucceeded(result: BiometricPrompt.AuthenticationResult) {
                    super.onAuthenticationSucceeded(result)
                    val content =
                        "xTicket/${ticketData!!.ticket.uuid}/EventId/${ticketData!!.saleDate.eventId}/SaleDateId/${ticketData!!.saleDate.saleDateId}/Tickets/${ticketData!!.ticket.totalTickets}"
                    val fragment = TicketQrFragment.newInstance(content)
                    fragment.show(parentFragmentManager, "ticketQrFragment")
                }

                override fun onAuthenticationFailed() {
                    super.onAuthenticationFailed()
                    Toast.makeText(requireContext(), "Autenticacion fallida", Toast.LENGTH_LONG).show()
                }
            }

    private var _binding: FragmentTicketListBinding? = null
    private val binding get() = _binding!!
    private val ticketsViewModel: TicketsViewModel by viewModels()
    private var activeUser = ActiveUser.getInstance().getUser()

    @RequiresApi(Build.VERSION_CODES.P)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View {
        _binding = FragmentTicketListBinding.inflate(inflater, container, false)
        val rootView = binding.root
        binding.rvTicketsList.layoutManager = LinearLayoutManager(requireContext())

        initListeners()
        initObservables()
        checkBiometricSupport()
        return rootView
    }

    private fun getCancellationSignal() : CancellationSignal{
        cancellationSignal = CancellationSignal()
        cancellationSignal?.setOnCancelListener {
            Toast.makeText(requireContext(), Resources.getSystem().getString(R.string.Cancel_bio_auth), Toast.LENGTH_LONG).show()
        }
        return cancellationSignal as CancellationSignal
    }

    private fun checkBiometricSupport() : Boolean{
        val keyguarManager : KeyguardManager = requireActivity().getSystemService(KeyguardManager::class.java)
        if(!keyguarManager.isKeyguardSecure){
            Toast.makeText(requireContext(), "Sin autenticacion biometrica", Toast.LENGTH_LONG).show()
            return false
        }

        if(ActivityCompat.checkSelfPermission(requireContext(), android.Manifest.permission.USE_BIOMETRIC) != PackageManager.PERMISSION_GRANTED){
            Toast.makeText(requireContext(), "Sin permiso de datos biometricos", Toast.LENGTH_LONG).show()
            return false
        }

        return if(requireActivity().packageManager.hasSystemFeature(PackageManager.FEATURE_FINGERPRINT)){
            true
        }else true
    }

    override fun onResume() {
        super.onResume()
        ticketsViewModel.loadTickets(activeUser!!.userId)
    }

    @RequiresApi(Build.VERSION_CODES.P)
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

    @RequiresApi(Build.VERSION_CODES.P)
    private fun onItemSelected(ticketData: TicketData) {
        this.ticketData = ticketData
        val biometricPrompt = BiometricPrompt.Builder(requireContext())
            .setTitle("Autenticacion requerida")
            .setSubtitle("Escanee su huella")
            .setDescription("Para ver su voleo debe comprobar su identidad")
            .setNegativeButton("Cancelar", requireActivity().mainExecutor) { _, _ ->
                Toast.makeText(
                    requireContext(),
                    "Autenticacion cancelada",
                    Toast.LENGTH_LONG
                ).show()
            }.build()
        biometricPrompt.authenticate(getCancellationSignal(), requireActivity().mainExecutor, authenticationCallback)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
