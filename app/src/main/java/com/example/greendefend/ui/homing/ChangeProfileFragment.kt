package com.example.greendefend.ui.homing

import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.example.greendefend.Constants
import com.example.greendefend.databinding.FragmentChangeprofileBinding
import com.example.greendefend.domin.useCase.viewModels.AuthViewModel
import com.example.greendefend.utli.NetworkResult
import dagger.hilt.android.AndroidEntryPoint
import java.io.File


@AndroidEntryPoint
class ChangeProfileFragment : Fragment() {
    private val args: ChangeProfileFragmentArgs by navArgs()
    private lateinit var binding: FragmentChangeprofileBinding
    private val viewModelAccount: AuthViewModel by viewModels()
    private var uriImageSelected: Uri? = null
    private var permissions = arrayOf(android.Manifest.permission.READ_EXTERNAL_STORAGE)
    private val permissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { permissions ->
            val granted = permissions.entries.all { it.value }
            if (granted) {
                selectImage()
            } else {
//                Toast.makeText(requireContext(), "No Permission Granted", Toast.LENGTH_SHORT).show()
            }
        }
    private val resultLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                uriImageSelected = result.data!!.data!!
                binding.imgProfile.setImageURI(uriImageSelected)
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
        intent.type = "image/*"
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


        Glide.with(requireContext()).load(args.userData.imageUrl).into(
            binding.imgProfile
        )
        binding.userdata=args.userData

        binding.imageButtonCamera.setOnClickListener {
            pick()
        }
        binding.btnChange.setOnClickListener {
            binding.progressBar.visibility = View.VISIBLE

            observe(
                Constants.Id, binding.etName.text.toString(),
                binding.etBio.text.toString(),
                binding.etCountry.text.toString(),
                uriImageSelected!!
            )


        }
    }

    private fun observe(id: String, name: String, bio: String, country: String, uri: Uri) {
        viewModelAccount.edit(id, name, bio, country, uri)
        viewModelAccount.response.observe(viewLifecycleOwner) { response ->
            File(requireContext().cacheDir, Constants.fileName).delete()
            Constants.fileName = ""
            when (response) {
                is NetworkResult.Success -> {
                    binding.progressBar.visibility = View.GONE
                    Toast.makeText(requireContext(), "Sucessfull", Toast.LENGTH_SHORT).show()
                    Log.e("result", response.data.toString())
                    Constants.imageUrl = uri
                    Constants.Bio = bio
                    Constants.Name = name
                    findNavController().navigate(ChangeProfileFragmentDirections.actionChangeProfileFragmentToProfileFragment())
                }

                is NetworkResult.Error -> {
                    if (response.code==700){
                        Toast.makeText(requireContext(),response.errMsg, Toast.LENGTH_SHORT).show()
                        ( requireActivity() as HomeActivity).logoutAndObserve()
                    }
                    binding.progressBar.visibility = View.GONE
                    Toast.makeText(requireContext(), response.errMsg.toString(), Toast.LENGTH_SHORT)
                        .show()
                    findNavController().navigate(ChangeProfileFragmentDirections.actionChangeProfileFragmentToProfileFragment())
                }


            }
        }


    }

    override fun onDestroy() {
        (requireActivity() as HomeActivity).binding.toolbar.visibility=View.VISIBLE
        super.onDestroy()
    }

}

