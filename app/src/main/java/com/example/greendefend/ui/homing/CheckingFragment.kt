package com.example.greendefend.ui.homing

import android.Manifest.permission
import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
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
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.io.InputStream

class CheckingFragment : Fragment() {
    private lateinit var bitmap: Bitmap
    private val args: CheckingFragmentArgs by navArgs()
    private var uri: Uri? = null

    //    private val listDisease by lazy {
//        requireActivity().assets.open("label2.txt").bufferedReader().readLines()
//    }

    private var permissions = arrayOf(permission.READ_EXTERNAL_STORAGE,permission.WRITE_EXTERNAL_STORAGE, permission.CAMERA)
    private val permissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { permissions ->
            val granted = permissions.entries.all { it.value }
            if (granted) {
                selectImage()
            } else {
                Toast.makeText(
                    requireContext(),
                    getString(R.string.no_permission_granted), Toast.LENGTH_SHORT
                ).show()
            }
        }
    private val resultLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                if (result.data != null) {

                    if (args.temp) {
                        uri = result.data!!.data
                        val inputStream: InputStream? =
                            requireContext().contentResolver.openInputStream(uri!!)
                        bitmap = BitmapFactory.decodeStream(inputStream)
                        binding.imgCamera.setImageURI(uri)
                        binding.btnChecking.visibility = View.VISIBLE
                    } else {
                        bitmap = result.data!!.extras?.get("data") as Bitmap
                        uri=saveImageToExternalStorage(bitmap)
                        Log.e("uri",uri.toString())
                        binding.imgCamera.setImageURI(uri)
                        binding.btnChecking.visibility = View.VISIBLE

                    }

                } else {
                    Toast.makeText(
                        requireContext(),
                        getString(R.string.not_select_image), Toast.LENGTH_SHORT
                    ).show()
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
                Toast.makeText(requireContext(), R.string.not_select_image, Toast.LENGTH_SHORT)
                    .show()
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
        Log.e("rss", outputFeature0.toString())
        for (i in outputFeature0) {
            Log.e("rrr", i.toString())
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

    override fun onAttach(context: Context) {
        (requireActivity() as HomeActivity).binding.toolbar.visibility = View.GONE
        super.onAttach(context)
    }

    override fun onPause() {
        (requireActivity() as HomeActivity).binding.toolbar.visibility = View.VISIBLE
        super.onPause()
    }

//    fun checkSDK():Boolean{
//        if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.Q){
//            return true
//        }
//        return  false
//    }

//    private fun saveImage(bitmap: Bitmap): Uri? {
//
//
//        val dir = File(Environment.getDataDirectory(), "SaveImage")
//        if (!dir.exists()) {
//            dir.mkdirs()
//        }
//
//        val file=File(dir,"${System.currentTimeMillis()}.jpg")
//        try {
//            val outputStream=FileOutputStream(file)
//            bitmap.compress(Bitmap.CompressFormat.JPEG,100,outputStream)
//
//            Toast.makeText(requireContext(),"sucess save",Toast.LENGTH_SHORT).show()
//            outputStream.flush()
//            outputStream.close()
//        }catch (e:Exception){
//            e.printStackTrace()
//            return null
//        }
//
//        return Uri.fromFile(file)
//    }

    private fun saveImageToExternalStorage(bitmap:Bitmap):Uri? {
        if (isExternalStorageWritable()) {
            val directory =  File(Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES), "MyAppImages")

            if (!directory.exists()) {
                directory.mkdirs()
            }

            val fileName = "image_" + System.currentTimeMillis() + ".jpg"
            val file =  File(directory, fileName)

            try {
                val outputStream =  FileOutputStream(file)
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream)
                outputStream.close()
            } catch ( e : IOException) {
                Toast.makeText(requireContext(),"failed save image",Toast.LENGTH_SHORT).show()
                e.printStackTrace()
            }

            return Uri.fromFile(file)
        } else {
            Toast.makeText(requireContext(),"failed save image",Toast.LENGTH_SHORT).show()
            return null
        }
    }

    private fun isExternalStorageWritable():Boolean {
        val state = Environment.getExternalStorageState()
        return Environment.MEDIA_MOUNTED == state
    }
}





