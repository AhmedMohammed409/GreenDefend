package com.example.greendefend.ui.authentication

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.greendefend.databinding.FragmentEntercodeBinding
import com.example.greendefend.domin.model.account.Confirm
import com.example.greendefend.domin.useCase.AuthViewModel
import com.example.greendefend.utli.NetworkResult
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint

class EntercodeFragment: Fragment() {
   private lateinit var binding: FragmentEntercodeBinding
   private val args:EntercodeFragmentArgs by navArgs()
    private val  viewModelAccount: AuthViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View{
        // Inflate the layout for this fragment
        binding= FragmentEntercodeBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.btnSend.setOnClickListener {
            binding.progressBar.visibility = View.VISIBLE

if (binding.pinview.text!!.length==4){
    enterCodeAndObserve(Confirm(binding.pinview.text.toString().toInt(),args.email))
}


        }

        binding.pinview.addTextChangedListener(object :TextWatcher{
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                TODO("Not yet implemented")
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (binding.pinview.text!!.length==4){
                    enterCodeAndObserve(Confirm(binding.pinview.text.toString().toInt(),args.email))
                }else{
                    Toast.makeText(requireContext(),"Enter 4 digits code",Toast.LENGTH_SHORT).show()
                }
            }

            override fun afterTextChanged(s: Editable?) {
                TODO("Not yet implemented")
            }
        })

    }

    fun enterCodeAndObserve(confirm: Confirm){
        viewModelAccount.confirm(confirm)

        viewModelAccount.response.observe(viewLifecycleOwner) { response ->
            when (response) {
                is NetworkResult.Success -> {
                    binding.progressBar.visibility = View.GONE
                    Toast.makeText(requireContext(), "Sucessfull", Toast.LENGTH_SHORT).show()
                    Log.e("result", response.data.toString())
                    viewModelAccount.rest()
                    findNavController().navigate(EntercodeFragmentDirections.actionEntercodeFragmentToDoneFragment())
                }

                is NetworkResult.Error -> {
                    binding.progressBar.visibility = View.GONE
                    Toast.makeText(requireContext(), response.errMsg, Toast.LENGTH_SHORT).show()
                }

                is NetworkResult.Exception -> {
                    binding.progressBar.visibility = View.GONE
                    Log.e("result", response.e.toString())
                    Toast.makeText(
                        requireContext(),
                        response.e.message.toString(),
                        Toast.LENGTH_LONG
                    ).show()
                }
            }
        }
    }


}