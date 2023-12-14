package com.teamxticket.xticket.ui.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Gravity
import androidx.activity.viewModels
import androidx.core.view.isVisible
import com.teamxticket.xticket.R
import com.teamxticket.xticket.core.ActiveUser
import com.teamxticket.xticket.data.model.OneTimeUseCode
import com.teamxticket.xticket.databinding.ActivitySendOneTimeUseCodeBinding
import com.teamxticket.xticket.ui.viewModel.UserViewModel
import com.thecode.aestheticdialogs.AestheticDialog
import com.thecode.aestheticdialogs.DialogAnimation
import com.thecode.aestheticdialogs.DialogStyle
import com.thecode.aestheticdialogs.DialogType

class SendOneTimeUseCodeActivity : AppCompatActivity() {

    private lateinit var binding : ActivitySendOneTimeUseCodeBinding
    private val userViewModel : UserViewModel by viewModels()
    private var activeUser = ActiveUser.getInstance()
    private lateinit var email : String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySendOneTimeUseCodeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        email = intent.getStringExtra("email").toString()
        initObservables()
        initListeners()
    }

    private fun initListeners(){
        binding.btnSignInFr.setOnClickListener(){
            val code = OneTimeUseCode(email , binding.etCode.text.toString())

            if(binding.etCode.text.isNotEmpty()){
                userViewModel.searchUserByCode(code)
            } else {
                AestheticDialog.Builder(this, DialogStyle.FLAT, DialogType.ERROR)
                    .setTitle(getString(R.string.emptyField))
                    .setMessage("Por favor ingrese el código")
                    .setCancelable(true)
                    .setDarkMode(activeUser.getDarkMode())
                    .setGravity(Gravity.CENTER)
                    .setAnimation(DialogAnimation.SHRINK).show()
            }
        }
    }

    private fun initObservables(){
        userViewModel.receivedUser.observe(this) {
            if (it.token?.isNotEmpty() == true) {
                if (it.user?.email?.isEmpty() == false) {
                    if(it.user?.type == "assistant" || it.user?.type == "admin") {
                        Intent (this, AssistantMenuActivity::class.java).apply {
                            finish()
                            startActivity(this)
                        }
                    } else if (it.user?.type == "eventPlanner") {
                        Intent (this, EventPlannerMenuActivity::class.java).apply {
                            finish()
                            startActivity(this)
                        }
                    }
                }
            } else {
                AestheticDialog.Builder(this, DialogStyle.FLAT, DialogType.ERROR)
                    .setTitle(getString(R.string.notFound))
                    .setMessage(getString(R.string.invalidUser))
                    .setCancelable(true)
                    .setDarkMode(activeUser.getDarkMode())
                    .setGravity(Gravity.CENTER)
                    .setAnimation(DialogAnimation.SHRINK).show()
            }
        }

        userViewModel.showLoader.observe(this) {
            binding.overlayView.isVisible = it
            binding.progressBar.isVisible = it
        }
    }
}