package com.example.greendefend.ui.homing

import android.app.Activity
import android.content.Context
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
import com.example.greendefend.Constants
import com.example.greendefend.databinding.FragmentAskingBinding
import com.example.greendefend.domin.useCase.viewModels.ForumViewModel
import com.example.greendefend.utli.NetworkResult
import dagger.hilt.android.AndroidEntryPoint
import java.io.File

@AndroidEntryPoint
class AskingFragment : Fragment() {
   private lateinit var binding: FragmentAskingBinding
    private  var selectedfile: Uri? =null
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
    private val forumViewModel: ForumViewModel by viewModels()

    private fun selectImage() {
        val intent = Intent(Intent.ACTION_GET_CONTENT)
        intent.type = "image/*"
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
           addPostAndObserve(binding.etPost.text.toString(),selectedfile)

        }


    }

    private fun addPostAndObserve(post:String, uri: Uri?){

        forumViewModel.addPost(Constants.Id,post,uri)

        forumViewModel.response.observe(viewLifecycleOwner){response->
            File(requireContext().cacheDir,Constants.fileName).delete()
            Constants.fileName=""
            when(response){
                is NetworkResult.Success -> {
                    binding.progressBar.visibility = View.GONE
                    Toast.makeText(requireContext(),"Sucess add post", Toast.LENGTH_LONG).show()
                    Log.e("sucess add post ", response.toString())
                    findNavController().navigate(AskingFragmentDirections.actionAskingFragmentToForumFragment())
                }

                is NetworkResult.Error -> {
                    binding.progressBar.visibility = View.GONE
                    Log.e("MsgErr Error", response.toString())
                    Toast.makeText(requireContext(), response.errMsg.toString(), Toast.LENGTH_LONG).show()
                    findNavController().navigate(AskingFragmentDirections.actionAskingFragmentToForumFragment())
                }
                is NetworkResult.Exception -> {
                    binding.progressBar.visibility = View.GONE
                    Log.e("MsgErr Exeption", response.e.message.toString())
                    Toast.makeText(
                        requireContext(),
                        response.e.message.toString(),
                        Toast.LENGTH_LONG
                    ).show()
                    findNavController().navigate(AskingFragmentDirections.actionAskingFragmentToForumFragment())
                }

            }

        }

    }

    override fun onAttach(context: Context) {
        (requireActivity() as HomeActivity).binding.toolbar.visibility=View.GONE
        super.onAttach(context)
    }

    override fun onDestroy() {
        (requireActivity() as HomeActivity).binding.toolbar.visibility=View.VISIBLE
        super.onDestroy()
    }

}