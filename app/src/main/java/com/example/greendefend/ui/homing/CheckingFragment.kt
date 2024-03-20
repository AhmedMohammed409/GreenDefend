package com.example.greendefend.ui.homing

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import com.example.greendefend.R
import com.example.greendefend.databinding.FragmentCheckingBinding
import com.karumi.dexter.Dexter
import com.karumi.dexter.MultiplePermissionsReport
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.multi.MultiplePermissionsListener

class CheckingFragment : Fragment() {


    private val getContent =
        registerForActivityResult(ActivityResultContracts.GetContent())
        {

            if (it!=null) {

                binding.imgCamera.setImageURI(it)
            }
            else{
                Toast.makeText(requireContext(),"Found error",Toast.LENGTH_SHORT).show()
            }
        }

    private lateinit var binding:FragmentCheckingBinding
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
         binding=FragmentCheckingBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        cheekPermission()
    }

    fun showCustomDialog() {
        val dialog = Dialog(requireContext())
        dialog.setContentView(R.layout.cutom_dialog)
        dialog.setCancelable(false)
        val btnGo = dialog.findViewById<Button>(R.id.btn_go)
        btnGo.setOnClickListener {
            getContent.launch("image/*")
            dialog.dismiss()
        }
        dialog.show()
    }
    private fun cheekPermission(){
        Dexter.withContext(requireContext()).withPermissions(
            android.Manifest.permission.CAMERA,
            android.Manifest.permission.READ_EXTERNAL_STORAGE
        ).withListener(object : MultiplePermissionsListener {
            override fun onPermissionsChecked(p0: MultiplePermissionsReport?) =
                if (p0!!.areAllPermissionsGranted()) {
                    showCustomDialog()

                } else {
                    Toast.makeText(
                        requireContext(),
                        getString(R.string.please_allow_permission_on_camera),
                        Toast.LENGTH_SHORT
                    ).show()
                }

            override fun onPermissionRationaleShouldBeShown(
                p0: MutableList<PermissionRequest>?,
                p1: PermissionToken?
            ) {
                p1!!.continuePermissionRequest()
            }
        }).check()
    }



}