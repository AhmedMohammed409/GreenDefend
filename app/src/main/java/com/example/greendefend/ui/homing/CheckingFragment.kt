package com.example.greendefend.ui.homing

import android.Manifest.permission
import android.app.Activity
import android.app.Dialog
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.core.net.toUri
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.greendefend.R
import com.example.greendefend.databinding.FragmentCheckingBinding
import org.tensorflow.lite.DataType
import org.tensorflow.lite.support.image.ImageProcessor
import org.tensorflow.lite.support.image.TensorImage
import org.tensorflow.lite.support.image.ops.ResizeOp
import org.tensorflow.lite.support.tensorbuffer.TensorBuffer
import java.io.InputStream

class CheckingFragment : Fragment() {
    private lateinit var bitmap: Bitmap
    private val args: CheckingFragmentArgs by navArgs()
    private var uri: Uri? = null

    //    private val listDisease by lazy {
//        requireActivity().assets.open("label2.txt").bufferedReader().readLines()
//    }
    private var permissions = arrayOf(permission.READ_EXTERNAL_STORAGE, permission.CAMERA)
    private val permissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { permissions ->
            val granted = permissions.entries.all { it.value }
            if (granted) {
                selectImage()
            } else {
                Toast.makeText(requireContext(),
                    getString(R.string.no_permission_granted), Toast.LENGTH_SHORT).show()
            }
        }
    private val resultLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                if(result.data!=null){

                    if (args.temp){
                        uri =   result.data!!.data
                        val inputStream: InputStream? =
                            requireContext().contentResolver.openInputStream(uri!!)
                        bitmap = BitmapFactory.decodeStream(inputStream)
                        binding.imgCamera.setImageURI(uri)
                        binding.btnChecking.visibility=View.VISIBLE
                    }else{
                        uri="testing".toUri()
                        bitmap = result.data!!.extras?.get("data") as Bitmap
                        binding.imgCamera.setImageBitmap(bitmap)
                        binding.btnChecking.visibility=View.VISIBLE
                    }

                }else{
                    Toast.makeText(requireContext(),
                        getString(R.string.not_select_image), Toast.LENGTH_SHORT).show()
                    findNavController().navigateUp()
                }


            }


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
            if (args.temp) {
                selectImage()
            } else {
                openCamera()
            }

        } else {
            permissionLauncher.launch(permissions)
        }

    }


    private lateinit var binding: FragmentCheckingBinding


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

        showCustomDialog()


        binding.btnChecking.setOnClickListener {
            val result = model()
            Log.e("index", result.toString())
            if (uri != null) {
                findNavController().navigate(
                    CheckingFragmentDirections.actionCheckingFragmentToDiagnosticResultsFragment(
                        uri!!,
                        result
                    )
                )
            } else {
                Toast.makeText(requireContext(), R.string.not_select_image, Toast.LENGTH_SHORT).show()
                findNavController().navigateUp()

            }


        }
    }

    private fun openCamera() {
        val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        resultLauncher.launch(cameraIntent)
    }

    private fun selectImage() {
        val intent = Intent(Intent.ACTION_GET_CONTENT)
        intent.type = "image/*"
        resultLauncher.launch(intent)
    }


    private fun showCustomDialog() {
        val dialog = Dialog(requireContext())
        dialog.setContentView(R.layout.cutom_dialog)
        dialog.setCancelable(false)
        val btnGo = dialog.findViewById<Button>(R.id.btn_go)
        btnGo.setOnClickListener {
            pick()

            dialog.dismiss()
        }
        dialog.show()
    }

    fun model(): Int {

        val imageProcessor = ImageProcessor.Builder()
            .add(ResizeOp(224, 224, ResizeOp.ResizeMethod.BILINEAR)).build()

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
        Log.e("rss",outputFeature0.toString())
        for (i in outputFeature0){
            Log.e("rrr",i.toString())
        }
//         outputFeature0.forEachIndexed { index, fl ->
//             Log.e("result float",index.toString())
//         }

        var maxId = 0
        outputFeature0.forEachIndexed { index, fl ->
            if (outputFeature0[maxId] < fl) {
                maxId = index
//                Log.e("r",index.toString())
            }
        }
        model.close()
        return maxId - 1
    }
}





