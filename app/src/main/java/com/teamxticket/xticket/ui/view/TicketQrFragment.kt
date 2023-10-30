package com.teamxticket.xticket.ui.view

import android.R
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import com.google.zxing.BarcodeFormat
import com.journeyapps.barcodescanner.BarcodeEncoder
import com.teamxticket.xticket.databinding.FragmentTicketQrBinding


class TicketQrFragment : DialogFragment() {

    private lateinit var binding: FragmentTicketQrBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentTicketQrBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initListeners()
        val args = arguments
        if (args != null) {
            val content = args.getString("content")
            try {
                val barcodeEncoder = BarcodeEncoder()
                val bitmap = barcodeEncoder.encodeBitmap(content, BarcodeFormat.QR_CODE, 400, 400)
                binding.qrImage.setImageBitmap(bitmap)
            } catch (e: Exception) {
                Toast.makeText(context, e.message, Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun initListeners() {
        binding.btnClose.setOnClickListener {
            dismiss()
        }
    }
    fun closeDialog(view: View) {
        dialog?.dismiss()
    }
    companion object {
        fun newInstance(qrCode: String): TicketQrFragment {
            val fragment = TicketQrFragment()
            val args = Bundle()
            args.putString("content", qrCode)
            fragment.arguments = args
            return fragment
        }
    }
}
