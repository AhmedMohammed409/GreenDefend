package com.example.greendefend.ui.authentication

import android.os.Bundle
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
import com.example.greendefend.domin.useCase.viewModels.AuthViewModel
import com.example.greendefend.utli.NetworkResult
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class EnterCodeFragment: Fragment() {
   private lateinit var binding: FragmentEntercodeBinding
   private val args:EnterCodeFragmentArgs by navArgs()
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
            if (binding.pinview.text!!.length==4){
                binding.progressBar.visibility = View.VISIBLE
                enterCodeAndObserve(Confirm(binding.pinview.text.toString(),args.email))
            }
        }

    }

    private fun enterCodeAndObserve(confirm: Confirm){
        viewModelAccount.confirm(confirm)
        viewModelAccount.response.observe(viewLifecycleOwner) { response ->
            when (response) {
                is NetworkResult.Success -> {
                    binding.progressBar.visibility = View.GONE
                    Toast.makeText(requireContext(), "Sucessfull", Toast.LENGTH_SHORT).show()
                    Log.e("result", response.data.toString())

                    findNavController().navigate(EnterCodeFragmentDirections.actionEntercodeFragmentToDoneFragment())
                }

                is NetworkResult.Error -> {
                    binding.progressBar.visibility = View.GONE
                    Toast.makeText(requireContext(), response.errMsg.toString(), Toast.LENGTH_SHORT).show()
                }

            }
        }
    }


}