package com.teamxticket.xticket.ui.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.teamxticket.xticket.R
import com.teamxticket.xticket.databinding.ActivityPurchaseDetailBinding

class PurchaseDetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityPurchaseDetailBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPurchaseDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)


    }
}