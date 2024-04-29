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
import androidx.navigation.fragment.findNavController
import com.example.greendefend.Constants
import com.example.greendefend.databinding.FragmentAskingBinding
import com.example.greendefend.domin.useCase.ForumViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AskingFragment : Fragment() {
   private lateinit var binding: FragmentAskingBinding
    private lateinit var selectedfile:Uri
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
            if (result.resultCode== Activity.RESULT_OK){
                 selectedfile = result.data!!.data!!
                binding.imgPost.setImageURI(selectedfile)
                binding.btnAddImage.visibility=View.GONE
            }else{
                Toast.makeText(requireContext(), "Not selected or diferent type", Toast.LENGTH_SHORT)
                    .show()
            }


        }
    private val viewModelFourm: ForumViewModel by viewModels()

    private fun selectImage() {
        val intent = Intent(Intent.ACTION_GET_CONTENT)
        intent.type = "*/*"
        resultLauncher.launch(intent)
    }

    private fun hasPermission(permissions: Array<String>): Boolean =
        permissions.all {
            ActivityCompat.checkSelfPermission(requireContext(), it) == PackageManager.PERMISSION_GRANTED
        }

    private fun pick() {
        if (hasPermission(permissions)){
            selectImage()
        }
        else{ permissionLauncher.launch(permissions)
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
        binding= FragmentAskingBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnAddImage.setOnClickListener { pick() }
        binding.btnSend.setOnClickListener {
            binding.progressBar.visibility=View.VISIBLE
           addpostAndObserve(binding.etPost.text.toString(),selectedfile)

        }


    }

    private fun addpostAndObserve(post:String, uri: Uri?){
        viewModelFourm.addPost(Constants.Id,post,uri!!)
        viewModelFourm.serverResponse.observe(viewLifecycleOwner){
            binding.progressBar.visibility=View.GONE
            Toast.makeText(requireContext(),it,Toast.LENGTH_SHORT).show()
            findNavController().navigate(AskingFragmentDirections.actionAskingFragmentToForumFragment())
        }
    }



}