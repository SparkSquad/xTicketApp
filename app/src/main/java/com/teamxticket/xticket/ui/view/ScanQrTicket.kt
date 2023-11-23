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

class ScanQrTicket : AppCompatActivity() {

    private lateinit var codeScanner: CodeScanner
    private lateinit var mainLayout: FrameLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_scan_qr_ticket)
        val mainLayout = findViewById<FrameLayout>(R.id.mainLayout)
        val scannerView = findViewById<CodeScannerView>(R.id.scanner_view)
        codeScanner = CodeScanner(this, scannerView)
        val colorFrom = ContextCompat.getColor(this, R.color.red)
        val colorTo = ContextCompat.getColor(this, android.R.color.transparent)

        val anim = ValueAnimator.ofObject(ArgbEvaluator(), colorFrom, colorTo)
        anim.duration = 5000 // milliseconds

        anim.addUpdateListener { valueAnimator ->
            val color = valueAnimator.animatedValue as Int
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                mainLayout.setBackgroundColor(color)
            } else {
                val colorDrawable = ColorDrawable(color)
                mainLayout.setBackgroundDrawable(colorDrawable)
            }
        }

        // Parameters (default values)
        codeScanner.camera = CodeScanner.CAMERA_BACK
        codeScanner.setDecodeCallback { result ->
            runOnUiThread {

                if (result.text.startsWith("xTicket")) {
                    Toast.makeText(this, "Valid Ticket",Toast.LENGTH_SHORT).show()
                    val intent = Intent(this, TicketDetailsQr::class.java)
                    startActivity(intent)
                    finish()

                } else {
                    Toast.makeText(this, "Invalid Ticket",Toast.LENGTH_SHORT).show()
                }
            }
        }
        codeScanner.autoFocusMode = AutoFocusMode.SAFE // or CONTINUOUS
        codeScanner.scanMode = ScanMode.CONTINUOUS // or CONTINUOUS or PREVIEW
        codeScanner.isAutoFocusEnabled = true // Whether to enable auto focus or not
        codeScanner.isFlashEnabled = false // Whether to enable flash or not

        // Callbacks
        codeScanner.decodeCallback = DecodeCallback { result ->
            runOnUiThread {

                if (result.text.startsWith("xTicket")) {
                    val ticketData = parseTicketData(result.text)
                    val intent = Intent(this, TicketDetailsQr::class.java)
                    startActivity(intent)
                    finish()

                } else {
                    Toast.makeText(this, "Invalid Ticket",Toast.LENGTH_SHORT).show()
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

    private fun parseTicketData(ticketText: String): Event? {
        val parts = ticketText.split("/")
        val eventIdIndex = parts.indexOf("EventId")

        // Verifica si "EventId" está presente y si hay un índice siguiente
        if (eventIdIndex != -1 && eventIdIndex + 1 < parts.size) {
            // Intenta convertir el valor a Int
            val eventId: Int? = parts[eventIdIndex + 1].toIntOrNull()

            // Verifica si la conversión fue exitosa
            if (eventId != null) {
                return Event(eventId, "", "", "", "", 0, null, null, null)
            }
        }

        return null
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