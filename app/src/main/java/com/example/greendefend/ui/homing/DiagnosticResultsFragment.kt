package com.example.greendefend.ui.homing

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.example.greendefend.databinding.FragmentDiagnosticResultsBinding
import com.example.greendefend.domin.model.Disease
import com.example.greendefend.utli.Info
import com.google.gson.Gson


class DiagnosticResultsFragment : Fragment() {
    private lateinit var binding: FragmentDiagnosticResultsBinding
    private val args:DiagnosticResultsFragmentArgs by navArgs()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {}
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding= FragmentDiagnosticResultsBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

       val json=readJSONfromAssets()


        val listDisease=getListfromJson(json)
//        Log.e("list arry",listDisease.toString())
        Log.e("list arry size ",listDisease.size.toString()+listDisease[0])
//Toast.makeText(requireContext(),listDisease.size,Toast.LENGTH_LONG).show()
        binding.img.setImageURI(args.uri)
        binding.disease=listDisease[args.index]
        binding.txtDisease.text=listDisease[args.index].name
        binding.txtDescription.text=listDisease[args.index].description
        binding.txtReason.text=listDisease[args.index].cause
        binding.txtProtection.text=listDisease[args.index].prevention
        binding.txtTreatment.text=listDisease[args.index].treatment
    }
    private fun readJSONfromAssets():String{
        var json:String?=null
        Log.e("lang",Info().getLanguage())
try {
   json= requireActivity().assets.open("diseases_${Info().getLanguage()}.json").bufferedReader().use {
        it.readText()
    }
}catch (e :Exception){
e.printStackTrace()
}
return json!!
    }
    private fun getListfromJson(json:String):List<Disease>{
        var result: List<Disease>? =null
        try {
            val gson = Gson()
             result = gson.fromJson(json, Array<Disease>::class.java).asList()
            Log.e("convert j",result.toString())

        }catch (e:Exception){
            e.printStackTrace()
        }
        return result!!
    }


}