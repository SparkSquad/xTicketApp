package com.teamxticket.xticket.ui.view

import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Bundle
import android.util.Patterns
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.viewModels
import com.teamxticket.xticket.R
import com.teamxticket.xticket.core.ActiveUser
import com.teamxticket.xticket.data.model.User
import com.teamxticket.xticket.databinding.FragmentProfileBinding
import com.teamxticket.xticket.ui.viewModel.UserViewModel
import java.util.regex.Pattern

class ProfileFragment : Fragment() {

    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!
    private val userViewModel: UserViewModel by viewModels()
    private val activeUser = ActiveUser.getInstance().getUser()!!
    private lateinit var newUserData: User

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        val rootView = binding.root

        setupValidationOnFocusChange(binding.userName)
        setupValidationOnFocusChange(binding.userLastName)
        setupValidationOnFocusChange(binding.userEmail)
        setUserData()
        setUpBtnUpdateProfile()
        initObservables()

        return rootView
    }

    private fun setUserData() {
        binding.userName.setText(activeUser.name)
        binding.userLastName.setText(activeUser.surnames)
        binding.userEmail.setText(activeUser.email)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun setupValidationOnFocusChange(editText: EditText) {
        editText.onFocusChangeListener = View.OnFocusChangeListener { _, hasFocus ->
            if (!hasFocus) {
                val inputText = editText.text.toString().replace("\\s+".toRegex(), " ").uppercase().trim()
                setElementView(editText, inputText.isEmpty(), getString(R.string.emptyField))
            }
        }
    }

    private fun setElementView(editText: EditText, isError: Boolean, message: String) {
        editText.apply {
            if(isError) {
                error = message
                setHintTextColor(Color.RED)
                backgroundTintList = ColorStateList.valueOf(Color.RED)
            } else {
                error = null
                setHintTextColor(Color.GRAY)
                backgroundTintList = ColorStateList.valueOf(Color.BLACK)
            }
        }
    }

    private fun setUpBtnUpdateProfile() {
        binding.btnSaveProfile.setOnClickListener {
            val userName = binding.userName.text.toString().replace("\\s+".toRegex(), " ").trim()
            val userLastName = binding.userLastName.text.toString().replace("\\s+".toRegex(), " ").trim()
            val userEmail = binding.userEmail.text.toString().replace("\\s+".toRegex(), " ").trim()
            val userPassword = binding.userPassword.text.toString()
            val userPasswordConfirmation = binding.userPasswordConfirmation.text.toString()

            if(validateFields(userName, userLastName, userEmail, userPassword, userPasswordConfirmation)) {
                newUserData = activeUser.copy(
                    name = userName,
                    surnames = userLastName,
                    email = userEmail,
                    password = userPassword
                )
                userViewModel.updateUser(newUserData)
            }
        }
    }

    private fun validateFields(userName: String, userLastName: String, userEmail: String, userPassword: String, userPasswordConfirmation: String): Boolean {
        if (userName.isEmpty() || userLastName.isEmpty() || userEmail.isEmpty()) {
            setElementView(binding.userName, userName.isEmpty(), getString(R.string.emptyField))
            setElementView(binding.userLastName, userLastName.isEmpty(), getString(R.string.emptyField))
            setElementView(binding.userEmail, userEmail.isEmpty(), getString(R.string.emptyField))
            Toast.makeText(context, getString(R.string.indicatedEmptyFields), Toast.LENGTH_SHORT).show()
            return false

        } else if(!Patterns.EMAIL_ADDRESS.matcher(userEmail).matches()) {
            setElementView(binding.userEmail, true, getString(R.string.invalidEmail))
            return false

        }

        if(userPassword.isNotEmpty()) {
            if(!isValidPassword(userPassword)) {
                setElementView(binding.userPassword, true, getString(R.string.invalidPassword))
                return false

            } else if (userPassword != userPasswordConfirmation) {
                setElementView(binding.userPassword, true, getString(R.string.passwordsDontMatch))
                setElementView(binding.userPasswordConfirmation, true, getString(R.string.passwordsDontMatch))
                return false

            }
        }

        setElementView(binding.userName, false, "")
        setElementView(binding.userLastName, false, "")
        setElementView(binding.userEmail, false, "")
        setElementView(binding.userPassword, false, "")
        setElementView(binding.userPasswordConfirmation, false, "")

        return true
    }

    private fun isValidPassword(password: String): Boolean {
        val passwordRegex = "^" +
                "(?=.*[0-9])" +
                "(?=.*[a-z])" +
                "(?=.*[A-Z])" +
                "(?=.*[@#$%^&+=.])" +
                "(?=\\S+$)" +
                ".{6,}" +
                "$"
        val passwordPattern = Pattern.compile(passwordRegex)
        return passwordPattern.matcher(password).matches()
    }

    private fun initObservables() {
        userViewModel.successfulUpdate.observe(viewLifecycleOwner) { result ->
            if (result == -1) {
                Toast.makeText(context, getString(R.string.profile_not_updated), Toast.LENGTH_SHORT).show()

            } else {
                Toast.makeText(context, getString(R.string.profile_updated), Toast.LENGTH_SHORT).show()
                ActiveUser.getInstance().setUser(newUserData)

            }
        }
    }
}