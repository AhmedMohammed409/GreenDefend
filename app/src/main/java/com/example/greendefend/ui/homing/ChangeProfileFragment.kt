package com.example.greendefend.ui.homing

import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.greendefend.databinding.FragmentChangeprofileBinding
import com.example.greendefend.domin.useCase.AccountViewModel
import com.example.greendefend.utli.getAvailableInternalMemorySize
import com.example.greendefend.utli.getFileSize
import dagger.hilt.android.AndroidEntryPoint
import java.io.File


@AndroidEntryPoint
class ChangeProfileFragment : Fragment() {
    private lateinit var binding: FragmentChangeprofileBinding
    private val viewModelAccount: AccountViewModel by viewModels ()
    private  var selectedfile:Uri?=null
    private var permissions = arrayOf(android.Manifest.permission.READ_EXTERNAL_STORAGE)
    private val permissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { permissions ->
            val granted = permissions.entries.all { it.value }
            if (granted) {
                selectImage()
            } else {
                Toast.makeText(requireContext(), "No Permission Granted", Toast.LENGTH_SHORT).show()
            }
        }
    private val resultLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                 selectedfile = result.data!!.data!!
                binding.imgProfile.setImageURI(selectedfile)
            } else {
                Toast.makeText(
                    requireContext(),
                    "Not selected or diferent type",
                    Toast.LENGTH_SHORT
                )
                    .show()
            }


        }

    private fun selectImage() {
        val intent = Intent(Intent.ACTION_GET_CONTENT)
        intent.type = "*/*"
        resultLauncher.launch(intent)
    }

    private fun hasPermission(permissions: Array<String>): Boolean =
        permissions.all {
            ActivityCompat.checkSelfPermission(
                requireContext(),
                it
            ) == PackageManager.PERMISSION_GRANTED
        }

    private fun pick() {
        if (hasPermission(permissions)) {
            selectImage()
        } else {
            permissionLauncher.launch(permissions)
        }

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentChangeprofileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.imgProfile.setOnClickListener {
            pick()
        }
        binding.btnChange.setOnClickListener {
            binding.progressBar.visibility=View.VISIBLE
            uplaoadAndObserve(
                binding.etName.text.toString(),
                binding.etBio.text.toString(),
              "Egypt",
                selectedfile!!

            )
        }
    }

    private fun uplaoadAndObserve(fullName:String,bio:String,country:String,fileUri: Uri) {

        viewModelAccount.editProfile("0bd6d620-912e-410a-91d6-d8c9d424265c",
            fullName,bio,"Eg",fileUri)
        viewModelAccount.serverResponse.observe(viewLifecycleOwner){
            if (it.isNotEmpty()){
                Toast.makeText(requireContext(),it,Toast.LENGTH_LONG).show()
                binding.progressBar.visibility=View.GONE
                viewModelAccount.rest()
            }

        }
        viewModelAccount.connectionError.observe(viewLifecycleOwner){
            Toast.makeText(requireContext(),it,Toast.LENGTH_LONG).show()
            binding.progressBar.visibility=View.GONE
            viewModelAccount.rest()
        }
    }

    }

