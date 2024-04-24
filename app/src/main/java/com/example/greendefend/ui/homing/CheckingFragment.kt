package com.example.greendefend.ui.homing

import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.greendefend.databinding.FragmentCheckingBinding
import org.tensorflow.lite.DataType
import org.tensorflow.lite.support.image.ImageProcessor
import org.tensorflow.lite.support.image.TensorImage
import org.tensorflow.lite.support.image.ops.ResizeOp
import org.tensorflow.lite.support.tensorbuffer.TensorBuffer

class CheckingFragment : Fragment() {
    private lateinit var bitmap: Bitmap
    private  var uri: Uri?=null
    private val listDisease by lazy {
        requireActivity().assets.open("label2.txt").bufferedReader().readLines()
    }
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
                uri = result.data!!.data!!
                bitmap = MediaStore.Images.Media.getBitmap(requireActivity().contentResolver, uri)
                binding.imgCamera.setImageURI(uri)
            } else {
                Toast.makeText(
                    requireContext(),
                    "Not selected",
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


    private lateinit var binding: FragmentCheckingBinding
    private var imageProcessor = ImageProcessor.Builder()
        .add(ResizeOp(224, 224, ResizeOp.ResizeMethod.BILINEAR)).build()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {}
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCheckingBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        pick()
        binding.btnChecking.setOnClickListener {
            val result = model()
            Log.e("index",    result.toString())
            if (uri !=null){
                findNavController().navigate(
                    CheckingFragmentDirections.actionCheckingFragmentToDiagnosticResultsFragment(
                        uri!!,
                        result)
                )
            }else{
                Toast.makeText(requireContext(),"Not Select Image",Toast.LENGTH_SHORT).show()
            }


        }
    }


    fun model(): Int {
        var tensorImage = TensorImage(DataType.FLOAT32)
        tensorImage.load(bitmap)
        tensorImage = imageProcessor.process(tensorImage)
        val model = com.example.greendefend.ml.Model.newInstance(requireContext())

// Creates inputs for reference.
        val inputFeature0 =
            TensorBuffer.createFixedSize(intArrayOf(1, 224, 224, 3), DataType.FLOAT32)
        inputFeature0.loadBuffer(tensorImage.buffer)

// Runs model inference and gets result.
        val outputs = model.process(inputFeature0)
        val outputFeature0 = outputs.outputFeature0AsTensorBuffer.floatArray

        var maxId = 0
        outputFeature0.forEachIndexed { index, fl ->
            if (outputFeature0[maxId] < fl) {
                maxId = index
            }
        }
        model.close()
        Log.e("Max id ",maxId.toString())
        return maxId


    }
}


//
//fun showCustomDialog() {
//val dialog = Dialog(requireContext())
//dialog.setContentView(R.layout.cutom_dialog)
//dialog.setCancelable(false)
//val btnGo = dialog.findViewById<Button>(R.id.btn_go)
//btnGo.setOnClickListener {
//    getContent.launch("image/*")
//    dialog.dismiss()
//}
//dialog.show()
//}
//private fun cheekPermission() {
//Dexter.withContext(requireContext()).withPermissions(
//    android.Manifest.permission.CAMERA,
//    android.Manifest.permission.READ_EXTERNAL_STORAGE
//).withListener(object : MultiplePermissionsListener {
//    override fun onPermissionsChecked(p0: MultiplePermissionsReport?) =
//        if (p0!!.areAllPermissionsGranted()) {
//            showCustomDialog()
//
//        } else {
//            Toast.makeText(
//                requireContext(),
//                getString(R.string.please_allow_permission_on_camera),
//                Toast.LENGTH_SHORT
//            ).show()
//        }
//
//    override fun onPermissionRationaleShouldBeShown(
//        p0: MutableList<PermissionRequest>?,
//        p1: PermissionToken?
//    ) {
//        p1!!.continuePermissionRequest()
//    }
//}).check()
//}
