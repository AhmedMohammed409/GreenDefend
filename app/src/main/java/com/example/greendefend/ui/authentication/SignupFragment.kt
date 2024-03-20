package com.example.greendefend.ui.authentication

import android.os.Bundle
import android.util.Patterns
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.text.isDigitsOnly
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.greendefend.databinding.FragmentSignupBinding
import java.util.Calendar


class SignupFragment : Fragment() {
    private lateinit var binding: FragmentSignupBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {}
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentSignupBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.btnSend.setOnClickListener {
            validationAll()
        }
    }

    private fun validationAll() {
        if (
            validationAllName(
                binding.etFirstName.text.toString(),
                binding.etSecondName.text.toString(),
                binding.etLastName.text.toString()) &&
            validationNumberPhone(binding.etPhoneNumber.text.toString()) &&
            validationAge(binding.dataPacker.year) &&
            validationPassword(binding.etPassword.text.toString())
        ) {
            Toast.makeText(requireContext(), "Sucessful", Toast.LENGTH_SHORT).show()
            findNavController().navigate(SignupFragmentDirections.actionSignupFragmentToEntercodeFragment())
        } else {
            Toast.makeText(requireContext(), "Failed", Toast.LENGTH_SHORT).show()
        }
    }

    private fun validationNumberPhone(number: String): Boolean {
        return if ( Patterns.PHONE.matcher(number).matches()) {
            binding.TextInputLayoutPhone.isErrorEnabled = false
            true
        } else{
            binding.TextInputLayoutPhone.error = "Please Enter Correct Number"
            false
        }
    }

    private fun validationAllName(firstName: String, middleName: String, lastName: String): Boolean {
        var result = true
        if (validationName(firstName)) {
            binding.TextInputLayoutFirstName.error = "Please Enter Correct First Name"
        } else {
            binding.TextInputLayoutFirstName.isErrorEnabled = false
            result = false
        }
        if (validationName(middleName)) {
            binding.TextInputLayoutMiddleName.error = "Please Enter Correct Middle Name"
        } else {
            binding.TextInputLayoutMiddleName.isErrorEnabled = false
            result = false
        }
        if (validationName(lastName)) {
            binding.TextInputLayoutLastName.error = "Please Enter Correct Last Name"
        } else {
            binding.TextInputLayoutLastName.isErrorEnabled = false
            result = false
        }
        return result
    }

    private fun validationName(name: String): Boolean {
        return (name.contains("[0-9]".toRegex()) || name.isEmpty())
    }

    private fun validationPassword(password: String): Boolean {
        if (password.length < 8 &&
            password.firstOrNull { !it.isLetterOrDigit() } == null &&
            password.firstOrNull { it.isDigit() } == null &&
            password.filter { it.isLetter() }.firstOrNull { it.isUpperCase() } == null &&
            password.filter { it.isLetter() }.firstOrNull { it.isLowerCase() } == null
        ) {
            binding.TextInputLayoutPassword.error =
                "please enter Correct password contain number and symbols and minimum 8 max 24 "
            return false
        } else if (password !== binding.etConfirm.text.toString()) {
            binding.TextInputLayoutPassword.isErrorEnabled = false
            binding.TextInputLayoutConfirm.error = "please enter Confirm same Password"

            return false
        }
        binding.TextInputLayoutPassword.isErrorEnabled = false
        binding.TextInputLayoutConfirm.isErrorEnabled = false
        return true

    }

    private fun validationAge(yearOfBirth: Int): Boolean {
        val age = Calendar.getInstance().get(Calendar.YEAR) - yearOfBirth
        if (age < 10) {
            Toast.makeText(
                requireContext(),
                "The age must be more than 10 year",
                Toast.LENGTH_SHORT
            ).show()
            return false
        }
        return true

    }

}
   

