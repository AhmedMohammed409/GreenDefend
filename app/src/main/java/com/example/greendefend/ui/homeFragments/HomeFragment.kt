package com.example.greendefend.ui.homeFragments

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.fragment.app.Fragment
import com.example.greendefend.R
import com.example.greendefend.databinding.FragmentHomeBinding
import com.karumi.dexter.Dexter
import com.karumi.dexter.MultiplePermissionsReport
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.multi.MultiplePermissionsListener


class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding
//    private var launcher=registerForActivityResult(ActivityResultContracts.GetContent()){
//        binding.layoutUpload.visibility=View.GONE
//        binding.imgCamera.visibility=View.VISIBLE
//        binding.imgCamera.setImageURI(it)
//    }

        @Suppress("DEPRECATION")
        private var launcher=registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
            if (it.resultCode==Activity.RESULT_OK){
                binding.layoutUpload.visibility=View.GONE
                binding.imgCamera.visibility=View.VISIBLE
                binding.imgCamera.setImageBitmap(it.data!!.extras!!.get("data") as Bitmap?)
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
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        val actiotogle = ActionBarDrawerToggle(
            requireActivity(),
            binding.drawer,
            binding.toolbar,
            R.string.open,
            R.string.close
        )
        binding.drawer.addDrawerListener(actiotogle)


        binding.btnUpload.setOnClickListener {

            Dexter.withContext(requireContext()).withPermissions(
               android.Manifest.permission.CAMERA,
                android.Manifest.permission.READ_EXTERNAL_STORAGE).withListener(object : MultiplePermissionsListener{
                override fun onPermissionsChecked(p0: MultiplePermissionsReport?) =
                    if (p0!!.areAllPermissionsGranted()){

//                        launcher.launch("image/*")

                        val intentCamera= Intent(MediaStore.ACTION_IMAGE_CAPTURE)
                        launcher.launch(intentCamera)


                    }
                    else{
                        Toast.makeText(requireContext(),
                            getString(R.string.please_allow_permission_on_camera),Toast.LENGTH_SHORT).show()
                    }
                override fun onPermissionRationaleShouldBeShown(
                    p0: MutableList<PermissionRequest>?,
                    p1: PermissionToken?
                ) {
                   p1!!.continuePermissionRequest()
                }
            }).check()
        }

        binding.imgMoreExpand.setOnClickListener {
            val visability=if (binding.layoutExpand.visibility==View.GONE){ View.VISIBLE }else{View.GONE}
            binding.layoutExpand.visibility=visability
        }
    }

}