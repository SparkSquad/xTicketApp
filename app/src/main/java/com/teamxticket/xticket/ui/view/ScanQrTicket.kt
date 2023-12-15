package com.teamxticket.xticket.ui.view

import android.animation.ArgbEvaluator
import android.animation.ValueAnimator
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.FrameLayout
import android.widget.Toast
import androidx.activity.viewModels
import androidx.core.content.ContextCompat
import com.budiyev.android.codescanner.AutoFocusMode
import com.budiyev.android.codescanner.CodeScanner
import com.budiyev.android.codescanner.CodeScannerView
import com.budiyev.android.codescanner.DecodeCallback
import com.budiyev.android.codescanner.ErrorCallback
import com.budiyev.android.codescanner.ScanMode
import com.teamxticket.xticket.R
import com.teamxticket.xticket.data.model.Event
import com.teamxticket.xticket.data.model.TicketData
import com.teamxticket.xticket.ui.viewModel.EventViewModel

class ScanQrTicket : AppCompatActivity() {

    private lateinit var codeScanner: CodeScanner
    private val eventViewModel : EventViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_scan_qr_ticket)
        val scannerView = findViewById<CodeScannerView>(R.id.scanner_view)
        codeScanner = CodeScanner(this, scannerView)


        // Parameters (default values)
        codeScanner.camera = CodeScanner.CAMERA_BACK // or CAMERA_FRONT or specific camera id
        codeScanner.formats = CodeScanner.ALL_FORMATS // list of type BarcodeFormat,
        // ex. listOf(BarcodeFormat.QR_CODE)
        codeScanner.autoFocusMode = AutoFocusMode.SAFE // or CONTINUOUS
        codeScanner.scanMode = ScanMode.CONTINUOUS // or CONTINUOUS or PREVIEW
        codeScanner.isAutoFocusEnabled = true // Whether to enable auto focus or not
        codeScanner.isFlashEnabled = false // Whether to enable flash or not

        // Callbacks
        codeScanner.decodeCallback = DecodeCallback { result ->
            runOnUiThread {
                if (result.text.startsWith("xTicket")) {
                    val parts = result.text.split("/")
                        val eventId = parts[3].toIntOrNull()
                        val saleDateId = parts[5].toIntOrNull()
                        val totalTickets = parts[7].toIntOrNull()
                    Toast.makeText(this, eventId.toString(), Toast.LENGTH_SHORT).show()

                        if (eventId != null && saleDateId != null) {
                            val intent = Intent(this, TicketDetailsQr::class.java)
                            intent.putExtra("EVENT_ID", eventId)
                            intent.putExtra("SALE_DATE_ID", saleDateId)
                            intent.putExtra("TICKETS", totalTickets)
                            startActivity(intent)
                        } else {
                            Toast.makeText(this, "Invalid Event ID or Sale Date ID", Toast.LENGTH_SHORT).show()
                        }

                } else {
                    Toast.makeText(this, "Invalid Ticket", Toast.LENGTH_SHORT).show()
                }
            }
        }

        codeScanner.errorCallback = ErrorCallback { // or ErrorCallback.SUPPRESS
            runOnUiThread {
                Toast.makeText(this, "Camera initialization error: ${it.message}",
                    Toast.LENGTH_LONG).show()
            }
        }

        scannerView.setOnClickListener {
            codeScanner.startPreview()
        }
    }

    override fun onResume() {
        super.onResume()
        codeScanner.startPreview()
    }

    override fun onPause() {
        codeScanner.releaseResources()
        super.onPause()
    }
}