package com.example.greendefend.ui.authentication

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModel
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.greendefend.databinding.FragmentEntercodeBinding
import com.example.greendefend.date.local.account.Confirm


class EntercodeFragment: Fragment() {
   private lateinit var binding: FragmentEntercodeBinding
   private val args:EntercodeFragmentArgs by navArgs()
    private val  viewModelAccount:ViewModelAccount by viewModels()
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
            findNavController().navigate(EntercodeFragmentDirections.actionEntercodeFragmentToDoneFragment())
        }

    }

    fun enterCodeAndObserve(confirm: Confirm){
        viewModelAccount.confirm(confirm)

        viewModelAccount.serverResponse.observe(viewLifecycleOwner) {
            if (it.isNotEmpty()) {
                Toast.makeText(requireContext(), "Sucessfull", Toast.LENGTH_SHORT).show()
                binding.progressBar.visibility = View.GONE
                viewModelAccount.rest()
                findNavController().navigate(EntercodeFragmentDirections.actionEntercodeFragmentToDoneFragment())
            }
        }

        viewModelAccount.connectionError.observe(viewLifecycleOwner) {
            if (it.isNotEmpty()) {

                Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
                binding.progressBar.visibility = View.GONE
                viewModelAccount.rest()
            }
        }
    }


}